package com.match.app.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.match.app.utils.ScreenUtils;
import com.matches.fitness.R;
import com.match.app.base.BaseFragment;
import com.match.app.ui.home.activity.SelectGymActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAppointFragment extends BaseFragment {

    @BindView(R.id.iv_anim)
    ImageView iv_anim;
    @BindView(R.id.llSelectGym)
    LinearLayout llSelectGym;
    @BindView(R.id.llSelectDate)
    LinearLayout llSelectDate;

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
        ScreenUtils.setImageViewHeight(getActivity(), iv_anim);
        Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.appoint_anim);
        iv_anim.startAnimation(rotate);

        llSelectGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelectGymActivity.class));
            }
        });

        llSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), SelectGymActivity.class));
            }
        });
    }

}
