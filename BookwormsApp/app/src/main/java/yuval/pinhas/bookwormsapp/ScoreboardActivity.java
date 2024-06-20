package yuval.pinhas.bookwormsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Activity to display the scoreboard, including the best scores for fast typing and fast talking.
 *
 * @author Yuval Pinhas
 */
public class ScoreboardActivity extends AppCompatActivity {

    /**
     * Database helper instance for accessing user data
     */
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    /**
     * TextView to display the best score for fast typing
     */
    TextView fastTypingBestTextView;

    /**
     * TextView to display the best score for fast talking
     */
    TextView fastTalkingBestTextView;

    /**
     * TextView to display the time for the best fast typing score
     */
    TextView timeFastTyping;

    /**
     * TextView to display the time for the best fast talking score
     */
    TextView timeFastTalking;

    /**
     * ImageView to display the icon pointing there is gonna be a username next to it for fast typing
     */
    ImageView icFastTypingBestImageView;

    /**
     * ImageView to display the icon pointing there is gonna be a username next to it for fast talking
     */
    ImageView icFastTalkingBestImageView;


    /**
     * Called when the activity is starting. Responsible for initializing the activity, including
     * setting up the toolbar, retrieving and displaying best scores from the database.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, then this Bundle contains the data it most recently supplied
     *                           in {@link #onSaveInstanceState(Bundle)}. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // Enable the back arrow
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed()); // Handle back arrow click event
        getSupportActionBar().setTitle(""); // Remove "Bookworms" from the title

        // Initialize
        fastTypingBestTextView = findViewById(R.id.fast_typing_best_textview);
        fastTalkingBestTextView = findViewById(R.id.fast_talking_best_textview);
        icFastTypingBestImageView = findViewById(R.id.ic_user_fast_typing);
        icFastTalkingBestImageView = findViewById(R.id.ic_user_fast_talking);
        timeFastTyping = findViewById(R.id.time_fast_typing);
        timeFastTalking = findViewById(R.id.time_fast_talking);

        // Retrieve usernames with the best scores
        String userMinTyping = databaseHelper.getUsernameWithMinFastTypingBest();
        String userMinTalking = databaseHelper.getUsernameWithMinFastTalkingBest();

        // Update the TextViews with the best scores adding the icon for the user according to the demand
        if (userMinTyping == null) {
            fastTypingBestTextView.setText("You can be the first!");
        } else {
            fastTypingBestTextView.setText(" " + userMinTyping);
            icFastTypingBestImageView.setVisibility(View.VISIBLE);

        }
        if (userMinTalking == null) {
            fastTalkingBestTextView.setText("You can be the first!");
        } else {
            fastTalkingBestTextView.setText(" " + userMinTalking);
            icFastTalkingBestImageView.setVisibility(View.VISIBLE);
        }

        // Update the TextViews with the time for the best scores
        timeFastTyping.setText(userMinTyping == null ? "" : "With a time of: " + databaseHelper.getFastTypingScore(userMinTyping) + "s");
        timeFastTalking.setText(userMinTalking == null ? "" : "With a time of: " + databaseHelper.getFastTalkingScore(userMinTalking) + "s");
    }
}
