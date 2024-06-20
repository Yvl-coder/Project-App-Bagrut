package yuval.pinhas.bookwormsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * This activity allows users to challenge them self with fast talking by presenting them with a random word
 * from a predefined list and prompting them to say it as quickly as possible. It includes
 * a countdown timer to limit the talking time. Users can submit their said words and receive
 * feedback on their performance.
 *
 * @autor Yuval Pinhas
 */
public class FastTalkingActivity extends AppCompatActivity {
    /**
     * The username of the current user.
     */
    private final String username = SharedPrefManager.getInstance(FastTalkingActivity.this).getUsername();

    /**
     * The button to start / saying the word.
     */
    private Button startSayButton;

    /**
     * The text view displaying the word to say.
     */
    private TextView wordToSay;

    /**
     * The text view displaying the timer.
     */
    private TextView timerTextView;

    /**
     * The text view displaying the instructions.
     */
    private TextView instructionsTextView;

    /**
     * Flag indicating if the timer is currently running.
     */
    private boolean isTimerRunning = false;

    /**
     * The countdown timer instance.
     */
    private CountDownTimer countDownTimer;

    /**
     * The remaining time in milliseconds.
     */
    private long timeLeftInMillis;

    /**
     * An array of words for the challenge.
     */
    private String[] words = {
            "keyboard", "mouse", "white", "black", "screen", "bottle", "tree", "lion", "zebra", "cable",
            "speed", "brand", "book", "page", "teacher", "student", "apple", "banana", "orange", "grape",
            "peach", "strawberry", "computer", "phone", "table", "chair", "class", "door", "window", "car",
            "bike", "train", "plan", "ship", "clock", "mirror", "desk", "pen", "pencil", "notebook",
            "flower", "guitar", "music", "painting", "camera", "keyboard", "monitor", "headphones", "speaker",
            "microphone", "button", "map", "shoes", "hat", "shirt", "pants", "socks", "glasses", "umbrella"
    };

    /**
     * The word to be said.
     */
    private String word;

    /**
     * ActivityResultLauncher for speech input.
     */
    private ActivityResultLauncher<Intent> speechLauncher;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being reinitialized after previously being shut down then
     *                           this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_talking);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back arrow
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Handle back arrow click event
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Remove "Bookworms" from the title
        getSupportActionBar().setTitle("");

        startSayButton = findViewById(R.id.start_say_button);
        wordToSay = findViewById(R.id.word_to_say);
        timerTextView = findViewById(R.id.timer_textview);
        instructionsTextView = findViewById(R.id.instructions_textview);

        // Register the ActivityResultLauncher to handle the result from the speech recognition activity
        speechLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), // Use the StartActivityForResult to start an activity and receive a result
                result -> { // Defines the actions to be taken when the result of the speech recognition activity is received, using lambda func
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Get the recognized words from the speech input
                        ArrayList<String> speechResult = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        // Get the first recognized word
                        String userAnswer = Objects.requireNonNull(speechResult).get(0);

                        // Check if the recognized word matches the displayed word
                        if (userAnswer.equalsIgnoreCase(word)) {
                            // Calculate the time taken to say the word
                            long timeTaken = 60000 - timeLeftInMillis;
                            double newScore = timeTaken / 1000.0; // Calculate the score

                            // Create an instance of DatabaseHelper to interact with the database
                            DatabaseHelper dbHelper = new DatabaseHelper(FastTalkingActivity.this);

                            // Get the old score from the database
                            double oldScore = dbHelper.getFastTalkingScore(username);

                            // Update the score in the database and show the appropriate dialog
                            if (dbHelper.updateFastTalkingScore(username, newScore, oldScore)) {
                                if (oldScore < newScore)
                                    showDialog("Good job! You said '" + word + "' correctly in " + newScore + " seconds. But, your best time is even less: " + oldScore + " seconds.");
                                else if (oldScore == newScore)
                                    showDialog("Good job! You said '" + word + "' correctly in " + newScore + " seconds. Also, your best time is exactly this time.");
                                else if (oldScore != Double.MAX_VALUE)
                                    showDialog("Good job! You said '" + word + "' correctly in " + newScore + " seconds. Also, you improved. Your previous best was: " + oldScore + " seconds.");
                                else
                                    showDialog("Good job! You said '" + word + "' correctly in " + newScore + " seconds.");
                            } else {
                                showDialog("Failed to update score.");
                            }
                            // Close the DatabaseHelper instance
                            dbHelper.close();
                        } else {
                            showDialog("Nice try! Try again.");
                        }

                        // Reset the UI elements
                        startSayButton.setText("Start");
                        instructionsTextView.setVisibility(View.VISIBLE);
                        wordToSay.setVisibility(View.INVISIBLE);
                        timerTextView.setVisibility(View.INVISIBLE);

                        // Cancel the countdown timer
                        countDownTimer.cancel();
                        isTimerRunning = false;
                    }
                });

        // Set the onClickListener for the start/say button
        startSayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the button text is "Start", start the stopwatch and select a random word
                if (startSayButton.getText().toString().equals("Start")) {
                    startStopwatch();

                    // Choose a random word from the array
                    Random random = new Random();
                    int index = random.nextInt(words.length);
                    word = words[index];

                    // Display the selected word to the user
                    wordToSay.setText("Say the word: " + word);

                    // Change button text to "Say!"
                    startSayButton.setText("Say!");

                    // Update visibility of UI elements
                    instructionsTextView.setVisibility(View.INVISIBLE);
                    wordToSay.setVisibility(View.VISIBLE);
                    timerTextView.setVisibility(View.VISIBLE);
                } else { // If the button text is "Say!", start the STT
                    // Create an intent to recognize speech input
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                    try {
                        // Start the activity to recognize speech input using the launcher
                        speechLauncher.launch(intent);
                    } catch (Exception e) {
                        // Display an error message if speech recognition fails
                        Toast.makeText(FastTalkingActivity.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * Starts the stopwatch for the challenge.
     */
    private void startStopwatch() {
        if (!isTimerRunning) {
            // Set the initial time to 60,000 milliseconds (1 minute)
            timeLeftInMillis = 60000;
            isTimerRunning = true;

            // Create and start the countdown timer
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // Update the remaining time on each tick
                    timeLeftInMillis = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    // Reset the UI elements when the timer finishes
                    startSayButton.setText("Start");
                    instructionsTextView.setVisibility(View.VISIBLE);
                    wordToSay.setVisibility(View.INVISIBLE);
                    timerTextView.setVisibility(View.INVISIBLE);

                    // Cancel the countdown timer
                    countDownTimer.cancel();
                    isTimerRunning = false;

                    // Show a dialog indicating that the user must be faster
                    showDialog("You must be faster!");
                }
            }.start();
        }
    }

    /**
     * Updates the timer display.
     */
    private void updateTimer() {
        // Calculate the remaining seconds
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        // Format the remaining time and set it to the TextView
        String timeLeftFormatted = String.format(Locale.getDefault(), "Time left: %02ds", seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    /**
     * Shows a dialog with the given message.
     *
     * @param message The message to display in the dialog.
     */
    private void showDialog(String message) {
        // Create and show an AlertDialog with the provided message
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Overrides the onDestroy method to stop the countdown timer if it's running.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the countdown timer if it's running
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isTimerRunning = false;
        }
    }
}
