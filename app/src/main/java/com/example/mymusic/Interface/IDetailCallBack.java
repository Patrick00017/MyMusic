package com.example.mymusic.Interface;

import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public interface IDetailCallBack {
    void updateUI(List<Track> tracks);

    void onNetWokrError();

    void onDataEmpty();
}
