package com.example.mymusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymusic.R;
import com.example.mymusic.base.BaseApplication;

public abstract class UILoader extends FrameLayout {

    private View m_emptyView;
    private View m_networkView;
    private View m_loadingView;
    private View m_successfulView;
    private onNetWorkClickedListener m_listener = null;

    private UI_STATUS m_status = UI_STATUS.LOADING;

    public enum UI_STATUS {
        LOADING,
        EMPTY,
        NETWORKERROR,
        SUCCESSFUL
    }

    public UILoader(@NonNull Context context) {
    this(context, null);
}

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        showViewWithStatus();
    }

    private void showViewWithStatus() {
        if (m_emptyView == null) {
            m_emptyView = getEmptyView();
            addView(m_emptyView);
        }

        if (m_networkView == null) {
            m_networkView = getNetWorkErrorView();
            addView(m_networkView);
        }

        if (m_loadingView == null) {
            m_loadingView = getLoadingView();
            addView(m_loadingView);
        }

        if (m_successfulView == null) {
            m_successfulView = getSuccessfulView(this);
            m_successfulView.setOnClickListener((v) -> {
                if (m_listener != null) {
                    m_listener.onClick();
                }
            });
            addView(m_successfulView);
        }

        m_emptyView.setVisibility(m_status == UI_STATUS.EMPTY ? VISIBLE : GONE);
        m_loadingView.setVisibility(m_status == UI_STATUS.LOADING ? VISIBLE : GONE);
        m_networkView.setVisibility(m_status == UI_STATUS.NETWORKERROR ? VISIBLE : GONE);
        m_successfulView.setVisibility(m_status == UI_STATUS.SUCCESSFUL ? VISIBLE : GONE);
    }

    public void changeStatusTo(UI_STATUS status){
        m_status = status;

        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                showViewWithStatus();
            }
        });
    }

    private View getEmptyView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
    }

    private View getNetWorkErrorView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_networkerror_view, this, false);
    }

    private View getLoadingView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
    }

    public abstract View getSuccessfulView(ViewGroup container);

    public interface onNetWorkClickedListener{
        void onClick();
    }

    public void setonNetWorkClickedListener(onNetWorkClickedListener listener)
    {
        m_listener = listener;
    }

}
