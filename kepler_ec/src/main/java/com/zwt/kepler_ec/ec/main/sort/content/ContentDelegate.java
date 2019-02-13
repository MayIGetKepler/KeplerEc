package com.zwt.kepler_ec.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.zwt.kepler_core.delegates.KeplerDelegate;
import com.zwt.kepler_core.net.RestClient;
import com.zwt.kepler_core.ui.loader.LoaderStyle;
import com.zwt.kepler_ec.ec.R;

import java.util.List;

/**
 * @author ZWT
 */
public class ContentDelegate extends KeplerDelegate {

    private static final String ARG_CONTENT_ID = "CONTENT_ID";
    private int mContentId = -1;
    private RecyclerView mRvContent = null;

    public static ContentDelegate newInstance(int contentId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID, contentId);
        ContentDelegate delegate = new ContentDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mContentId = args.getInt(ARG_CONTENT_ID);
        }

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_list_content;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        findView(rootView);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRvContent.setLayoutManager(manager);
        initData();
    }

    private void findView(View rootView) {
        mRvContent = rootView.findViewById(R.id.rv_sort_content_container);

    }

    private void initData() {
        RestClient.Builder()
                .url("sort_content_list")
                .loader(getContext(),LoaderStyle.BallPulseIndicator)
                .success(body -> {
                    List<SectionBean> beans = new SectionDataConverter().convert(body);
                    SectionAdapter adapter = new SectionAdapter(R.layout.item_section_content,R.layout.item_section_header,beans);
                    mRvContent.setAdapter(adapter);
                })
                .build()
                .get();
    }

    public void scroll(int position){
        mRvContent.smoothScrollToPosition(position);
    }
}
