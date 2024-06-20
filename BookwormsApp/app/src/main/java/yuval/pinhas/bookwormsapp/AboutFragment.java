package yuval.pinhas.bookwormsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment responsible for displaying information about the app.
 * It includes a spinner to select questions and a text view to display answers.
 *
 * @author Yuval Pinhas
 */
public class AboutFragment extends Fragment {

    /**
     * Spinner to select questions.
     */
    private Spinner questionSpinner;

    /**
     * TextView to display the answers corresponding to the selected question.
     */
    private TextView answerTextView;

    /**
     * Array containing questions.
     */
    private final String[] questions = {
            "1. Can you Introduce Yourself?",
            "2. Why Did You Develop This App?",
            "3. Is This App Free?",
            "4. Is There a Registration Process?",
            "5. There is a library?",
            "6. What is the quality and the amount of the books in the app?",
            "7. Where Can I Write A Review?"
    };

    /**
     * Array containing answers corresponding to each question.
     */
    private final String[] answers = {
            "My name is Yuval Pinhas, I am 18 years old. As part of the computer science curriculum we were needed to build an app about a subject we want, I chose books.",
            "I found that apps for real book fans don't supply the best resources to discover the best book for you and have the community similar to you! So, I decided to develop this app and please the bookworms among us.",
            "OF COURSE! This app is a non-profit app! I only want you to enjoy as much as you can!",
            "Yes! Reach the register icon and click it!",
            "Login to find ;) You will do fine, this app is easy to understand!",
            "Currently there are 16 books, and the amount always increasing! All the books there are A-M-A-Z-I-N-G!",
            "I will be more than happy to listen to you, and improve if necessary. You can mail me your review, and I will check it as soon as possible. Find my mail in \"Contact\"."
    };

    /**
     * Inflates the layout for the fragment, initializes UI components,
     * and sets up the spinner and its listener.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState This fragment's previously saved state, if any.
     * @return The View for the fragment's UI.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Initialize UI components
        questionSpinner = view.findViewById(R.id.question_spinner);
        answerTextView = view.findViewById(R.id.answer_text_view);

        // Create an ArrayAdapter for the spinner and set its adapter. The Adapter acts as a bridge between the UI Component and the Data Source.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, questions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionSpinner.setAdapter(adapter);

        // Set up the spinner's item selection listener
        questionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Display the corresponding answer when a question is selected
                answerTextView.setText(answers[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return view;
    }
}
