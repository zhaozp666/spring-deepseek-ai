package com.example.demo.controller;

import com.example.demo.vo.BasicVo;
import com.example.demo.vo.LoginInfo;
import com.example.demo.vo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @PostMapping("/api/login")
    public BasicVo<User> login(@RequestBody LoginInfo loginInfo) {

        if (loginInfo.getUsername().equals("test")
                && loginInfo.getPassword().equals("123")) {
            User user = new User();
            user.setToken("abc123");
            user.setName("张三");
            return BasicVo.success(user);
        } else {
            return BasicVo.fail(401, "用户名或密码错误");
        }
    }

}

