package yuval.pinhas.bookwormsapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Locale;
import java.util.Random;


/**
 * This activity allows users to challenge them self with fast typing by presenting them with a random word
 * from a predefined list and prompting them to type it as quickly as possible. It includes
 * a countdown timer to limit the typing time. Users can submit their typed words and receive
 * feedback on their performance.
 *
 *
 * @author Yuval
 */
public class FastTypingActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    /**
     * The username of the current user.
     */
    private String username = SharedPrefManager.getInstance(FastTypingActivity.this).getUsername();

    /**
     * The TextToSpeech engine instance for pronouncing words.
     */
    private TextToSpeech textToSpeech;

    /**
     * The button used to start or submit the typing task.
     */
    private Button startSubmitButton;

    /**
     * The EditText field for user input of typed words.
     */
    private EditText answerEditText;

    /**
     * The TextView displaying the countdown timer.
     */
    private TextView timerTextView;

    /**
     * The TextView displaying instructions for the typing task.
     */
    private TextView instructionsTextView;

    /**
     * Indicates whether the countdown timer is currently running.
     */
    private boolean isTimerRunning = false;

    /**
     * The CountDownTimer instance for tracking typing time.
     */
    private CountDownTimer countDownTimer;

    /**
     * The remaining time in milliseconds on the countdown timer.
     */
    private long timeLeftInMillis;

    /**
     * The InputMethodManager instance for managing input methods.
     */
    private InputMethodManager inputMethodManager;

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
     * The currently selected word for the typing challenge.
     */
    private String word;


    /**
     * Initializes the activity layout and sets up necessary components.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_typing);

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

        // Initialize the TextToSpeech engine
        textToSpeech = new TextToSpeech(this, this);

        startSubmitButton = findViewById(R.id.start_submit_button);
        answerEditText = findViewById(R.id.answer_edittext);
        timerTextView = findViewById(R.id.timer_textview);
        instructionsTextView = findViewById(R.id.instructions_textview);

        // Get the input method manager service
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        // Set the onClickListener for the start/submit button
        startSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the button text is "Start", start the stopwatch and select a random word
                if (startSubmitButton.getText().toString().equals("Start")) {
                    startStopwatch();

                    // Choose a random word from the array
                    Random random = new Random();
                    int index = random.nextInt(words.length);
                    word = words[index];

                    // Speak the selected word
                    speakWord(word);

                    startSubmitButton.setText("Submit");
                    instructionsTextView.setVisibility(View.INVISIBLE);
                    answerEditText.setVisibility(View.VISIBLE);
                    timerTextView.setVisibility(View.VISIBLE);
                } else {
                    // If the button text is "Submit", check the user's answer
                    String userAnswer = answerEditText.getText().toString().trim();
                    if (userAnswer.equalsIgnoreCase(word)) {
                        long timeTaken = 60000 - timeLeftInMillis;
                        double newScore = timeTaken / 1000.0; // Calculate the score
                        DatabaseHelper dbHelper = new DatabaseHelper(FastTypingActivity.this);
                        double oldScore = dbHelper.getFastTypingScore(username);

                        // Update the score in the database and show the appropriate dialog
                        if (dbHelper.updateFastTypingScore(username, newScore, oldScore)) {
                            if (oldScore < newScore)
                                showDialog("Good job! You typed '" + word + "' correctly in " + newScore + " seconds. But, your best time is even less: " + oldScore + " seconds.");
                            else if (oldScore == newScore)
                                showDialog("Good job! You typed '" + word + "' correctly in " + newScore + " seconds. Also, your best time is exactly this time.");
                            else if (oldScore != Double.MAX_VALUE)
                                showDialog("Good job! You typed '" + word + "' correctly in " + newScore + " seconds. Also, you improved. Your previous best was: " + oldScore + " seconds.");
                            else
                                showDialog("Good job! You typed '" + word + "' correctly in " + newScore + " seconds.");
                        } else {
                            showDialog("Failed to update score.");
                        }
                        dbHelper.close(); // Close the DatabaseHelper instance
                    } else {
                        showDialog("Nice try! Try again.");
                    }

                    // Reset the UI elements
                    startSubmitButton.setText("Start");
                    answerEditText.getText().clear();
                    instructionsTextView.setVisibility(View.VISIBLE);
                    answerEditText.setVisibility(View.INVISIBLE);
                    timerTextView.setVisibility(View.INVISIBLE);
                    countDownTimer.cancel();
                    isTimerRunning = false;

                    // Hide the keyboard
                    inputMethodManager.hideSoftInputFromWindow(answerEditText.getWindowToken(), 0);
                }
            }
        });
    }

    /**
     * Initializes the TextToSpeech engine.
     *
     * @param status The initialization status of the TextToSpeech engine.
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(FastTypingActivity.this, "TTS not supported!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(FastTypingActivity.this, "TTS failed! Try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Speaks the given word using Text-to-Speech.
     *
     * @param word The word to be spoken.
     */
    private void speakWord(String word) {
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    /**
     * Starts the countdown timer for typing.
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
                    // Update the remaining time
                    timeLeftInMillis = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    // Handle the timer finish event
                    startSubmitButton.setText("Start");
                    answerEditText.getText().clear();
                    instructionsTextView.setVisibility(View.VISIBLE);
                    answerEditText.setVisibility(View.INVISIBLE);
                    timerTextView.setVisibility(View.INVISIBLE);
                    countDownTimer.cancel();
                    isTimerRunning = false;
                    showDialog("You must be faster!");

                    // Hide the keyboard
                    inputMethodManager.hideSoftInputFromWindow(answerEditText.getWindowToken(), 0);
                }
            }.start();
        }
    }

    /**
     * Updates the timer display with the remaining time.
     */
    private void updateTimer() {
        // Calculate the remaining seconds
        int seconds = (int) (timeLeftInMillis / 1000) % 60; // The int is not necessary, its to mark that the result is a whole number
        // Format the remaining time and set it to the TextView
        String timeLeftFormatted = String.format(Locale.getDefault(), "Time left: %02ds", seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    /**
     * Displays a dialog with the given message.
     *
     * @param message The message to be displayed in the dialog.
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
