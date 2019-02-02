package com.zwt.kepler_core.ui.refresh;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.zwt.kepler_core.application.Kepler;
import com.zwt.kepler_core.net.RestClient;

/**
 * @author ZWT
 */
public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(@NonNull SwipeRefreshLayout REFRESH_LAYOUT) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Thread thread = new Thread(
                () -> {
                    try {
                        Thread.sleep(2000);
                        REFRESH_LAYOUT.setRefreshing(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
    }

    public void firstPage(String url) {
        REFRESH_LAYOUT.setOnRefreshListener(this);
        RestClient
                .Builder()
                .url(url)
                .success(body -> {
                    Toast.makeText(Kepler.getApplicationContext(),body,Toast.LENGTH_SHORT).show();
                    REFRESH_LAYOUT.setRefreshing(false);
                })
                .build()
                .get();
    }
}
