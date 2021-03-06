package com.out.accu.link.page.main.settings;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cyou17173.android.arch.base.page.SmartStateFragment;
import com.out.accu.link.R;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.User;
import com.out.accu.link.page.login.LoginActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-11-09
 */
public class SettingsFragment extends SmartStateFragment<SettingsContract.Presenter> implements
        SettingsContract.View, View.OnClickListener {

    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    QMUICommonListItemView userNameItem;
    QMUICommonListItemView mobileNameItem;
    QMUICommonListItemView passwordItem;

    QMUITipDialog mLoadingDialog;

    @BindView(R.id.btnExitAccount)
    Button mBtnExitAccount;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(getContext().getString(R.string.menu_setting));
    }

    /**
     * 设置布局ID
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        ButterKnife.bind(this, getView());

        mLoadingDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(getResources().getString(R.string.modifying))
                .create();
        mLoadingDialog.setCancelable(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().start();
    }

    /**
     * 注册事件，如事件监听、广播接收等
     */
    @Override
    public void registerEvent() {
        mBtnExitAccount.setOnClickListener(v -> {
            // 退出当前账号
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }

    /**
     * 注销事件
     */
    @Override
    public void unregisterEvent() {

    }

    /**
     * 创建对应的Presenter, 通常需要传递两个对象（视图和数据服务）
     */
    @Override
    public SettingsContract.Presenter createPresenter() {
        return new SettingsPresenter(this, DataManager.getInstance().getDataService());
    }

    @Override
    public void showUser(User user) {
        mGroupListView.removeAllViews();

        if(userNameItem == null) {
            userNameItem = mGroupListView.createItemView(getResources().getString(R.string.modify_username));
        }
        userNameItem.setDetailText(user.username);

        if(mobileNameItem == null) {
            mobileNameItem = mGroupListView.createItemView(getResources().getString(R.string.modify_mobile));
        }
        mobileNameItem.setDetailText(user.mobile);

        if(passwordItem == null) {
            passwordItem = mGroupListView.createItemView(getResources().getString(R.string.modify_password));
        }

        QMUIGroupListView.newSection(getContext())
                .addItemView(userNameItem, this)
                .addItemView(mobileNameItem, this)
                .addItemView(passwordItem, this)
                .addTo(mGroupListView);
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        if(v.equals(userNameItem)) {
//            onUserName();
        } else if(v.equals(mobileNameItem)) {
            onMobile();
        } else if(v.equals(passwordItem)) {
            onPassword();
        }
    }

    private void onUserName() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.modify_username))
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction(R.string.cancel, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(R.string.modify, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            getPresenter().modifyUsername(text.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(getPresenter().getUser().username);
    }

    private void onMobile() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.modify_mobile))
                .setInputType(InputType.TYPE_CLASS_PHONE)
                .addAction(R.string.cancel, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(R.string.modify, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            getPresenter().modifyMobile(text.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(getPresenter().getUser().mobile);
    }

    private void onPassword() {
        QMUIDialog dialog =new QMUIDialog.CustomDialogBuilder(getContext())
                .setLayout(R.layout.item_password)
                .setTitle(R.string.modify_password)
                .addAction(R.string.cancel, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(R.string.modify, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        EditText mEtOldPwd = dialog.findViewById(R.id.oldPwd);
                        EditText mEtNewPwd = dialog.findViewById(R.id.newPwd);
                        String oldPwd = mEtOldPwd.getText().toString();
                        String newPwd = mEtNewPwd.getText().toString();
                        getPresenter().modifyPassword(oldPwd, newPwd);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
}