package com.njfu.error;

public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    //包装器业务异常类实现

    //直接接收EmBusinessError的传参构造业务异常
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    //接收自定义的errorMsg
    public BusinessException(CommonError commonError,String errorMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errorMsg);

    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
