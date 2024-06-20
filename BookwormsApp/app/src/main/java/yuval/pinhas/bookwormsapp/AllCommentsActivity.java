package yuval.pinhas.bookwormsapp;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * AllCommentsActivity is an activity class responsible for displaying all comments for a specific book.
 * It retrieves comments data from a database and populates a RecyclerView with the comments.
 *
 * @author Yuval Pinhas
 */
public class AllCommentsActivity extends AppCompatActivity {

    /**
     * RecyclerView to display comments
     */
    private RecyclerView commentsRecyclerView;

    /**
     * Adapter to manage comments data
     */
    private CommentsAdapter commentsAdapter;

    /**
     * Helper class for database operations
     */
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    /**
     * Called when the activity is starting. 
     * Initializes the activity, including setting up the toolbar, RecyclerView, and retrieving comments data.
     *
     * @param savedInstanceState a Bundle containing the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comments);

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

        // Initialize RecyclerView
        commentsRecyclerView = findViewById(R.id.comments_recycler_view);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsAdapter = new CommentsAdapter(this);
        commentsRecyclerView.setAdapter(commentsAdapter);

        // Retrieve data passed from BookActivity
        int bookNumber = getIntent().getIntExtra("bookNumber", -1);

        // Retrieve all comments for the book from the database
        List<Comment> comments = databaseHelper.getAllCommentsForBook(bookNumber);

        // Update comments status TextView
        TextView commentsStatusTextView = findViewById(R.id.comments_status);
        int commentsLen = comments.size();
        if (commentsLen == 0) {
            commentsStatusTextView.setText("None user commented! :(");
        } else if (commentsLen == 1) {
            commentsStatusTextView.setText("There is a user commented:");
        } else  {
            commentsStatusTextView.setText("There are " + comments.size() + " users commented:");
        }
        commentsStatusTextView.setPaintFlags(commentsStatusTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Set comments to the adapter
        commentsAdapter.setComments(comments);
    }
}
