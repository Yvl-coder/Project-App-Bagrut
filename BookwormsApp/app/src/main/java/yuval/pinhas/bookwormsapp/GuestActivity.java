package yuval.pinhas.bookwormsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This activity represents the guest interface of the Bookworms app,
 * providing access to various fragments: Home, About, Login, Signup, and Contact.
 *
 * @author Yuval Pinhas
 */
public class GuestActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {

    /**
     * The bottom navigation view for navigating between fragments.
     */
    BottomNavigationView bottomNavigationView;

    /**
     * The HomeFragment instance.
     */
    HomeFragment homeFragment = new HomeFragment();

    /**
     * The AboutFragment instance.
     */
    AboutFragment aboutFragment = new AboutFragment();

    /**
     * The LoginFragment instance.
     */
    LoginFragment loginFragment = new LoginFragment();

    /**
     * The SignupFragment instance.
     */
    SignupFragment signupFragment = new SignupFragment();

    /**
     * The ContactFragment instance.
     */
    ContactFragment contactFragment = new ContactFragment();

    /**
     * The ID of the selected item in the bottom navigation view.
     */
    private static final String SELECTED_ITEM_ID = "selected_item_id";

    /**
     * The ID of the currently selected item.
     */
    private int selectedItem = R.id.home; // Default selection


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
        setContentView(R.layout.activity_guest);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(this);

        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt(SELECTED_ITEM_ID);
        }

        bottomNavigationView.setSelectedItemId(selectedItem);
    }

    /**
     * Called to retrieve per-instance state from an activity before being killed so that the state can be restored
     * in {@link #onCreate(Bundle)} or {@link #onRestoreInstanceState(Bundle)}.
     *
     * @param outState Bundle in which used to store the activity's state so that it can be restored if the activity is recreated.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SELECTED_ITEM_ID, selectedItem);
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when a menu item in the bottom navigation view is selected.
     *
     * @param item The selected menu item.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectedItem = item.getItemId();
        if (selectedItem == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
            return true;
        } else if (selectedItem == R.id.about) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, aboutFragment).commit();
            return true;
        } else if (selectedItem == R.id.login) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, loginFragment).commit();
            return true;
        } else if (selectedItem == R.id.signup) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, signupFragment).commit();
            return true;
        } else if (selectedItem == R.id.contact) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, contactFragment).commit();
            return true;
        }
        return false;
    }

    /**
     * Called when the activity is about to become visible.
     * Check if the user is already logged in, if so, redirect to the UserActivity.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).getUsername() != null) {
            Intent i = new Intent(GuestActivity.this, UserActivity.class);
            startActivity(i);
            finish();
        }
    }
}
