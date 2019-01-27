package com.zwt.keplerec;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.net.RestClient;
import com.zwt.kepler_core.ui.loader.LoaderStyle;

/**
 * @author ZWT
 */
public class EntryDelegate extends KeplerDelegate {

    private TextView mTextView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_entry;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {

        mTextView = rootView.findViewById(R.id.tvTest);
        testGet();

    }

    private void testGet(){
        RestClient.Builder().url("/test")
                .loader(getContext(),LoaderStyle.BallClipRotatePulseIndicator)
                .success(body -> mTextView.setText(body))
                .error(errorMsg -> Toast.makeText(getContext(),errorMsg,Toast.LENGTH_SHORT).show())
                .build()
                .get();

    }
}
