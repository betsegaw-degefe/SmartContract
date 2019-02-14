package com.gebeya.smartcontract.view.aboutUs;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gebeya.framework.base.BaseFragment;
import com.gebeya.smartcontract.R;
import com.gebeya.smartcontract.databinding.FragmentAboutUsBinding;

public class AboutUsFragment extends BaseFragment {

    private static final int ANIM_DURATION = 5000;

    FragmentAboutUsBinding binding;
    AnimatorSet set = new AnimatorSet();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // binding view with the about us layout.
        binding = DataBindingUtil.inflate(inflater,
              R.layout.fragment_about_us, container, false);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        ObjectAnimator floatingAnimator = ObjectAnimator.ofFloat(
              binding.aboutUsIcon,
              "translationY",
              0, -60
        );

        floatingAnimator.setDuration(ANIM_DURATION);
        floatingAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        floatingAnimator.setRepeatMode(ObjectAnimator.REVERSE);

        set.play(floatingAnimator);
        set.start();


    }
}
