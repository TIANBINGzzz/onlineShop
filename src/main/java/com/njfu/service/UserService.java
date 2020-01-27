package com.njfu.service;

import com.njfu.error.BusinessException;
import com.njfu.service.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel vaildateLogin(String telphone,String password) throws BusinessException;
    List<UserModel> listUser();

}
