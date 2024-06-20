package yuval.pinhas.bookwormsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A fragment for displaying contact information.
 *
 * @author Yuval Pinhas
 */
public class ContactFragment extends Fragment {

    /**
     * Inflates the layout for the ContactFragment and sets up onClickListeners for the email and phone TextViews.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root View of the fragment's layout.
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        // Set onClickListener for email TextView
        TextView emailTextView = root.findViewById(R.id.email_text_view);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        // Set onClickListener for phone TextView
        TextView phoneTextView = root.findViewById(R.id.phone_text_view);
        phoneTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneNumber();
            }
        });

        return root;
    }

    /**
     * Sends an email using an email client.
     */
    private void sendEmail() {
        String[] TO = {"yuvalpinhas73@gmail.com"}; // Email recipient
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO); // Intent for sending email
        emailIntent.setData(Uri.parse("mailto:")); // Set data for email intent
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO); // Set email recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ticket About The \"Bookworms\" App"); // Set email subject
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear developer,\n(Write the email body...)\n\nGreetings,\n(Write your name...)"); // Set email body
        startActivity(Intent.createChooser(emailIntent, "Send email...")); // Start email intent with chooser
    }

    /**
     * Dials a phone number using the default phone app.
     */
    private void dialPhoneNumber() {
        String phoneNumber = "0556692388"; // Phone number to dial
        Intent intent = new Intent(Intent.ACTION_DIAL); // Intent for dialing phone number
        intent.setData(Uri.parse("tel:" + phoneNumber)); // Set data for dialing intent
        startActivity(intent); // Start dialing intent
    }
}
