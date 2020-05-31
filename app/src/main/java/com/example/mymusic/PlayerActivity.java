package com.example.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mymusic.Interface.IPlayerCallBack;
import com.example.mymusic.presenter.PlayerPresenter;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.List;

public class PlayerActivity extends AppCompatActivity implements IPlayerCallBack {

    private PlayerPresenter m_playerPresenter = PlayerPresenter.getInstance();
    private ImageView m_trackCover;
    private TextView m_trackTitle;
    private ImageView m_playPre;
    private ImageView m_playOrPause;
    private ImageView m_playNext;
    private static Boolean m_isPlaying = false;
    private Track m_playingTrack = null;
    private SeekBar m_playBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        m_playerPresenter.RegisterCallBack(this);
        m_playerPresenter.initPlayList();
        m_isPlaying = m_playerPresenter.isPlaying();
        m_playingTrack = m_playerPresenter.getPlayingTrack();
        initView();
        showTrackCoverAndTitle();
        checkPlayBorder();
        initListener();
    }

    private void initListener() {
        m_playPre.setOnClickListener((v) -> {
            m_playerPresenter.playPre();
        });

        m_playOrPause.setOnClickListener((v) -> {
            if(m_isPlaying){
                m_playerPresenter.pause();
            }
            else{
                m_playerPresenter.play();
            }
        });

        m_playNext.setOnClickListener((v) -> {
            m_playerPresenter.playNext();
        });

        m_playBar.setMax(100);
    }

    private void initView() {
        m_trackCover = findViewById(R.id.track_cover);
        m_trackTitle = findViewById(R.id.track_title);
        m_playPre = findViewById(R.id.play_pre);
        m_playOrPause = findViewById(R.id.play_pause);
        m_playNext = findViewById(R.id.play_next);
        m_playBar = findViewById(R.id.track_seek);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m_playerPresenter.unRegisterCallBack(this);
    }

    //=================================== IPlayerCallBack =========================================
    @Override
    public void onPlay() {
        m_isPlaying = true;
        m_playOrPause.setImageResource(R.drawable.pause_white);
//        AnimationDrawable playToPauseAnim = (AnimationDrawable) m_playOrPause.getDrawable();
//        playToPauseAnim.start();
    }

    @Override
    public void onTrackPause() {
        m_isPlaying = false;
        m_playOrPause.setImageResource(R.drawable.play_white);
//        AnimationDrawable playToPauseAnim = (AnimationDrawable) m_playOrPause.getDrawable();
//        playToPauseAnim.start();
    }

    @Override
    public void onSeekTo() {

    }

    @Override
    public void onPlayPre() {
        m_isPlaying = true;
        m_playOrPause.setImageResource(R.drawable.pause_white);
        m_playingTrack = m_playerPresenter.getPlayingTrack();
        showTrackCoverAndTitle();
        checkPlayBorder();
    }

    @Override
    public void onPlayNext() {
        m_isPlaying = true;
        m_playOrPause.setImageResource(R.drawable.pause_white);
        m_playingTrack = m_playerPresenter.getPlayingTrack();
        showTrackCoverAndTitle();
        checkPlayBorder();
    }
//==================================================================================================

    private void showTrackCoverAndTitle(){
        Picasso.get().load(m_playingTrack.getCoverUrlLarge()).into(m_trackCover);
        m_trackTitle.setText(m_playingTrack.getTrackTitle());
    }

    private void checkPlayBorder(){
        int index = m_playerPresenter.getPlayIndex();
        int size = m_playerPresenter.getPlayListSize();
        if(index <= 0)
        {
            m_playPre.setEnabled(false);
        }
        else if(index >= size-1)
        {
            m_playNext.setEnabled(false);
        }
        else{
            m_playPre.setEnabled(true);
            m_playNext.setEnabled(true);
        }
    }
}
