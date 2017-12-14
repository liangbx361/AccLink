package com.out.accu.link.page.login;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cyou17173.android.arch.base.page.SmartActivity;
import com.out.accu.link.R;
import com.out.accu.link.data.mode.LoginInfo;
import com.out.accu.link.util.ProgressHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-09-29
 */
public class LoginActivity extends SmartActivity<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_server)
    EditText etServer;
    @BindView(R.id.et_port)
    EditText etPort;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.displayServer)
    TextView tvModifyServer;
    @BindView(R.id.serverBlock)
    ViewGroup vgServer;
    @BindView(R.id.portBlock)
    ViewGroup vgPort;

    ProgressDialog mProgressDialog;

    boolean showServer;

    /**
     * 设置布局ID
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        ButterKnife.bind(this);
//        etUsername.setText("user1");
//        etPassword.setText("123456");
//        etServer.setText("211.72.229.132");
//        etPort.setText("60003");

        mProgressDialog = ProgressHelper.getProgressBar(this);

    }

    /**
     * 注册事件，如事件监听、广播接收等
     */
    @Override
    public void registerEvent() {
        btGo.setOnClickListener(view -> {
            try {
                getPresenter().login(getLoginInfo());
            } catch (IllegalArgumentException ignored) {

            }
        });

        tvModifyServer.setOnClickListener(v -> {
            if(showServer) {
                vgServer.setVisibility(View.GONE);
                vgPort.setVisibility(View.GONE);
                tvModifyServer.setText(R.string.show_server_info);
                showServer = false;
            } else {
                vgServer.setVisibility(View.VISIBLE);
                vgPort.setVisibility(View.VISIBLE);
                tvModifyServer.setText(R.string.hide_server_info);
                showServer = true;
            }
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
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    private String getUserName() throws IllegalArgumentException {
        String userName = etUsername.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, R.string.user_name_not_empty, Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("");
        }

        return userName;
    }

    private String getPassword() throws IllegalArgumentException {
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.password_not_empty, Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("");
        }

        return password;
    }

    private String getServer() throws IllegalArgumentException {
        String server = etServer.getText().toString();

        if (TextUtils.isEmpty(server)) {
            Toast.makeText(this, R.string.server_not_empty, Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("");
        }

        try {
            InetAddress.getByName(server);
        } catch (UnknownHostException e) {
            Toast.makeText(this, R.string.server_not_correct, Toast.LENGTH_SHORT).show();
            return null;
        }

        return server;
    }

    private int getPort() throws IllegalArgumentException {
        String port = etPort.getText().toString();

        if (TextUtils.isEmpty(port)) {
            Toast.makeText(this, R.string.port_not_empty, Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("");
        }

        try {
            return Integer.valueOf(port);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.port_not_correct, Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public ProgressDialog getProgress() {
        return mProgressDialog;
    }

    @Override
    public void showLoginInfo(LoginInfo loginInfo) {
        if(loginInfo != null) {
            etUsername.setText(loginInfo.user);
            etPassword.setText(loginInfo.password);
            etServer.setText(loginInfo.server);
            etPort.setText(loginInfo.port + "");
        }

        if (loginInfo != null && !TextUtils.isEmpty(loginInfo.server) && loginInfo.port != 0) {
            vgServer.setVisibility(View.GONE);
            vgPort.setVisibility(View.GONE);
            tvModifyServer.setText(R.string.show_server_info);
            showServer = false;
        } else {
            vgServer.setVisibility(View.VISIBLE);
            vgPort.setVisibility(View.VISIBLE);
            tvModifyServer.setText(R.string.hide_server_info);
            showServer = true;
        }
    }

    @Override
    public LoginInfo getLoginInfo() {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.user = getUserName();
        loginInfo.password = getPassword();
        loginInfo.server = getServer();
        loginInfo.port = getPort();
        return loginInfo;
    }
}