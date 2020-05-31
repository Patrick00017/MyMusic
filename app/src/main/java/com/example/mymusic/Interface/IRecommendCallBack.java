package com.example.mymusic.Interface;

import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.List;

public interface IRecommendCallBack {
    //更新界面UI
    void updataDataList(List<Album> albumList);

    //当网络异常时
    void onNetWorkError();

    //当获取到的数据为空时
    void onEmptyData();

}
