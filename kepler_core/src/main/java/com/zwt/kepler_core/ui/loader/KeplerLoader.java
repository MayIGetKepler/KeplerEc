package com.zwt.kepler_core.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.zwt.kepler_core.R;
import com.zwt.kepler_core.util.DimenUtil;

import java.util.ArrayList;

/**
 * @author ZWT
 */
public class KeplerLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final String DEFAULT_LOADER = LoaderStyle.BallBeatIndicator.name();

    //将loader保存在这里，以便统一关闭
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    public static void showLoading(Context context, String type) {

        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(context, type);

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        dialog.setContentView(avLoadingIndicatorView);

        final Window dialogWindow = dialog.getWindow();

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        if (dialogWindow!= null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth/LOADER_SIZE_SCALE;
            lp.height = deviceHeight/LOADER_SIZE_SCALE;
            lp.gravity = Gravity.CENTER;
        }

        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog  : LOADERS) {
        	if (dialog!=null&&dialog.isShowing()){
        	    dialog.cancel();
            }
        }
    }
}
