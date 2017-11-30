package com.out.accu.link.util;

import android.content.Context;
import android.widget.Toast;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author liangbx
 * @version 2017/9/1
 */

public class ExitAppController {

    private int pressedCount;
    private long pressedTime;
    private long delayTime;
    private Context mContext;

    public ExitAppController(Context context) {
        this(context, 2000);
    }

    public ExitAppController(Context context, long delayTime) {
        mContext = context;
        this.delayTime = delayTime;
    }

    public boolean onBackPressed() {
        if(pressedCount == 0) {
            pressedCount++;
            pressedTime = System.currentTimeMillis();
            Toast.makeText(mContext, "再按一次退出", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            if(System.currentTimeMillis() - pressedTime < delayTime) {
                return false;
            } else {
                pressedCount = 0;
                return onBackPressed();
            }
        }
    }
}
