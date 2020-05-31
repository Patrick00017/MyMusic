package com.example.mymusic.presenter;

import com.example.mymusic.Interface.IPlayerCallBack;
import com.example.mymusic.Interface.IPlayerPresenter;
import com.example.mymusic.base.BaseApplication;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import java.util.ArrayList;
import java.util.List;

public class PlayerPresenter implements IPlayerPresenter {
    private static PlayerPresenter instance = null;
    private List<IPlayerCallBack> m_callBacks = new ArrayList<>();
    private XmPlayerManager m_playerManager = XmPlayerManager.getInstance(BaseApplication.getAppContext());
    private List<Track> m_tracks = new ArrayList<>();
    private int m_playIndex = 0;

    private PlayerPresenter(){
    }

    public static PlayerPresenter getInstance(){
        if(instance == null){
            synchronized (PlayerPresenter.class){
                if(instance==null){
                    instance = new PlayerPresenter();
                }
            }
        }

        return instance;
    }

    @Override
    public void play() {
        m_playerManager.play();
        for(IPlayerCallBack callBack : m_callBacks){
            callBack.onPlay();
        }
    }

    @Override
    public void pause() {
        m_playerManager.pause();
        for(IPlayerCallBack callBack : m_callBacks){
            callBack.onTrackPause();
        }
    }

    @Override
    public void seekTo(int time) {

    }

    @Override
    public void playPre() {
        m_playerManager.playPre();
        m_playIndex--;
        for(IPlayerCallBack callBack : m_callBacks){
            callBack.onPlayPre();
        }
    }

    @Override
    public void playNext() {
        m_playerManager.playNext();
        m_playIndex++;
        for(IPlayerCallBack callBack : m_callBacks){
            callBack.onPlayNext();
        }
    }

    @Override
    public void RegisterCallBack(IPlayerCallBack callBack) {
        if (callBack != null) {
            m_callBacks.add(callBack);
        }
    }

    @Override
    public void unRegisterCallBack(IPlayerCallBack callBack) {
        m_callBacks.remove(callBack);
    }

    public void initPlayList(){
        m_playerManager.playList(m_tracks, m_playIndex);
    }

    public void setPlayListAndIndex(List<Track> playList, int index){
        m_tracks = playList;
        m_playIndex = index;
    }

    public List<Track> getPlayList(){
        return m_tracks;
    }

    public int getPlayIndex(){
        return m_playIndex;
    }

    public boolean isPlaying(){
        return m_playerManager.isPlaying();
    }

    public Track getPlayingTrack(){
        return m_tracks.get(m_playIndex);
    }

    public int getPlayListSize(){
        return m_tracks.size();
    }
}
