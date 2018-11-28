package com.gebeya.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gebeya.framework.utils.Util;

import butterknife.ButterKnife;

/**
 * This class acts as the base Activity for all our other Activity classes.
 * It contains common functionality across all Activities, such as logging of
 * the Activity lifecycle methods.
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * Invoked when the Activity is first created from scratch.
     * This is where you would also specify what UI to display, using the
     * setContentView() method
     */

    /**
     * Convenience method to automatically invoke ButterKnife to create all the UI
     * bindings associated with the given Activity.
     * This method should be called in onCreate() above.
     */
    protected void bind() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d("< -------------------- onCreate(Bundle) -------------------- >");
    }

    /**
     * Invoked when the Activity is about to become visible to the screen.
     */
    @Override
    protected void onStart() {
        super.onStart();
        d("< -------------------- onStart() -------------------- >");
    }

    /**
     * Invoked when the Activity is now visible to the screen and the user
     * can now interact with it.
     */
    @Override
    protected void onResume() {
        super.onResume();
        d("< -------------------- onResume() -------------------- >");
    }

    /**
     * Invoked when the Activity is preparing/about to go into the background.
     */
    @Override
    protected void onPause() {
        super.onPause();
        d("< -------------------- onPause() -------------------- >");
    }

    /**
     * Invoked when the Activity is now in the background and its UI no longer
     * visible to the user on the screen.
     */
    @Override
    protected void onStop() {
        super.onStop();
        d("< -------------------- onStop() -------------------- >");
    }

    /**
     * Invoked by the system when the Activity is about to be killed/removed
     * from memory.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        d("< -------------------- onDestroy() -------------------- >");
    }

    /**
     * Send a simple Log debug message, using the Debug level.
     *
     * @param message Message to send.
     */
    protected void d(String message) {
        Util.d(this, message);
    }

    /**
     * Send a simple Log error message, using the Error level.
     *
     * @param message Message to send.
     */
    protected void e(String message) {
        Util.e(this, message);
    }

    /**
     * Show a toast message with a default length of short.
     *
     * @param message Message to show as the toast content.
     */
    protected void toast(String message) {
        toast(message, Toast.LENGTH_SHORT);
    }

    /**
     * Show a toast message with a provided length.
     *
     * @param message Message to show as the toast content.
     * @param length  Length of the toast message.
     */
    protected void toast(String message, int length) {
        Toast.makeText(this, message, length).show();
    }

}
