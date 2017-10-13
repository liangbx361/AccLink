package com.out.accu.link.data;

import io.reactivex.Observable;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/9/29
 */

public interface DataService {

    /**
     * 登录
     * @param userName 用户名
     * @param password 密码
     */
    Observable<Boolean> login(String userName, String password);

    /**
     * 采集数据
     */

}
