package com.example.mymusic.Interface;

public interface IPlayerCallBack {

    void onPlay();

    void onTrackPause();

    void onSeekTo(int currentPos, int total);

    void onPlayPre();

    void onPlayNext();

    void updateCoverAndTitle();

}
