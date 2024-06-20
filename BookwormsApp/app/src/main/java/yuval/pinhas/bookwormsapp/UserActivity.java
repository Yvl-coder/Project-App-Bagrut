package yuval.pinhas.bookwormsapp;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This activity represents the user interface of the Bookworms app,
 * providing access to various fragments: Home, Challenges, Profile, Library, and Quotes.
 *
 * @author Yuval Pinhas
 */
public class UserActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    /**
     * The bottom navigation view for navigating between fragments.
     */
    BottomNavigationView bottomNavigationView;

    /**
     * The HomeFragment instance.
     */
    HomeFragment homeFragment = new HomeFragment();

    /**
     * The ChallengesFragment instance.
     */
    ChallengesFragment challengesFragment = new ChallengesFragment();

    /**
     * The ProfileFragment instance.
     */
    ProfileFragment profileFragment = new ProfileFragment();

    /**
     * The LibraryFragment instance.
     */
    LibraryFragment libraryFragment = new LibraryFragment();

    /**
     * The QuotesFragment instance.
     */
    QuotesFragment quotesFragment = new QuotesFragment();


    /**
     * The ID of the selected item in the bottom navigation view.
     */
    private static final String SELECTED_ITEM_ID = "selected_item_id";

    /**
     * Variable to store the selected item index.
     */
    private int selectedItem = R.id.home;


    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then
     *                           this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState}.
     *                           <b>Note: Otherwise, it is null.</b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Restore the selected fragment index if it was saved
        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt(SELECTED_ITEM_ID, R.id.home);
        }

        // Select the fragment based on the saved index
        bottomNavigationView.setSelectedItemId(selectedItem);
    }

    /**
     * Called when a menu item in the bottom navigation view is selected.
     *
     * @param item The selected menu item.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.home) {
            selectedItem = R.id.home;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.challenges) {
            selectedItem = R.id.challenges;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, challengesFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.library) {
            selectedItem = R.id.library;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, libraryFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.quotes) {
            selectedItem = R.id.quotes;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, quotesFragment)
                    .commit();
            return true;
        } else if (itemId == R.id.profile) {
            selectedItem = R.id.profile;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, profileFragment)
                    .commit();
            return true;
        }

        return false;
    }

    /**
     * Called to retrieve per-instance state from an activity before being killed so that the state can be restored
     * in {@link #onCreate(Bundle)} or {@link #onRestoreInstanceState(Bundle)}.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the selected item id
        outState.putInt(SELECTED_ITEM_ID, selectedItem);
    }
}
