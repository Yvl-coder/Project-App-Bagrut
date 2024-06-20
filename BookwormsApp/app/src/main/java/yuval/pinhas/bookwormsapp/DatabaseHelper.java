package yuval.pinhas.bookwormsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a SQLite database helper for managing users, comments, and scoreboard tables
 * in the Bookworms app.
 *
 * @author Yuval Pinhas
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * The users table name
     */
    private static final String USERS_TABLE_NAME = "users";

    /**
     * The comments table name
     */
    private static final String COMMENTS_TABLE_NAME = "comments";

    /**
     * The scoreboard table name
     */
    private static final String SCOREBOARD_TABLE_NAME = "scoreboard";

    /**
     * The database name
     */
    private static final String DATABASE_NAME = "appDB.db";

    /**
     * The amount of books in the library
     */
    private static final int AMOUNT_OF_BOOKS = 16;

    /**
     * Constructs a new DatabaseHelper instance.
     * Context provides access to application-specific resources. Such as Database.
     *
     * @param context The context of the application.
     */
    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("create Table " + USERS_TABLE_NAME + "(username TEXT primary key, id TEXT, email TEXT, password TEXT)");

        // Create comments table
        StringBuilder createTableCommand = new StringBuilder("create Table " + COMMENTS_TABLE_NAME + " (username TEXT primary key"); // Start creating the SQL command, using StringBuilder for mutable String
        for (int i = 1; i <= AMOUNT_OF_BOOKS; i++) { // Loop to add the book comment columns
            createTableCommand.append(", book").append(i).append("_comment TEXT"); // making the field named 'book(i)_comment'
        }
        createTableCommand.append(")"); // Close the SQL command
        db.execSQL(createTableCommand.toString());

        // Create scoreboard table
        db.execSQL("create Table " + SCOREBOARD_TABLE_NAME + "(username TEXT primary key, fast_typing_best REAL, fast_talking_best REAL)");
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("drop Table if exists " + USERS_TABLE_NAME);
        db.execSQL("drop Table if exists " + COMMENTS_TABLE_NAME);
        db.execSQL("drop Table if exists " + SCOREBOARD_TABLE_NAME);
        onCreate(db);
    }


    // User related methods:

    /**
     * Inserts user data into the users table.
     *
     * @param username The username.
     * @param id       The user ID.
     * @param email    The user email.
     * @param password The user password.
     * @return True if the operation is successful, false otherwise.
     */
    public Boolean insertUserData(String username, String id, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("id", id);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = db.insert(USERS_TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    /**
     * Checks if a user with the given username, ID, and email exists.
     *
     * @param username The username.
     * @param id       The user ID.
     * @param email    The user email.
     * @return True if the user exists, false otherwise.
     */
    public Boolean checkUserExist(String username, String id, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE username = ? AND id = ? AND email = ?", new String[]{username, id, email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    /**
     * Checks if the username and password combination matching.
     *
     * @param username The username.
     * @param password The user password.
     * @return True if the username and password match, false otherwise.
     */
    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE username = ? AND password = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    /**
     * Changes the password for the given username.
     *
     * @param username    The username.
     * @param newPassword The new password.
     * @return True if the password is successfully changed, false otherwise.
     */
    public boolean changePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        int updatedRows = db.update(USERS_TABLE_NAME, values, "username = ?", new String[]{username});
        db.close();
        return updatedRows > 0;
    }

    /**
     * Retrieves the user ID for the given username.
     *
     * @param username The username.
     * @return The user ID.
     */
    public String getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userId = "";
        Cursor cursor = db.query(USERS_TABLE_NAME, new String[]{"id"}, "username = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("id");
            if (columnIndex != -1) {
                userId = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        db.close();
        return userId;
    }

    /**
     * Retrieves the user email for the given username.
     *
     * @param username The username.
     * @return The user email.
     */
    public String getUserEmail(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userEmail = "";
        Cursor cursor = db.query(USERS_TABLE_NAME, new String[]{"email"}, "username = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("email");
            if (columnIndex != -1) {
                userEmail = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        db.close();
        return userEmail;
    }

    /**
     * Checks if the given ID exists for any user other than the current user.
     *
     * @param username The current username.
     * @param id       The ID to check.
     * @return True if the ID exists for another user, false otherwise.
     */
    public boolean checkIdExistsExceptCurrUser(String username, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE id = ? AND username != ?", new String[]{id, username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    /**
     * Checks if the given email exists for any user other than the current user.
     *
     * @param username The current username.
     * @param email    The email to check.
     * @return True if the email exists for another user, false otherwise.
     */
    public boolean checkEmailExistsExceptCurrUser(String username, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE email = ? AND username != ?", new String[]{email, username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    /**
     * Updates the user details (ID and email) for the given username.
     *
     * @param username The username.
     * @param newId    The new user ID.
     * @param newEmail The new user email.
     * @return True if the user details are successfully updated, false otherwise.
     */
    public boolean updateUserDetails(String username, String newId, String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", newId);
        values.put("email", newEmail);
        int updatedRows = db.update(USERS_TABLE_NAME, values, "username = ?", new String[]{username});
        db.close();
        return updatedRows > 0;
    }


    // Comment related methods:

    /**
     * Adds a new user comment entry with empty comments for all books.
     *
     * @param username The username.
     * @return True if the operation is successful, false otherwise.
     */
    public Boolean addUserComment(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        for (int i = 1; i <= AMOUNT_OF_BOOKS; i++)
            contentValues.put("book" + i + "_comment", "");

        long result = db.insert(COMMENTS_TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    /**
     * Adds or updates a comment for a specific book for the given username.
     *
     * @param username   The username.
     * @param bookNumber The book number.
     * @param comment    The comment.
     * @return True if the operation is successful, false otherwise.
     */
    public boolean addOrUpdateComment(String username, int bookNumber, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book" + bookNumber + "_comment", comment);
        int updatedRows = db.update(COMMENTS_TABLE_NAME, values, "username = ?", new String[]{username});
        if (updatedRows == 0) { // No row updated, likely the user's first comment
            ContentValues newValues = new ContentValues();
            newValues.put("username", username);
            newValues.put("book" + bookNumber + "_comment", comment);
            db.insert(COMMENTS_TABLE_NAME, null, newValues);
        }
        db.close();
        return true;
    }

    /**
     * Retrieves the comment for a specific book for the given username.
     *
     * @param username   The username.
     * @param bookNumber The book number.
     * @return The comment for the book.
     */
    public String getBookComment(String username, int bookNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String comment = "";
        Cursor cursor = db.query(COMMENTS_TABLE_NAME, new String[]{"book" + bookNumber + "_comment"}, "username = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("book" + bookNumber + "_comment");
            if (columnIndex != -1) {
                comment = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        db.close();
        return comment;
    }

    /**
     * Retrieves all comments for a specific book.
     *
     * @param bookNumber The book number.
     * @return A list of comments for the book.
     */
    public List<Comment> getAllCommentsForBook(int bookNumber) {
        List<Comment> comments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = new String[]{"username", "book" + bookNumber + "_comment"};
        Cursor cursor = db.query(COMMENTS_TABLE_NAME, projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String comment = cursor.getString(cursor.getColumnIndexOrThrow("book" + bookNumber + "_comment"));

            if (!comment.isEmpty()) {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                comments.add(new Comment(username, comment));
            }
        }

        cursor.close();
        db.close();

        return comments;
    }


    // Scoreboard related methods:

    /**
     * Adds a new user scoreboard entry with maximum scores for fast typing and fast talking.
     *
     * @param username The username.
     * @return True if the operation is successful, false otherwise.
     */
    public Boolean addUserScoreboard(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("fast_talking_best", Double.MAX_VALUE); // for making the first score to be the best
        contentValues.put("fast_typing_best", Double.MAX_VALUE); // for making the first score to be the best

        long result = db.insert(SCOREBOARD_TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    /**
     * Updates the fast typing score for the given username if the new score is better than the current best.
     *
     * @param username The username.
     * @param newScore The new fast typing score.
     * @param oldScore The old best fast typing score.
     * @return True if the score is successfully updated, false otherwise.
     */
    public boolean updateFastTypingScore(String username, double newScore, double oldScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("fast_typing_best", Math.min(newScore, oldScore));
        int updatedRows = db.update(SCOREBOARD_TABLE_NAME, values, "username = ?", new String[]{username});
        db.close();

        return updatedRows > 0;
    }

    /**
     * Retrieves the best fast typing score for the given username.
     *
     * @param username The username.
     * @return The best fast typing score.
     */
    public double getFastTypingScore(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        double score = 0.0;
        Cursor cursor = db.query(SCOREBOARD_TABLE_NAME, new String[]{"fast_typing_best"}, "username = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("fast_typing_best");
            if (columnIndex != -1) {
                score = cursor.getDouble(columnIndex);
            }
            cursor.close();
        }
        db.close();
        return score;
    }

    /**
     * Updates the fast talking score for the given username if the new score is better than the current best.
     *
     * @param username The username.
     * @param newScore The new fast talking score.
     * @param oldScore The old best fast talking score.
     * @return True if the score is successfully updated, false otherwise.
     */
    public boolean updateFastTalkingScore(String username, double newScore, double oldScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("fast_talking_best", Math.min(newScore, oldScore));
        int updatedRows = db.update(SCOREBOARD_TABLE_NAME, values, "username = ?", new String[]{username});
        db.close();

        return updatedRows > 0;
    }

    /**
     * Retrieves the best fast talking score for the given username.
     *
     * @param username The username.
     * @return The best fast talking score.
     */
    public double getFastTalkingScore(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        double score = 0.0;
        Cursor cursor = db.query(SCOREBOARD_TABLE_NAME, new String[]{"fast_talking_best"}, "username = ?", new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("fast_talking_best");
            if (columnIndex != -1) {
                score = cursor.getDouble(columnIndex);
            }
            cursor.close();
        }
        db.close();
        return score;
    }

    /**
     * Retrieves the username with the minimum value of the best fast typing score.
     *
     * @return The username with the minimum fast typing score.
     */
    public String getUsernameWithMinFastTypingBest() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM " + SCOREBOARD_TABLE_NAME + " WHERE fast_typing_best != " + Double.MAX_VALUE + " ORDER BY fast_typing_best ASC LIMIT 1", null);
        String username = null;
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("username");
            if (columnIndex != -1) {
                username = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        db.close();
        return username;
    }

    /**
     * Retrieves the username with the minimum value of the best fast talking score.
     *
     * @return The username with the minimum fast talking score.
     */
    public String getUsernameWithMinFastTalkingBest() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM " + SCOREBOARD_TABLE_NAME + " WHERE fast_talking_best != " + Double.MAX_VALUE + " ORDER BY fast_talking_best ASC LIMIT 1", null);
        String username = null;
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("username");
            if (columnIndex != -1) {
                username = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        db.close();
        return username;
    }

    // All tables related methods:
    /**
     * Deletes the user with the given username and associated data from all tables.
     *
     * @param username The username.
     * @return True if the user is successfully deleted, false otherwise.
     */
    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete user from users table
        int usersDeleted = db.delete(USERS_TABLE_NAME, "username = ?", new String[]{username});

        // Delete user's comments from comments table
        int commentsDeleted = db.delete(COMMENTS_TABLE_NAME, "username = ?", new String[]{username});

        // Delete user's scoreboard from scoreboard table
        int scoreboardDeleted = db.delete(SCOREBOARD_TABLE_NAME, "username = ?", new String[]{username});

        db.close();

        // Check if all delete operations were successful
        return usersDeleted > 0 && commentsDeleted > 0 && scoreboardDeleted > 0;
    }
}
