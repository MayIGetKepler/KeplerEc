package com.zwt.kepler_core.delegates.web.event;

import android.widget.Toast;

/**
 * @author ZWT
 */
public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(),"未定义的action",Toast.LENGTH_SHORT).show();
        return null;
    }
}
