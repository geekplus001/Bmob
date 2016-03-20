package com.ben.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/3/18 0018.
 */
public class Note extends BmobObject {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
