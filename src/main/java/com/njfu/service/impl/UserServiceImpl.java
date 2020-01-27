package com.njfu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.njfu.dao.UserDOMapper;
import com.njfu.dao.UserPasswordDOMapper;
import com.njfu.dataobject.UserDO;
import com.njfu.dataobject.UserPasswordDO;
import com.njfu.error.BusinessException;
import com.njfu.error.EmBusinessError;
import com.njfu.service.UserService;
import com.njfu.service.model.ItemModel;
import com.njfu.service.model.UserModel;
import com.njfu.validator.ValidationResult;
import com.njfu.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;


    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if(userDO==null){
            return null;
        }

        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO,userPasswordDO);

    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel==null){
            throw  new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

       ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        UserDO userDO = convertFromModel(userModel);

        //System.out.println("name: "+userDO.getName()+"  telphone: "+userDO.getTelphone()+"    age:"+userDO.getAge()+"   gender"+userDO.getGender());
        try {
            userDOMapper.insertSelective(userDO);

        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号重复注册");
        }
        userModel.setId(userDO.getId());

        UserPasswordDO userPasswordDO  = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }

    @Override
    public UserModel vaildateLogin(String telphone, String password) throws BusinessException {
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if (userDO==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);

        if(!StringUtils.equals(password,userModel.getPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    @Override
    public List<UserModel> listUser() {
        List<UserDO> userDOList  = userDOMapper.listUser();
        List<UserModel> userModelList = userDOList.stream().map(userDO -> {
          UserModel userModel = convertFromDO(userDO);
          return userModel;
        }).collect(Collectors.toList());
        return userModelList;
    }


    private UserPasswordDO convertPasswordFromModel(UserModel userModel){
        if (userModel==null){
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setPassword(userModel.getPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;

    }

    private UserDO convertFromModel(UserModel userModel){
        if (userModel==null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    private UserModel convertFromDO(UserDO userDO){
        if (userDO==null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        return userModel;
    }


    private  UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO==null){
            return null;
        }
        if (userPasswordDO==null){
            return  null;
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        userModel.setPassword(userPasswordDO.getPassword());
        return userModel;
    }

}
