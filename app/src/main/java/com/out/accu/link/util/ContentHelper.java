package com.out.accu.link.util;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.out.accu.link.R;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/27
 */

public class ContentHelper {

    Button mBtnDateTime;
    Button mBtnTankLevel;
    Button mBtnModuleId;
    EditText mEtContent;

    public ContentHelper(View view) {
        initView(view);
    }

    private void initView(View view) {
        mBtnDateTime = view.findViewById(R.id.dateTime);
        mBtnTankLevel = view.findViewById(R.id.tankLevel);
        mBtnModuleId = view.findViewById(R.id.moduleId);
        mEtContent = view.findViewById(R.id.content);

        mBtnDateTime.setOnClickListener(v -> {
           mEtContent.setText(mEtContent.getText() + "%T");
            setLastSelection();
        });

        mBtnTankLevel.setOnClickListener(v -> {
            mEtContent.setText(mEtContent.getText() + "%V0");
            setLastSelection();
        });

        mBtnModuleId.setOnClickListener(v -> {
            mEtContent.setText(mEtContent.getText() + "%M");
            setLastSelection();
        });
    }

    public String getContent() {
        return mEtContent.getText().toString();
    }

    public void setLastSelection() {
        mEtContent.setSelection(mEtContent.getText().length());
    }
}
