package com.example.mymusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymusic.Interface.IDetailCallBack;
import com.example.mymusic.adapter.TrackListAdapter;
import com.example.mymusic.base.BaseApplication;
import com.example.mymusic.presenter.DetailPresenter;
import com.example.mymusic.presenter.PlayerPresenter;
import com.example.mymusic.utils.LogUtils;
import com.example.mymusic.view.UILoader;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public class AlbumDetailActivity extends AppCompatActivity implements IDetailCallBack {

    private static final String TAG = "AlbumDetailActivity";
    private RecyclerView m_track_list;
    private UILoader m_loader;
    private DetailPresenter m_detailPresenter = DetailPresenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_detail);
        ViewGroup content = findViewById(R.id.detail_content);
        m_loader = new UILoader(this) {
            @Override
            public View getSuccessfulView(ViewGroup container) {
                View view = LayoutInflater.from(container.getContext()).inflate(R.layout.detail_successful_view, container, false);
                m_track_list = view.findViewById(R.id.track_list);
                return view;
            }
        };
        content.addView(m_loader);


        m_detailPresenter.RegisterCallBack(this);
        requestData();
    }

    @Override
    protected void onDestroy() {
        m_detailPresenter.unRegisterCallBack(this);
        super.onDestroy();
    }

    private void requestData(){
        Album album = m_detailPresenter.getTargetAlbum();
        m_detailPresenter.getTrackList((int)album.getId(), 1);
    }

    //================================     detail call back     ========================================
    @Override
    public void updateUI(List<Track> tracks) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        m_track_list.setLayoutManager(manager);
        TrackListAdapter trackListAdapter = new TrackListAdapter(tracks);
        m_track_list.setAdapter(trackListAdapter);
        trackListAdapter.setOnAlbumClickedListener(new TrackListAdapter.OnAlbumClickedListener() {
            @Override
            public void onClick(int index) {
                PlayerPresenter presenter = PlayerPresenter.getInstance();
                presenter.setPlayListAndIndex(tracks, index);
                Intent intent = new Intent(getActivityContext(), PlayerActivity.class);
                startActivity(intent);
            }
        });
        m_loader.changeStatusTo(UILoader.UI_STATUS.SUCCESSFUL);
    }

    @Override
    public void onNetWokrError() {
        m_loader.changeStatusTo(UILoader.UI_STATUS.NETWORKERROR);
    }

    @Override
    public void onDataEmpty() {
        m_loader.changeStatusTo(UILoader.UI_STATUS.EMPTY);
    }
//==================================================================================================

    public Context getActivityContext(){
        return this;
    }
}
