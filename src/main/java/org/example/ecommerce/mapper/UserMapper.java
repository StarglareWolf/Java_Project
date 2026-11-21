package org.example.ecommerce.mapper;

import org.example.ecommerce.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

@Repository
public class UserMapper {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserByUsername(String username) {
        String sql = "select * from users where username=?";
        try{
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        }
        catch(Exception e){
            return null;
        }
    }

    public int registerUser(String username, String password){
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        String sql = "insert into users(username, password) values(?,?)";

        return jdbcTemplate.update(sql,username,encryptedPassword);
    }

    public User logIn(String username, String password){
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        String sql = "select * from users where username = ? and password = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, encryptedPassword);
        }
        catch(Exception e){
            return null;
        }
    }

    public int updatePassword(String username, String newpassword){
        String encryptedPassword = DigestUtils.md5DigestAsHex(newpassword.getBytes());
        String sql = "update users set password = ? where username = ?";
        return jdbcTemplate.update(sql,encryptedPassword,username);
    }
}
