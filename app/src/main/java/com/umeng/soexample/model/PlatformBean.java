package com.umeng.soexample.model;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by D on 2017/11/28.
 */
public class PlatformBean {
    public int resId;
    public String name;
    public SHARE_MEDIA platform;

    public PlatformBean(int resId, String name, SHARE_MEDIA platform) {
        this.resId = resId;
        this.name = name;
        this.platform = platform;
    }
}
