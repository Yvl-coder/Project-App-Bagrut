package yuval.pinhas.bookwormsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/**
 * An adapter for displaying comments in a RecyclerView.
 *
 * @author Yuval Pinhas
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    /**
     * List of comments to be displayed
     */
    private List<Comment> comments;

    /**
     * Context field
     */
    static Context context;

    /**
     * Constructs a CommentsAdapter with the given context.
     * @param context The context in which the adapter will be used.
     */
    public CommentsAdapter(Context context) {
        this.context = context;
    }

    /**
     * Sets the comments to be displayed in the RecyclerView.
     * @param comments The list of comments to be displayed.
     */
    public void setComments(List<Comment> comments) {
        // Move current user's comment to the top of the list, if exists
        int currentUserIndex = -1;
        String currentUser = SharedPrefManager.getInstance(context).getUsername();
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getUsername().equals(currentUser)) {
                currentUserIndex = i;
                break;
            }
        }

        if (currentUserIndex != -1) {
            Comment currentUserComment = comments.remove(currentUserIndex);
            comments.add(0, currentUserComment);
        }

        this.comments = comments;
        notifyDataSetChanged(); // Refreshes the entire list when the data set (the list of Comment objects) has changed, ensuring the UI displays the latest data.
    }

    /**
     * Called when RecyclerView needs a new {@link CommentViewHolder} of the given type to represent
     * an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false); // Inflating the layout for an individual comment item
        return new CommentViewHolder(view); // Returning a new instance of CommentViewHolder
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method updates the
     * contents of the {@link CommentViewHolder#usernameTextView} and {@link CommentViewHolder#commentTextView}
     * with the data from the corresponding Comment object.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position); // Getting the comment object at the specified position
        holder.bind(comment); // Binding the comment data to the ViewHolder
    }

    /**
     * Returns The total number of items in this adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }
}
