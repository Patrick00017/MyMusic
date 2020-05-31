package com.example.mymusic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mymusic.AlbumDetailActivity;
import com.example.mymusic.Interface.IRecommendCallBack;
import com.example.mymusic.R;
import com.example.mymusic.adapter.RecommendListAdapter;
import com.example.mymusic.base.BaseApplication;
import com.example.mymusic.presenter.DetailPresenter;
import com.example.mymusic.presenter.RecommendPresenter;
import com.example.mymusic.utils.LogUtils;
import com.example.mymusic.view.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends BaseFragment implements IRecommendCallBack, UILoader.onNetWorkClickedListener {
    private static final String TAG = "RecommendFragment";

    private List<Album> m_data = new ArrayList<>();

    private UILoader m_uiLoader;
    private RecyclerView m_recommendDataListView;
    private RecommendPresenter m_recommendPresenter = RecommendPresenter.getInstance();
    private RecommendListAdapter m_recommendListAdapter;

    @Override
    protected View onSubViewCreated(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        m_recommendPresenter.RegisterCallBack(this);
        m_uiLoader = new UILoader(BaseApplication.getAppContext()) {
            @Override
            public View getSuccessfulView(ViewGroup container) {
                View view =  LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.fragment_recommend, container, false);
                initView(view);
                return view;
            }
        };
        m_uiLoader.setonNetWorkClickedListener(this);

        LogUtils.d(TAG, "start get data");
        m_recommendPresenter.getDataFromHIMA();

        if (m_uiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) m_uiLoader.getParent()).removeView(m_uiLoader);
            LogUtils.d(TAG,"unbind uiloader and viewgroup");
        }

        return m_uiLoader;
    }

    private void initView(View view) {
        m_recommendDataListView = view.findViewById(R.id.recommend_albums);
    }

    //=============================recommend call back==========================================
    @Override
    public void updataDataList(List<Album> albumList) {
        m_data = albumList;
        m_recommendListAdapter = new RecommendListAdapter(m_data);
        m_recommendListAdapter.setonAlbumClickedListener(new RecommendListAdapter.onAlbumClickedListener() {
            @Override
            public void onClick(Album album) {
                DetailPresenter presenter = DetailPresenter.getInstance();
                presenter.setTargetAlbum(album);
                Intent intent = new Intent(getContext(), AlbumDetailActivity.class);
                startActivity(intent);
            }
        });
        m_recommendDataListView.setAdapter(m_recommendListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BaseApplication.getAppContext());
        m_recommendDataListView.setLayoutManager(linearLayoutManager);

        m_uiLoader.changeStatusTo(UILoader.UI_STATUS.SUCCESSFUL);
    }

    @Override
    public void onNetWorkError() {
        m_uiLoader.changeStatusTo(UILoader.UI_STATUS.NETWORKERROR);
    }

    @Override
    public void onEmptyData() {
        m_uiLoader.changeStatusTo(UILoader.UI_STATUS.EMPTY);
    }
    //==============================================================================================


    @Override
    public void onDestroy() {
        super.onDestroy();
        m_recommendPresenter.unRegisterCallBack(this);
    }

    //网络错误时点击界面重试
    @Override
    public void onClick() {
        m_recommendPresenter.getDataFromHIMA();
    }
}
