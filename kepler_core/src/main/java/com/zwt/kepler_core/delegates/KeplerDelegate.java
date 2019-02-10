package com.zwt.kepler_core.delegates;

/**
 * @author ZWT
 */
public abstract class KeplerDelegate extends PermissionDelegate {

    @SuppressWarnings("unchecked")
    public <T extends KeplerDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
