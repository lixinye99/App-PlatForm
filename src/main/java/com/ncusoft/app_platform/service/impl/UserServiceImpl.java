package com.ncusoft.app_platform.service.impl;

import com.ncusoft.app_platform.mapper.BackendUserMapper;
import com.ncusoft.app_platform.mapper.DevUserMapper;
import com.ncusoft.app_platform.model.BackendUser;
import com.ncusoft.app_platform.model.Bo.UserLoginBo;
import com.ncusoft.app_platform.model.DevUser;
import com.ncusoft.app_platform.model.enumeration.UserType;
import com.ncusoft.app_platform.service.UserService;
import com.ncusoft.app_platform.utils.GetString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author monkJay
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    DevUserMapper devUserMapper;

    @Resource
    BackendUserMapper backendUserMapper;

    public DevUser findByDevId(int uid){
        DevUser devUser = new DevUser(uid);
        return devUserMapper.selectOne(devUser);
    }

    @Override
    public BackendUser findByBackId(int uid){
        BackendUser backendUser = new BackendUser(uid);
        return backendUserMapper.selectOne(backendUser);
    }

    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public boolean queryUsernameIsExist(String username) {
        DevUser users = new DevUser();
        users.setDevName(username);
        DevUser res = devUserMapper.selectOne(users);
        return res != null;
    }

    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public boolean queryAdminUsernameIsExist(String username) {
        BackendUser users = new BackendUser();
        users.setUserName(username);
        BackendUser res = backendUserMapper.selectOne(users);
        return res != null;
    }

    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public DevUser queryDevUser(UserLoginBo user) {
        String pwd = GetString.getMd5(user.getDevPassword());
        Example userExample = new Example(DevUser.class);
        Example.Criteria criteria = userExample.createCriteria();
        // 对比数据库中的值和传入的值
        criteria.andEqualTo("devName",user.getDevName());
        criteria.andEqualTo("devPassword",pwd);
        return devUserMapper.selectOneByExample(userExample);
    }

    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public BackendUser queryAdminUser(UserLoginBo user) {
        String pwd = GetString.getMd5(user.getDevPassword());
        Example userExample = new Example(BackendUser.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("userName",user.getDevName());
        criteria.andEqualTo("userPassword",pwd);
        return backendUserMapper.selectOneByExample(userExample);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUser(UserLoginBo users) {
        String password = GetString.getMd5(users.getDevPassword());
        if (users.getUserType().equals(UserType.DEVELOPER.getType())) {
            DevUser user = new DevUser();
            user.setDevName(users.getDevName());
            user.setDevPassword(password);
            devUserMapper.insert(user);
        } else if (users.getUserType().equals(UserType.ADMIN.getType())) {
            BackendUser user = new BackendUser();
            user.setUserName(users.getDevName());
            user.setUserPassword(password);
            backendUserMapper.insert(user);
        }
    }
}