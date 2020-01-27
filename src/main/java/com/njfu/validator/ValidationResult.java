package com.njfu.validator;



import org.apache.commons.lang3.*;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    //校验结果
    private boolean hasErrors = false;

    //存放错误信息的map
    private Map<String,String> errorMsgMap = new HashMap<>();

    //获取错误结果
    public String getErrMsg(){
     return   StringUtils.join(errorMsgMap.values().toArray(),",");
    }


    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }
}
