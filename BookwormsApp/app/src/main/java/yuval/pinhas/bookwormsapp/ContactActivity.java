package yuval.pinhas.bookwormsapp;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * ContactActivity is an activity class responsible for displaying contact information for logged user.
 *
 * @author Yuval Pinhas
 */
public class ContactActivity extends AppCompatActivity {

    /**
     * The ContactFragment instance.
     */
    ContactFragment contactFragment = new ContactFragment();

    /**
     * Called when the activity is starting.
     * Initializes the activity, including setting up the toolbar and add contact fragment to the activity.
     *
     * @param savedInstanceState a Bundle containing the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call the superclass onCreate method to perform default initialization of the activity
        setContentView(R.layout.activity_contact); // Set the activity content view to the layout defined in activity_contact.xml

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

        // Add the fragment to the activity
        getSupportFragmentManager().beginTransaction().replace(R.id.flContact, contactFragment).commit();
    }
}