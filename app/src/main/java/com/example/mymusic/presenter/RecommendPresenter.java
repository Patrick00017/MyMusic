package com.example.mymusic.presenter;

import com.example.mymusic.Interface.IRecommendCallBack;
import com.example.mymusic.Interface.IRecommendPresenter;
import com.example.mymusic.utils.LogUtils;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendPresenter implements IRecommendPresenter {
    private static final String TAG = "RecommendPresenter";
    private static RecommendPresenter instance = null;

    private List<IRecommendCallBack> m_callBacks = new ArrayList<>();

    private RecommendPresenter() {
    }

    public static RecommendPresenter getInstance(){
        if(instance == null){
            synchronized (RecommendPresenter.class){
                if(instance == null){
                    instance = new RecommendPresenter();
                }
            }
        }
        return instance;
    }

    @Override
    public void getDataFromHIMA() {
//        //获取数据
//        //获取分类方法
//        Map<String, String> map = new HashMap<>();
//        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
//            @Override
//            public void onSuccess(CategoryList categoryList) {
//                for(int i=0; i<categoryList.getCategories().size(); i++){
//                    LogUtils.d(TAG, categoryList.getCategories().get(i).getCategoryName());
//                }
//            }
//            @Override
//            public void onError(int i, String s) {
//                LogUtils.d(TAG, "error.");
//            }
//        });

        Map<String ,String> albumMap = new HashMap<>();
        albumMap.put(DTransferConstants.CATEGORY_ID ,"2");
        albumMap.put(DTransferConstants.CALC_DIMENSION ,"1");
        CommonRequest.getAlbumList(albumMap, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(AlbumList albumList) {
                for(int i=0; i<albumList.getAlbums().size(); i++){
                    LogUtils.d(TAG, "get data success");
                    //successful
                    for(IRecommendCallBack callBack : m_callBacks){
                        callBack.updataDataList(albumList.getAlbums());
                    }
                }
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.d(TAG, "error.");
                //error
                for(IRecommendCallBack callBack : m_callBacks){
                    callBack.onNetWorkError();
                }
            }
        });
    }

    @Override
    public void RegisterCallBack(IRecommendCallBack callBack) {
        m_callBacks.add(callBack);
    }

    @Override
    public void unRegisterCallBack(IRecommendCallBack callBack) {
        m_callBacks.remove(callBack);
    }
}
