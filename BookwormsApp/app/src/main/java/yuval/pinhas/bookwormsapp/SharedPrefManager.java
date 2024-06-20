package yuval.pinhas.bookwormsapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manager class for handling shared preferences related to the Bookworms app.
 *
 * @author Yuval Pinhas
 */
public class SharedPrefManager {

    /**
     * Name of the shared preferences file
     */
    private static final String SHARED_PREF_NAME = "bookworms_app_shared_pref";

    /**
     * Singleton instance of the SharedPrefManager
     */
    private static SharedPrefManager instance;

    /**
     * SharedPreferences instance for accessing shared preferences
     */
    private final SharedPreferences sharedPreferences;

    /**
     * SharedPreferences.Editor instance for editing shared preferences
     */
    private final SharedPreferences.Editor editor;


    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the SharedPreferences and SharedPreferences.Editor instances.
     *
     * @param context The context of the application or activity.
     */
    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * Retrieves the singleton instance of SharedPrefManager.
     * because we are not using single thread (see QuotesFragment) synchronized is must to ensures that only one thread can access the instance variable at a time.
     *
     * @param context The context of the application or activity.
     * @return The singleton instance of SharedPrefManager.
     */
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    /**
     * Saves the provided username in shared preferences.
     *
     * @param username The username to be saved.
     */
    public void saveUsername(String username) {
        editor.putString("username", username);
        editor.apply();
    }

    /**
     * Retrieves the saved username from shared preferences.
     *
     * @return The saved username, or null if not found.
     */
    public String getUsername() {
        return sharedPreferences.getString("username", null);
    }

    /**
     * Clears the saved username from shared preferences.
     */
    public void clearUsername() {
        editor.remove("username");
        editor.apply();
    }
}
