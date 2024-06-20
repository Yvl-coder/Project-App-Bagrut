package yuval.pinhas.bookwormsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Fragment for displaying and managing user profile information.
 *
 * @author Yuval Pinhas
 */
public class ProfileFragment extends Fragment {

    /**
     * TextView instances used to display the username, ID, and email of the user, respectively.
     */
    private TextView usernameTextView, idTextView, emailTextView;

    /**
     * Button instance used to handle the logout functionality.
     */
    private Button logoutButton;

    /**
     * Instance of DatabaseHelper, which is a helper class for managing interactions with the local database.
     */
    private DatabaseHelper databaseHelper;


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return Return the View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(getContext());

        // Initialize views
        usernameTextView = view.findViewById(R.id.username);
        idTextView = view.findViewById(R.id.id);
        emailTextView = view.findViewById(R.id.email);
        logoutButton = view.findViewById(R.id.logout_button);

        String username = SharedPrefManager.getInstance(getActivity()).getUsername();
        usernameTextView.setText(username);
        idTextView.setText(databaseHelper.getUserId(username));
        emailTextView.setText(databaseHelper.getUserEmail(username));

        LinearLayout contact = view.findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to ContactActivity
                Intent intent = new Intent(getActivity(), ContactActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout editSaveDetails = view.findViewById(R.id.edit_save_details);
        editSaveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDetailsDialog();
            }
        });

        // Set delete profile button click listener
        LinearLayout deleteProfile = view.findViewById(R.id.delete_profile);
        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteProfileConfirmationDialog();
            }
        });

        // Set logout button click listener
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout button click
                logout();
                Toast.makeText(requireContext(), "Logout Successfully!", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    /**
     * Method to show a confirmation dialog before deleting the user profile.
     */
    private void showDeleteProfileConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Profile");
        builder.setMessage("Are you sure you want to delete your profile?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete user profile and logout
                deleteUserAndLogout();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Method to delete the user profile and log out.
     */
    private void deleteUserAndLogout() {
        String username = SharedPrefManager.getInstance(getActivity()).getUsername();
        if (databaseHelper.deleteUser(username)) {
            logout();
            Toast.makeText(requireContext(), "Profile deleted successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Failed to delete profile!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to show the edit details dialog for updating ID and email.
     */
    private void showEditDetailsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View viewInflated = getLayoutInflater().inflate(R.layout.dialog_edit_profile_details, null);
        builder.setTitle("Edit ID/Email:\nUsername is permanent.");
        builder.setView(viewInflated);

        EditText editId = viewInflated.findViewById(R.id.edit_id);
        EditText editEmail = viewInflated.findViewById(R.id.edit_email);

        // Set current user details in EditText fields
        editId.setText(idTextView.getText().toString());
        editEmail.setText(emailTextView.getText().toString());


        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // This method is mandatory to implement
                // Your logic for positive button click goes here
                String newId = editId.getText().toString().trim();
                String newEmail = editEmail.getText().toString().trim();

                if (isValidId(newId)) {
                    if (isValidEmail(newEmail)) {
                        // Check if the new ID or email already exists
                        boolean idExists = databaseHelper.checkIdExistsExceptCurrUser(usernameTextView.getText().toString(), newId);
                        boolean emailExists = databaseHelper.checkEmailExistsExceptCurrUser(usernameTextView.getText().toString(), newEmail);

                        if (!idExists) {
                            if (!emailExists) {
                                editId.setError("ID already exists! Please choose a different one.");
                                // Update user details in database
                                boolean isUpdated = databaseHelper.updateUserDetails(usernameTextView.getText().toString(), newId, newEmail);
                                if (isUpdated) {
                                    // Update UI with new details
                                    idTextView.setText(newId);
                                    emailTextView.setText(newEmail);
                                    Toast.makeText(getContext(), "Details updated successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Failed to update details!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Email already exists! Please choose a different one.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "ID already exists! Please choose a different one.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Invalid Email format!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid ID format!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * Method to validate the format of the provided ID.
     *
     * @param id The ID to validate.
     * @return True if the ID format is valid, false otherwise.
     */
    private boolean isValidId(String id) {

        return id.length() == 9 && TextUtils.isDigitsOnly(id);
    }

    /**
     * Method to validate the format of the provided email address.
     *
     * @param email The email address to validate.
     * @return True if the email format is valid, false otherwise.
     */
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /**
     * Method to handle logout by clearing the username from shared preferences
     * and starting the GuestActivity.
     */
    private void logout() {
        SharedPrefManager.getInstance(getActivity()).clearUsername();

        // Start GuestActivity
        Intent intent = new Intent(getActivity(), GuestActivity.class);
        startActivity(intent);
        requireActivity().finish(); // Finish the current activity
    }
}
