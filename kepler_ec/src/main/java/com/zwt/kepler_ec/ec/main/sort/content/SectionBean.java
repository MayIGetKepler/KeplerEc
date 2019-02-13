package com.zwt.kepler_ec.ec.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author ZWT
 */
public class SectionBean extends SectionEntity<SectionContentItemEntity> {

    private boolean mHasMore = false;
    private int mId = -1;

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionBean(SectionContentItemEntity sectionContentItemEntity) {
        super(sectionContentItemEntity);
    }

    public boolean isHasMore() {
        return mHasMore;
    }

    public void setHasMore(boolean hasMore) {
        mHasMore = hasMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
