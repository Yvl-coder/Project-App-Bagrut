package yuval.pinhas.bookwormsapp;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

/**
 * BookActivity is an activity class responsible for displaying details of a specific book,
 * allowing users to view, add, edit, and delete comments related to the book.
 *
 * @author Yuval Pinhas
 */
public class BookActivity extends AppCompatActivity {

    /**
     * Helper class for database operations
     */
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    /**
     * The user that is currently logged
     */
    private String username = SharedPrefManager.getInstance(this).getUsername();


    /**
     * Called when the activity is starting.
     * Initializes the activity, including setting up the toolbar, retrieving book data,
     * displaying book details, and handling user interactions for adding, editing, and deleting comments.
     *
     * @param savedInstanceState a Bundle containing the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

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
        getSupportActionBar().setTitle(""); //remove "Bookworms" from the title

        // Retrieve data passed from LibraryFragment
        String bookName = getIntent().getStringExtra("bookName");
        String bookSummary = getIntent().getStringExtra("bookSummary");
        int bookNumber = getIntent().getIntExtra("bookNumber", -1);

        // Set book name and underline
        TextView bookNameTextView = findViewById(R.id.book_name);
        bookNameTextView.setText(bookName);
        bookNameTextView.setPaintFlags(bookNameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //add underline to the title "summary"
        ((TextView)findViewById(R.id.summary_title)).setPaintFlags(bookNameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Set book summary
        TextView bookSummaryTextView = findViewById(R.id.book_summary);
        bookSummaryTextView.setText(bookSummary);

        // Set book cover image dynamically based on bookNumber
        ImageView bookCoverImageView = findViewById(R.id.book_cover);
        bookCoverImageView.setImageResource(getResources().getIdentifier("book" + bookNumber, "drawable", getPackageName()));

        //add underline to the title "Your Comment:"
        ((TextView)findViewById(R.id.comment_text_view_title)).setPaintFlags(bookNameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Initialize comment views
        EditText commentEditText = findViewById(R.id.comment_edit_text);
        TextView previousCommentTextView = findViewById(R.id.previous_comment_text_view);

        // Retrieve previous comment for the book
        String previousComment = databaseHelper.getBookComment(username, bookNumber);
        if (!previousComment.isEmpty()) {
            previousCommentTextView.setText(previousComment);
        } else {
            previousCommentTextView.setText("No comment yet, add a comment below!");
        }

        //add underline to the title "Enter Comment"
        ((TextView)findViewById(R.id.comment_enter_title)).setPaintFlags(bookNameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Initialize the edit and delete and save TextViews
        TextView editComment = findViewById(R.id.edit_comment);
        TextView deleteComment = findViewById(R.id.delete_comment);
        TextView saveComment = findViewById(R.id.save_comment);
        TextView clearComment = findViewById(R.id.clear_comment);

        // Set up click listener for the edit button
        editComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Copy the text from previousCommentTextView to commentEditText
                String bookComment = databaseHelper.getBookComment(username, bookNumber);
                if (bookComment.isEmpty())
                    Toast.makeText(BookActivity.this, "Can't edit with no comment entered!", Toast.LENGTH_SHORT).show();
                else
                    commentEditText.setText(bookComment);
            }
        });

        // Set up click listener for the delete button
        deleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookComment = databaseHelper.getBookComment(username, bookNumber);
                if (bookComment.isEmpty()) {
                    Toast.makeText(BookActivity.this, "There is not comment to delete!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Delete the comment from the database
                if (!databaseHelper.addOrUpdateComment(username, bookNumber, "")) { // If the deleting not succeed
                    Toast.makeText(BookActivity.this, "Error Occured while deleting the comment! Try again later!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Update the displayed comment to indicate it's deleted
                previousCommentTextView.setText("No comment yet, add a comment below!");
                // Clear the comment EditText
                commentEditText.setText("");
                // Show toast message for successful delete
                Toast.makeText(BookActivity.this, "Comment deleted successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up click listener for the save button
        saveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered comment from the EditText
                String comment = commentEditText.getText().toString().trim();
                if (!comment.isEmpty()) {
                    // Save the comment to the database
                    if (!databaseHelper.addOrUpdateComment(username, bookNumber, comment)) { // If the saving not succeed
                        Toast.makeText(BookActivity.this, "Error Occured while saving the comment! Try again later!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Update the displayed comment
                    previousCommentTextView.setText(comment);
                    // Clear the comment EditText
                    commentEditText.setText("");

                    // Show toast message for successful save
                    Toast.makeText(BookActivity.this, "Comment saved successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up click listener for the clear button
        clearComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the text in the comment EditText
                commentEditText.setText("");
            }
        });

        // Set up click listener for the "View All Comments" button
        Button viewAllCommentsButton = findViewById(R.id.view_all_comments_button);
        viewAllCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to a new activity to display all comments for the current book
                Intent intent = new Intent(BookActivity.this, AllCommentsActivity.class);
                intent.putExtra("bookNumber", bookNumber);
                startActivity(intent);
            }
        });
    }
}
