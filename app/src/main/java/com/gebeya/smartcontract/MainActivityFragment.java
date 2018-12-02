/*
package com.gebeya.smartcontract;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gebeya.framework.base.BaseFragment;

import butterknife.BindView;

public class MainActivityFragment extends BaseFragment {

    @BindView(R.id.section_label)
    TextView mTextView;

    */
/**
     * The fragment argument representing the section number for this
     * fragment.
     *//*



    private static final String ARG_SECTION_NUMBER = "section_number";

    public MainActivityFragment() {
    }

    */
/**
     * Returns a new instance of this fragment for the given section
     * number.
     *//*



    public static MainActivityFragment newInstance(int sectionNumber) {
        MainActivityFragment fragment = new MainActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;


    }
}
*/
