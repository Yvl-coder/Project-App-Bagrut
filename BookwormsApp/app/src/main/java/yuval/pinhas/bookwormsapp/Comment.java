package yuval.pinhas.bookwormsapp;

/**
 * Represents a comment made by a user.
 *
 * @author Yuval Pinhas
 */
public class Comment {

    /**
     * The username of the user who made the comment
     */
    private String username;

    /**
     * The actual comment made by the user
     */
    private String comment;


    /**
     * Constructs a Comment object with the provided username and comment.
     * @param username The username of the user who made the comment.
     * @param comment The actual comment made by the user.
     */
    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    /**
     * Gets the username of the user who made the comment.
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the actual comment made by the user.
     * @return The comment made by the user.
     */
    public String getComment() {
        return comment;
    }
}
