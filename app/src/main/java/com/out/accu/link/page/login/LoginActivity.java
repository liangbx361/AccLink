package com.out.accu.link.page.login;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.transition.Explode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cyou17173.android.arch.base.page.SmartActivity;
import com.out.accu.link.R;
import com.out.accu.link.page.main.MainActivity;

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
    @BindView(R.id.bt_go)
    Button btGo;

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
    }

    /**
     * 注册事件，如事件监听、广播接收等
     */
    @Override
    public void registerEvent() {
        btGo.setOnClickListener(view -> {
            Explode explode = new Explode();
            explode.setDuration(500);

            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
            ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent, option.toBundle());
            finish();

//            try {
//                getPresenter().login(getUserName(), getPassword());
//            } catch (IllegalArgumentException ignored) {
//
//            }
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

        if(TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("");
        }

        return userName;
    }

    private String getPassword() throws IllegalArgumentException {
        String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException("");
        }

        return password;
    }
}