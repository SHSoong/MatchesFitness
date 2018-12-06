package com.match.app.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.match.app.base.BaseFragment;
import com.match.app.ui.adapter.SwipeStackAdapter;
import com.match.app.ui.home.activity.FilterActivity;
import com.match.app.ui.login.DateChoiceActivity;
import com.matches.fitness.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.fls.swipestack.SwipeStack;

public class HomePairFragment extends BaseFragment {

    @BindView(R.id.swipeStack)
    SwipeStack swipeStack;
    @BindView(R.id.rlFilter)
    RelativeLayout rlFilter;
    @BindView(R.id.rlYes)
    RelativeLayout rlYes;
    @BindView(R.id.rlNotice)
    RelativeLayout rlNotice;

    private List<Integer> list = new ArrayList<>();
    private SwipeStackAdapter adapter;
    private int curIndex = 0;

    public static Fragment newInstance() {
        return new HomePairFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = inflater.inflate(R.layout.fragment_homepair, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initSwipeCards();
        showSwipeCards();
    }

    private void initView() {
        rlFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FilterActivity.class));
            }
        });
        rlYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeStack.swipeTopViewToRight();
            }
        });
        rlNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mActivity, DateChoiceActivity.class));
            }
        });
    }

    private void initData() {
        list.clear();
        list.add(R.mipmap.img_avatar_01);
        list.add(R.mipmap.img_avatar_02);
        list.add(R.mipmap.img_avatar_03);
        list.add(R.mipmap.img_avatar_04);
        list.add(R.mipmap.img_avatar_05);
        list.add(R.mipmap.img_avatar_06);
        list.add(R.mipmap.img_avatar_07);
    }

    private void initSwipeCards() {


//        swipeCardsView.retainLastCard(true);
//        swipeCardsView.enableSwipe(true);
//        swipeCardsView.getTouchscreenBlocksFocus();
//        //设置滑动监听
//        swipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {
//
//            @Override
//            public void onShow(int index) {
//                curIndex = index;
//            }
//
//            @Override
//            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
//                switch (type) {
//                    case LEFT:
//                        break;
//                    case RIGHT:
//                        break;
//                }
//            }
//
//            @Override
//            public void onItemClick(View cardImageView, int index) {
//            }
//        });

    }

    /**
     * 显示cardsview
     */
    private void showSwipeCards() {
        swipeStack.setAdapter(new SwipeStackAdapter(getActivity(), list));
    }

}
