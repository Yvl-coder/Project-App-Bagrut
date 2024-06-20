package yuval.pinhas.bookwormsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import yuval.pinhas.bookwormsapp.databinding.FragmentSignupBinding;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Fragment for handling user signup functionality.
 *
 * @author Yuval Pinhas
 */
public class SignupFragment extends Fragment {

    /**
     * An instance of DatabaseHelper is used to interact with the local database.
     */
    private DatabaseHelper databaseHelper;

    /**
     * Handles the creation of the signup fragment view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState This fragment is being re-constructed from a previous saved state
     *                           as given here.
     * @return the root view of the fragment layout
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSignupBinding binding = FragmentSignupBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        databaseHelper = new DatabaseHelper(requireContext());

        // Show hide password using eye icon
        ImageView imageViewShowHidePwd = rootView.findViewById(R.id.imageView_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.signupPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    // if the password is visible then hide it
                    binding.signupPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
                } else {
                    // Password is currently shown, hide it
                    binding.signupPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });

        // Show rules dialog when Rules(?) is clicked
        TextView rulesTextView = rootView.findViewById(R.id.rules);
        rulesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating a dialog to display the rules
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Rules");
                builder.setMessage("Username must start with a letter (a-z,A-Z) and can include up to single digit, the length must be 6-12 characters.\n\n" +
                        "Id must be exactly 9 digits.\n\n" +
                        "Email must be a valid email.\n\n" +
                        "Password must contain a capital letter (A-Z), a lowercase letter (a-z), and a special character (!,@, etc), the length must be 8-20 characters.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Dismiss the dialog when OK is clicked
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show(); // Show the dialog
            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.signupUsername.getText().toString().trim();
                String id = binding.signupId.getText().toString().trim();
                String email = binding.signupEmail.getText().toString().trim();
                String password = binding.signupPassword.getText().toString().trim();

                if (validateUsername(username) && validateId(id) && validateEmail(email) && validatePassword(password)) {
                    if (!databaseHelper.checkUserExist(username, id, email)) {
                        if (databaseHelper.insertUserData(username, id, email, password) && databaseHelper.addUserComment(username) && databaseHelper.addUserScoreboard(username)) {
                            Toast.makeText(requireContext(), "Signup Successfully!", Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(getActivity()).saveUsername(username); // save the session
                            Intent intent = new Intent(requireContext(), UserActivity.class);
                            startActivity(intent);
                            getActivity().finish(); // prevents going back to this activity after signing up
                        } else {
                            Toast.makeText(requireContext(), "Signup Failed! Try again Later.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "User already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return rootView;
    }

    /**
     * Validates the username based on specific rules.
     *
     * @param username The username to validate.
     * @return true if the username is valid, false otherwise.
     */
    private boolean validateUsername(String username) {
        // Username must start with a letter (a-z,A-Z) and can include up to one digit, the length must be 6-12.
        if (username.length() < 6 || username.length() > 12) {
            Toast.makeText(requireContext(), "Invalid Username! Length must be between 6 and 12 characters.", Toast.LENGTH_LONG).show();
            return false;
        }
        char firstChar = username.charAt(0);
        if (!Character.isLetter(firstChar)) {
            Toast.makeText(requireContext(), "Invalid Username! It must start with a letter (a-z,A-Z).", Toast.LENGTH_LONG).show();
            return false;
        }
        boolean hasDigit = false;
        for (int i = 1; i < username.length(); i++) {
            if (Character.isDigit(username.charAt(i))) {
                if (hasDigit) {
                    Toast.makeText(requireContext(), "Invalid Username! Only one digit is allowed.", Toast.LENGTH_LONG).show();
                    return false;
                }
                hasDigit = true;
            } else if (!Character.isLetter(username.charAt(i))) {
                Toast.makeText(requireContext(), "Invalid Username! Only letters and one digit are allowed.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the ID based on specific rules.
     *
     * @param id The ID to validate.
     * @return true if the ID is valid, false otherwise.
     */
    private boolean validateId(String id) {
        // Id must include exactly 9 digits.
        if (id.length() != 9 || !TextUtils.isDigitsOnly(id)) {
            Toast.makeText(requireContext(), "Invalid ID! It must include exactly 9 digits.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Validates the email based on specific rules.
     *
     * @param email The email to validate.
     * @return true if the email is valid, false otherwise.
     */
    private boolean validateEmail(String email) {
        // Email must be a valid email.
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Invalid Email Address!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Validates the password based on specific rules.
     *
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    private boolean validatePassword(String password) {
        // Password must contain a capital letter (A-Z), a regular letter (a-z), special character (!,@, etc), the length must be 8-20.
        if (password.length() < 8 || password.length() > 20) {
            Toast.makeText(requireContext(), "Invalid Password! Length must be between 8 and 20 characters.", Toast.LENGTH_LONG).show();
            return false;
        }
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasSpecialChar = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
        if (!hasUpperCase || !hasLowerCase || !hasSpecialChar) {
            Toast.makeText(requireContext(), "Invalid password format! Password must contain a capital letter (A-Z), a lowercase letter (a-z), and a special character (!,@, etc), the length must be 8-20 characters.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
