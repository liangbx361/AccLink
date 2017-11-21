package com.out.accu.link.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.out.accu.link.R;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/21
 */

public class ProgressHelper {

    public static ProgressDialog getProgressBar(Context context) {
        ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.setIndeterminate(true);
        progressBar.setMessage(context.getString(R.string.loading));
        return progressBar;
    }
}
