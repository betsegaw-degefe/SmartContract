package com.gebeya.framework.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gebeya.framework.utils.Util;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment {

    /**
     * Contains the Fragment's View, inflated from the overridden onCreateView() callback.
     */
    protected View root;

    /**
     * Convenience method to inflate the given layout resource ID and automatically assign the
     * inflated View object to the root, defined at the top.
     *
     * @param layoutId  layout resource ID to inflate from.
     * @param container container ViewGroup, as required by the LayoutInflater.
     */
    protected void inflate(int layoutId, ViewGroup container) {
        LayoutInflater inflater = getLayoutInflater();
        root = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, root);
    }

    /**
     * Called when the Fragment has just been attached to the Activity. This call is invoked right
     * after the Activity's onCreate() method has been invoked and before the Fragment's onCreate()
     * method is invoked.
     *
     * @param context Context to which the Fragment has been attached to (such as the Activity).
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        d("< ---------- onAttach(Context) ---------- >");
    }

    /**
     * Invoked when the Fragment is initialized for the first time. This call is not invoked when
     * the Fragment instance is created via the constructor, but when it is being created for the
     * first time, either from a FragmentTransaction or from the <fragment /> tag.
     *
     * @param savedInstanceState State information regarding the Fragment.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d("< ---------- onCreate(Bundle) ---------- >");
    }

    /**
     * Invoked when the Fragment is about to become visible to the screen.
     */
    @Override
    public void onStart() {
        super.onStart();
        d("< ---------- onStart() ---------- >");
    }

    /**
     * Final call when the Fragment is now visible on the screen and the user can now interact with
     * it.
     */
    @Override
    public void onResume() {
        super.onResume();
        d("< ---------- onResume() ---------- >");
    }

    /**
     * Invoked when the Fragment is preparing/about to go into the background.
     */
    @Override
    public void onPause() {
        super.onPause();
        d("< ---------- onPause() ---------- >");
    }

    /**
     * Invoked when the Fragment is now in the background and its UI no longer visible to the user
     * on the screen, either because it is being replaced, or the Activity is moving into the
     * background.
     */
    @Override
    public void onStop() {
        super.onStop();
        d("< ---------- onStop() ---------- >");
    }

    /**
     * Invoked by the system when the Fragment is about to be killed/removed from memory, either
     * because it is being replaced or its hosting Activity is being killed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        d("< ---------- onDestroy() ---------- >");
    }

    /**
     * Invoked when the the Fragment is being detached from the Activity host, such as during the
     * remove() TransactionDTO or replace() TransactionDTO. Invoked after the onDestroy() callback.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        d("< ---------- onDetach() ---------- >");
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
     * Show a toast message using the default length of short for the duration.
     *
     * @param resId The string resource ID to lookup.
     */
    protected void toast(int resId) {
        toast(resId, Toast.LENGTH_SHORT);
    }

    /**
     * Show a toast message using the provided duration length for the duration.
     *
     * @param resId    The string resource ID to lookup.
     * @param duration Provided duration for the toast message.
     */
    protected void toast(int resId, int duration) {
        final String s = getString(resId);
        toast(s, duration);
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
        Activity activity = getActivity();      // Could be null in some cases
        if (activity != null) {
            Toast.makeText(activity, message, length).show();
        }
    }



}
