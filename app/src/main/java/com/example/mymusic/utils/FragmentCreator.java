package com.example.mymusic.utils;

import androidx.fragment.app.Fragment;

import com.example.mymusic.fragment.BaseFragment;
import com.example.mymusic.fragment.HistoryFragment;
import com.example.mymusic.fragment.MineFragment;
import com.example.mymusic.fragment.RecommendFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentCreator {
    public static final int PAGE_COUNT = 3;
    private static Map<Integer, BaseFragment> s_cache = new HashMap<>();

    private static final int MINE = 0;
    private static final int RECOMMEND = 1;
    private static final int HISTORY = 2;

    //语法糖
    public static Fragment getFragment(int position) {
        if(s_cache.get(position) == null){
            switch (position){
                case MINE:
                    s_cache.put(MINE, new MineFragment());break;
                case RECOMMEND:
                    s_cache.put(RECOMMEND, new RecommendFragment());break;
                case HISTORY:
                    s_cache.put(HISTORY, new HistoryFragment());break;
            }
        }

        return s_cache.get(position);
    }
}
