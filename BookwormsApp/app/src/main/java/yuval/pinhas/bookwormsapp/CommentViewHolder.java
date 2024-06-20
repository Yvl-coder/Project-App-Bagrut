package yuval.pinhas.bookwormsapp;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder class for holding views associated with individual comments.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    /**
     * TextView for displaying the username
     */
    TextView usernameTextView;

    /**
     * TextView for displaying the comment
     */
    TextView commentTextView;

    /**
     * Constructs a CommentViewHolder.
     * @param itemView The view associated with the ViewHolder.
     */
    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameTextView = itemView.findViewById(R.id.username_text_view);
        commentTextView = itemView.findViewById(R.id.comment_text_view);
    }

    /**
     * Binds a Comment object to the views in the ViewHolder.
     * @param comment The Comment object to bind.
     */
    public void bind(Comment comment) {
        String username = comment.getUsername(); // The user who commented
        String usernameForTextView = " " + username + ":"; // Username to be displayed in TextView
        String currUsername = SharedPrefManager.getInstance(CommentsAdapter.context).getUsername();

        // Create a SpannableString to apply underline to username
        SpannableString spannableString = new SpannableString(usernameForTextView);

        // Apply underline to username
        if (username.equals(currUsername)) // Checking if the comment is from the current user
            spannableString = new SpannableString(spannableString + " (You)");
        spannableString.setSpan(new UnderlineSpan(), 1, usernameForTextView.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set text with underline to the username TextView
        usernameTextView.setText(spannableString);
        commentTextView.setText(comment.getComment()); // Set the comment text
    }
}
