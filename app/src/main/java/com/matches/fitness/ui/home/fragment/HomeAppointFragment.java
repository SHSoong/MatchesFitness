package com.matches.fitness.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.matches.fitness.R;
import com.matches.fitness.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAppointFragment extends BaseFragment {

    @BindView(R.id.iv_anim)
    ImageView iv_anim;


    public static Fragment newInstance() {
        return new HomeAppointFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homeappoint, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.appoint_anim);
        iv_anim.startAnimation(rotate);
    }

}
