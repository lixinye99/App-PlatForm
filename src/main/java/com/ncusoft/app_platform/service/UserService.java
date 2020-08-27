package com.ncusoft.app_platform.service;

import com.ncusoft.app_platform.model.BackendUser;
import com.ncusoft.app_platform.model.Bo.UserLoginBo;
import com.ncusoft.app_platform.model.DevUser;

/**
 * @author monkJay
 */
public interface UserService {
    /**
     * 根据 id 查询账号
     * @param uid 账号 ID
     */
    DevUser findByDevId(int uid);

    /**
     * 根据 id 查询账号
     * @param uid 账号 ID
     */
    BackendUser findByBackId(int uid);

    /**
     * 查询账号是否存在
     * @param username 账号
     * @return 是否存在
     */
    boolean queryUsernameIsExist(String username);

    boolean queryAdminUsernameIsExist(String username);

    /**
     * 查询开发者账号密码是否合法,存在则返回该账号
     * @param user 登录 body 参数
     * @return Users
     */
    DevUser queryDevUser(UserLoginBo user);

    /**
     * 查询管理员账号密码是否合法,存在则返回该账号
     * @param user 登录 body 参数
     * @return Users
     */
    BackendUser queryAdminUser(UserLoginBo user);

    /**
     * 用户注册
     * @param users Users
     * @return Users
     */
    void saveUser(UserLoginBo users);
}
