package com.example.mymusic.Interface;

public interface IRecommendPresenter {
    //向喜马拉雅获取专辑数据
    void getDataFromHIMA();

    //注册回调接口
    void RegisterCallBack(IRecommendCallBack callBack);

    //释放回调接口
    void unRegisterCallBack(IRecommendCallBack callBack);
}
