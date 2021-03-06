package com.zwt.kepler_core.ui.refresh;

/**
 * @author ZWT
 */
public class PagingBean {
    //当前是第几页
    private int mPageIndex = 0;
    //总共有多少条数据
    private int mTotal = 0;
    //一页显示多少条数据
    private int mPageSize = 0;
    //当前已经显示了多少条数据
    private int mCurrentCount = 0;
    //加载延迟
    private int mDelayed = 0;

    public int getPageIndex() {
        return mPageIndex;
    }

    public PagingBean setPageIndex(int pageIndex) {
        mPageIndex = pageIndex;
        return this;
    }

    public int getTotal() {
        return mTotal;
    }

    public PagingBean setTotal(int total) {
        mTotal = total;
        return this;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PagingBean setPageSize(int pageSize) {
        mPageSize = pageSize;
        return this;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public PagingBean setCurrentCount(int currentCount) {
        mCurrentCount = currentCount;
        return this;
    }

    public int getDelayed() {
        return mDelayed;
    }

    public PagingBean setDelayed(int delayed) {
        mDelayed = delayed;
        return this;
    }

    public void addIndex(){
        mPageIndex++;
    }
}
