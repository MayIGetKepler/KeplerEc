package com.zwt.kepler_core.delegates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * @author ZWT
 */
public abstract class BaseDelegate extends SwipeBackFragment {

    public abstract Object setLayout();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer){
            rootView = inflater.inflate((Integer) setLayout(),container,false);
        }else if (setLayout() instanceof  View){
            rootView = (View) setLayout();
        }
        if (rootView != null){
           onBindView(savedInstanceState,rootView);
        }
        return rootView;
    }

    protected abstract void onBindView(Bundle savedInstanceState,View rootView);
}
