package com.example.mymusic.Interface;

public interface IDetailPresenter {
    /**
     * 与喜马拉雅请求Track数据
     */
    void getTrackList(int albumID, int page);

    //注册回调接口
    void RegisterCallBack(IDetailCallBack callBack);

    //释放回调接口
    void unRegisterCallBack(IDetailCallBack callBack);
}
