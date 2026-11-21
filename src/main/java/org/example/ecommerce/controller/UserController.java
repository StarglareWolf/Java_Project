package org.example.ecommerce.controller;

import jakarta.servlet.http.HttpSession;
import org.example.ecommerce.ResultJs;
import org.example.ecommerce.SelfDefineException.InformationException;
import org.example.ecommerce.entity.User;
import org.example.ecommerce.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")        // https://localhost:8080/api
@ResponseBody
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/register")    // https://localhost:8080/api/register?
    public ResultJs<String> register(String username, String password){
        if(username == null || password == null) throw new InformationException("用户名和密码不能为空!");

        User existUser = userMapper.getUserByUsername(username);
        if(existUser != null) throw new InformationException("该用户已经存在,注册失败!");

        int rows = userMapper.registerUser(username, password);
        if(rows > 0){
            return new ResultJs<>("注册成功!");   // 用于 json 化输出，好进行测试
        }
        return new ResultJs<>("注册失败!");
    }

    @GetMapping("/login")        // https://localhost:8080/api/login?
    public String logIn(String username, String password, HttpSession session){
        User user = userMapper.logIn(username, password);
        if(user != null){
            session.setAttribute("LOGIN_USER_ID", user.getId());
            return "登录成功, 欢迎" + user.getUsername() + ",你的ID是: " + user.getId();
        }
        throw new InformationException("用户名或密码错误,登录失败!");
    }

    @GetMapping("/logout")
    public ResultJs<String> logout(HttpSession session){
        session.invalidate();
        return new ResultJs<>("退出成功!若需访问内容请重新登录!");
    }

    @GetMapping("/update")
    public ResultJs<String> update(String username, String password, String newpassword){
        User user = userMapper.logIn(username, password);
        if(user != null){
            int line = userMapper.updatePassword(username, newpassword);
            if(line > 0){
                return new ResultJs<>("密码修改完成!");
            }
            else{
                return new ResultJs<>("密码修改失败!");
            }
        }
        throw new InformationException("不存在该用户或用户名/密码错误!");
    }
}
