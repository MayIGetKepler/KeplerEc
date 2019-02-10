package com.zwt.kepler_core.ui.banner;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.zwt.kepler_core.R;

import java.util.ArrayList;

/**
 * @author ZWT
 */
public class BannerCreator {

    public static void setDefault(ConvenientBanner<String> convenientBanner,
                                  ArrayList<String> banners, OnItemClickListener clickListener){
        convenientBanner
                .setPages(new HolderCreator(),banners)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                .startTurning(3000)
                .setCanLoop(true);
    }
}
