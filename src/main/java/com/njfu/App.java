package com.njfu;

import com.njfu.dao.UserDOMapper;
import com.njfu.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.njfu"})
@MapperScan("com.njfu.dao")
@RestController
public class App {

    @Autowired
    private UserDOMapper userDOMapper;


    @RequestMapping("/")
    public String home(){
        UserDO userDO =  userDOMapper.selectByPrimaryKey(1);
        if(userDO==null){
            return "用户不存在";
        }
        return  userDO.getName();
    }


    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
