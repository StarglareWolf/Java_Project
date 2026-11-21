package org.example.ecommerce.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.List;
@RestController
public class UserInformation {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //写一个test请求，查询数据库信息，根据网络文档编写
    @GetMapping("/user")   // 括号里面是什么，那么访问网页就应该填写什么，如这里是 /test, 那么网页应该写为 http://localhost:8080/test, 是 /list 就改为 /list, 否则访问出错
    public List<Map<String,Object>> test(){

        String sql="select * from users";     // mysql 的关键字语句，users 是我建立的 todo_app 数据库的 users 表
        List<Map<String,Object>> list_map=jdbcTemplate.queryForList(sql);  // 合成一个 Map 然后显示在网页中
        return list_map;

    }

}
