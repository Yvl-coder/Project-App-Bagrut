package yuval.pinhas.bookwormsapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment to display various challenges available for users.
 *
 * @author Yuval Pinhas
 */
public class ChallengesFragment extends Fragment {

    /**
     * Inflates the layout for the ChallengesFragment.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);


        view.findViewById(R.id.challenge1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start FastTypingActivity when challenge 1 is clicked
                Intent intent = new Intent(getActivity(), FastTypingActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.challenge2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start FastTalkingActivity when challenge 2 is clicked
                Intent intent = new Intent(getActivity(), FastTalkingActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.scoreboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ScoreboardActivity when scoreboard is clicked
                Intent intent = new Intent(getActivity(), ScoreboardActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
