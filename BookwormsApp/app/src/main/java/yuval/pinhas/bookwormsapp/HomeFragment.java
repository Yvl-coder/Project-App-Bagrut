package yuval.pinhas.bookwormsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A fragment representing the Home page of the Bookworms app.
 * It displays a set of videos loaded from YouTube.
 *
 * @author Yuval Pinhas
 */
public class HomeFragment extends Fragment {

    /**
     * Array to hold WebView instances for displaying videos.
     */
    private WebView[] videos;

    /**
     * Array of URLs for the videos to be loaded.
     */
    private String[] urls;


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
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the WebView array and the URLs array
        videos = new WebView[]{
                root.findViewById(R.id.video1),
                root.findViewById(R.id.video2),
                root.findViewById(R.id.video3),
                root.findViewById(R.id.video4),
                root.findViewById(R.id.video5)
        };

        urls = new String[]{
                // URLs of the videos
                "https://www.youtube.com/embed/SKVcQnyEIT8?si=Mbg-nSv4f4KLlgAy&fs=0",
                "https://www.youtube.com/embed/fqhSlTCw-Kg?si=wQGcw1L0yTQbydQy&fs=0",
                "https://www.youtube.com/embed/eTFy8RnUkoU?si=F-R0G_gy4pNmuM5z&fs=0",
                "https://www.youtube.com/embed/WFXbYfCtVdc?si=B6qx_1APJaGnChpH&fs=0",
                "https://www.youtube.com/embed/RlvTw8kcP6A?si=pi0BEYAHkmZwkimU&fs=0"
        };

        // Load the videos
        loadVideos();

        return root;
    }

    /**
     * Load the videos into the WebView instances.
     */
    private void loadVideos() {
        for (int i = 0; i < videos.length; i++) {
            initializeVideo(videos[i], urls[i]);
        }
    }

    /**
     * Initialize a video in the WebView.
     *
     * @param video The WebView instance to load the video into.
     * @param url   The URL of the video.
     */
    private void initializeVideo(WebView video, String url) {
        video.setBackgroundColor(0); // Set background color to fully transparent
        video.getSettings().setJavaScriptEnabled(true); // Enable JavaScript
        video.loadUrl(url); // Load URL directly instead of using loadData saving performance
        video.setWebChromeClient(new WebChromeClient()); // Set a WebChromeClient for video playback
    }
}
