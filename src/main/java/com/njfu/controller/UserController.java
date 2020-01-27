package com.njfu.controller;

import com.alibaba.druid.util.StringUtils;
import com.njfu.controller.viewobject.ItemVO;
import com.njfu.controller.viewobject.UserVO;
import com.njfu.error.BusinessException;
import com.njfu.error.CommonError;
import com.njfu.error.EmBusinessError;
import com.njfu.response.CommonReturnType;
import com.njfu.service.UserService;
import com.njfu.service.model.ItemModel;
import com.njfu.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(origins = {"*"}, allowCredentials = "true",allowedHeaders = "*")

public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    //用户登录接口

    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone")String telphone,
                                    @RequestParam(name = "password")String password) throws BusinessException {
        if(org.apache.commons.lang3.StringUtils.isEmpty(telphone)||StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //校验是否合法
        UserModel userModel= userService.vaildateLogin(telphone,password);
        //System.out.println("合法");
        //将登录凭证加入用户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        //System.out.println("传值");
        return CommonReturnType.create(null);
    }




    //用户注册接口
    @Transactional
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name = "otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")Integer gender,
                                     @RequestParam(name = "age")Integer age,
                                    @RequestParam(name = "password")String password)throws BusinessException{
        //验证手机号和otpcode符合
        String inSessionOptCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);

        //System.out.println("yzm:"+inSessionOptCode);

        if (!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOptCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setPassword(password);
        //System.out.println("hello"+userModel.getAge()+userModel.getTelphone());
        userService.register(userModel);
        //System.out.println("hello"+userModel.getAge());
        //System.out.println("name: "+userModel.getName()+"  telphone: "+userModel.getTelphone()+"    age:"+userModel.getAge()+"   gender"+userModel.getGender()+"  id:   "+userModel.getId());

        return CommonReturnType.create(null);
    }

    //用户获取otp短信接口

    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FROMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone")String telphone){
        //生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        //将验证码与手机号关联

        httpServletRequest.getSession().setAttribute(telphone,otpCode);

        System.out.println("telphone = "+telphone + "&otpcode = " + otpCode);
        //将验证码发给用户
        return  CommonReturnType.create(null);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listUser(){
        List<UserModel> userModelList = userService.listUser();
        List<UserVO> userVOList = userModelList.stream().map(userModel -> {
            UserVO userVO = convertFromModel(userModel);
            return userVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(userModelList);
    }



    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id")Integer id) throws BusinessException{
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        if (userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = convertFromModel(userModel);
        //返回通用对象
        return  CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }


}
