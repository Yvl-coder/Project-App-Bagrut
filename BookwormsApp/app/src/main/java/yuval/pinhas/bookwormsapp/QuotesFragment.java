package yuval.pinhas.bookwormsapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Fragment for displaying and fetching quotes from an external API.
 *
 * @author Yuval Pinhas
 */
public class QuotesFragment extends Fragment {

    /**
     * Holds the base URL for the API from which quotes are fetched.
     * <a href="https://rapidapi.com/apininjas/api/quotes-by-api-ninjas/">Guide For The API</a>
     */
    private String API_URL = "https://quotes-by-api-ninjas.p.rapidapi.com/v1/quotes?category=";

    /**
     * Instance of ProgressDialog, which is a dialog that indicates the progress of an operation.
     * Show a progress dialog while fetching a quote.
     */
    private ProgressDialog progressDialog;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);

        // Fetch a quote with the category selected = happiness
        view.findViewById(R.id.category1_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchQuote("Happiness");
            }
        });

        // Fetch a quote with the category selected = success
        view.findViewById(R.id.category2_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchQuote("Success");
            }
        });

        // Fetch a quote with the category selected = inspirational
        view.findViewById(R.id.category3_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchQuote("Inspirational");
            }
        });

        return view;
    }

    /**
     * Method to fetch a quote from the API based on the given category.
     *
     * @param category The category of the quote to fetch.
     */
    private void fetchQuote(String category) {
        progressDialog = new ProgressDialog(getActivity()); // Initializes a new ProgressDialog instance
        progressDialog.setMessage("Fetching quote..."); // Sets the message for the progress dialog
        progressDialog.setCancelable(false); // Makes the progress dialog not cancelable
        progressDialog.show(); // Shows the progress dialog

        /*
          Creates a new thread to perform network operations, that enabling the access to the API.
          Performing network operations on the main thread can cause the app to freeze or become
          unresponsive. So new thread is being in use.
         */
        new Thread(new Runnable() {
            @Override
            public void run() { // Overrides the run method of the thread
                try {
                    URL url = new URL(API_URL + category); // Constructs the URL for the API
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Opens an HTTP connection to the URL
                    connection.setRequestMethod("GET"); // Sets the HTTP request method to GET
                    connection.setRequestProperty("X-RapidAPI-Key", "0fccd856f8mshab26489bc09c5c6p18fe15jsne5d5bf743479"); // Sets the API key header
                    connection.setRequestProperty("X-RapidAPI-Host", "quotes-by-api-ninjas.p.rapidapi.com"); // Sets the API host header

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); // Reads the response from the connection
                    StringBuilder response = new StringBuilder(); // Initializes a StringBuilder to hold the response
                    String line; // Declares a string variable to hold each line of the response
                    while ((line = reader.readLine()) != null) { // Reads each line of the response until it's null
                        response.append(line); // Appends each line to the StringBuilder
                    }
                    reader.close(); // Closes the BufferedReader

                    String jsonResponse = response.toString(); // Converts the StringBuilder to a string

                    /*
                      Creates a new handler to post a runnable on the main thread, makes that after fetching the quote, this thread is no longer exist.
                      Force the program to wait until this thread is ending, and then running the process on the main thread.
                      Avoiding errors that can cause because values are not been set until this thread finished.
                    */
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            showQuoteDialog(jsonResponse, category); // Show the quote dialog, using showQuoteDialog method
                        }
                    });

                    connection.disconnect(); // Disconnects the HTTP connection
                } catch (Exception e) { // Catches any exceptions that occurred
                    e.printStackTrace(); // Prints the stack trace of the exception
                } finally {
                    progressDialog.dismiss(); // Dismisses the progress dialog
                }
            }
        }).start(); // Starts the thread
    }

    /**
     * Method to show a dialog displaying the fetched quote and author.
     *
     * @param jsonResponse The JSON response containing the quote and author.
     * @param category     The category of the quote.
     */
    private void showQuoteDialog(String jsonResponse, String category) {
        try { // Handles exceptions that may occur during JSON parsing
            JSONObject jsonObject = new JSONArray(jsonResponse).getJSONObject(0); // Retrieves the first JSON object from the array, the API returns only a single object
            String quote = jsonObject.getString("quote"); // Retrieves the "quote" field value from the JSON object
            String author = jsonObject.getString("author"); // Retrieves the "author" field value from the JSON object.

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // Creates a new AlertDialog.Builder instance
            builder.setTitle(category); // Sets the title of the dialog to the category
            builder.setMessage(quote + "\n\n- " + author) // Sets the message of the dialog to the quote and author
                    .setPositiveButton("Next", new DialogInterface.OnClickListener() { // Sets a positive button for the dialog
                        public void onClick(DialogInterface dialog, int id) { // Defines the click listener for the positive button
                            fetchQuote(category); // Calls the fetchQuote method with the current category
                        }
                    })
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() { // Sets a negative button for the dialog
                        public void onClick(DialogInterface dialog, int id) { // Defines the click listener for the negative button
                            dialog.dismiss(); // Dismisses the dialog
                        }
                    });
            Dialog dialog = builder.create(); // Creates the AlertDialog from the builder
            dialog.show(); // Shows the dialog
        } catch (JSONException e) { // Catches JSON parsing exceptions
            e.printStackTrace(); // Prints the stack trace of the exception
        }
    }
}
