package com.example.mymusic.presenter;

import com.example.mymusic.Interface.IPlayerCallBack;
import com.example.mymusic.Interface.IPlayerPresenter;
import com.example.mymusic.base.BaseApplication;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.service.IXmPlayerStatusListener;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import java.util.ArrayList;
import java.util.List;

public class PlayerPresenter implements IPlayerPresenter, IXmPlayerStatusListener {
    private static PlayerPresenter instance = null;
    private List<IPlayerCallBack> m_callBacks = new ArrayList<>();
    private XmPlayerManager m_playerManager = XmPlayerManager.getInstance(BaseApplication.getAppContext());
    private List<Track> m_tracks = new ArrayList<>();
    private int m_startPlayIndex = 0;

    private PlayerPresenter(){
        m_playerManager.addPlayerStatusListener(this);
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
        m_playerManager.seekTo(time);
    }

    @Override
    public void playPre() {
        m_playerManager.playPre();
        for(IPlayerCallBack callBack : m_callBacks){
            callBack.onPlayPre();
        }
    }

    @Override
    public void playNext() {
        m_playerManager.playNext();
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
        m_playerManager.playList(m_tracks, m_startPlayIndex);
    }

    public void setPlayListAndIndex(List<Track> playList, int index){
        m_tracks = playList;
        m_startPlayIndex = index;
    }

    public List<Track> getPlayList(){
        return m_tracks;
    }

    public int getPlayIndex(){
        return m_playerManager.getCurrentIndex();
    }

    public boolean isPlaying(){
        return m_playerManager.isPlaying();
    }

    public Track getPlayingTrack(){
        return m_tracks.get(m_playerManager.getCurrentIndex());
    }

    public int getPlayListSize(){
        return m_tracks.size();
    }

    //==============================================================================================
    @Override
    public void onPlayStart() {

    }

    @Override
    public void onPlayPause() {

    }

    @Override
    public void onPlayStop() {

    }

    @Override
    public void onSoundPlayComplete() {
    }

    @Override
    public void onSoundPrepared() {

    }

    @Override
    public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
        for(IPlayerCallBack callBack : m_callBacks){
            callBack.updateCoverAndTitle();
        }
    }

    @Override
    public void onBufferingStart() {

    }

    @Override
    public void onBufferingStop() {

    }

    @Override
    public void onBufferProgress(int i) {

    }

    @Override
    public void onPlayProgress(int current, int total) {
        for(IPlayerCallBack callBack : m_callBacks){
            callBack.onSeekTo(current, total);
        }
    }

    @Override
    public boolean onError(XmPlayerException e) {
        return false;
    }
}
