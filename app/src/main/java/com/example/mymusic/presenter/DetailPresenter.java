package com.example.mymusic.presenter;

import com.example.mymusic.Interface.IDetailCallBack;
import com.example.mymusic.Interface.IDetailPresenter;
import com.example.mymusic.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailPresenter implements IDetailPresenter {
    private static final String TAG = "DetailPresenter";
    private List<IDetailCallBack> m_callBacks = new ArrayList<>();
    private static DetailPresenter instance = null;
    private Album m_album = null;

    private DetailPresenter(){}

    public static DetailPresenter getInstance(){
        if(instance==null){
            synchronized (DetailPresenter.class){
                if(instance==null){
                    instance = new DetailPresenter();
                }
            }
        }
        return instance;
    }

    public void setTargetAlbum(Album album){
        this.m_album = album;
    }

    public Album getTargetAlbum(){
        return m_album;
    }

    @Override
    public void getTrackList(int albumID, int page) {
        Map<String, String> map = new HashMap<>();
        map.put(DTransferConstants.ALBUM_ID, albumID + "");
        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, page + "");
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                List<Track> tracks = trackList.getTracks();
                //处理在最后一首按nextplay按钮会调到空界面的问题
                LogUtils.d(TAG, "get data successful");
                for(IDetailCallBack callBack : m_callBacks){
                    callBack.updateUI(tracks);
                }
            }

            @Override
            public void onError(int ErrorCode, String ErrorMsg) {
                LogUtils.d(TAG, "Errorcode ---> " + ErrorCode);
                LogUtils.d(TAG, "ErrorMsg ---> " + ErrorMsg);
                for(IDetailCallBack callBack : m_callBacks){
                    callBack.onNetWokrError();
                }
            }
        });
    }

    @Override
    public void RegisterCallBack(IDetailCallBack callBack) {
        if (callBack != null) {
            m_callBacks.add(callBack);
        }
    }

    @Override
    public void unRegisterCallBack(IDetailCallBack callBack) {
        m_callBacks.remove(callBack);
    }
}
