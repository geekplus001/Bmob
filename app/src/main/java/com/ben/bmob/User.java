package com.ben.bmob;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
public class User extends BmobUser {
    private BmobFile icon;

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }
}
