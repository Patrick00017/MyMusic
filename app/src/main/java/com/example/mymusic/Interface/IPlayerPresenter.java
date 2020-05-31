package com.example.mymusic.Interface;

public interface IPlayerPresenter {

    void play();

    void pause();

    void seekTo(int time);

    void playPre();

    void playNext();

    //注册回调接口
    void RegisterCallBack(IPlayerCallBack callBack);

    //释放回调接口
    void unRegisterCallBack(IPlayerCallBack callBack);
}
