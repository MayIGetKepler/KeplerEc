package com.zwt.kepler_core.ui.loader;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * @author ZWT
 */
public class LoaderCreator {

    //将实例化的indicator保存，避免大量反射影响性能
    private static final WeakHashMap<String,Indicator> LOADER_MAP = new WeakHashMap<>();

    /**
     * 创建loading
     * @param context 上下文
     * @param type AVLoadingIndicatorView的style
     * @return AVLoadingIndicatorView
     */
    static AVLoadingIndicatorView create(Context context,String type){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);

        if (LOADER_MAP.get(type) == null){
            Indicator indicator = getIndicator(type);
            LOADER_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADER_MAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String type) {
        if (type == null||type.isEmpty()) {
            return null;
        }
        final StringBuilder drawableClassName = new StringBuilder();

        //包含.即传入的已经是完整的包名
        if (!type.contains(".")){
            String packageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(packageName)
                            .append(".indicators")
                            .append(".");
        }
        drawableClassName.append(type);
        try {
            Class<?> drawableClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
