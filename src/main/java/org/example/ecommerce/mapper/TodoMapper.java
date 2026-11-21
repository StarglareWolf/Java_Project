package org.example.ecommerce.mapper;

import org.example.ecommerce.SelfDefineException.InformationException;
import org.example.ecommerce.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoMapper {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 创建待办
    public int createTodo(Integer userId, String title, String content){
        String sql = "insert into todolist(user_id, title, content) values(?,?,?)";
        return jdbcTemplate.update(sql,userId, title, content);
    }

    // 罗列待办
    public List<Todo> listTodo(Integer userId){
        String sql = "select * from todolist where user_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Todo.class), userId);
    }

    // 查询待办
    public Todo selectTodo(Integer id){
        String sql = "select * from todolist where id = ? ";
        try{
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Todo.class), id);
        }
        catch(Exception e){
            throw new InformationException("不可访问他人信息!");
        }
    }

    // 查询待办状态
    public int finishTodo(Integer id){
        String sql = "update todolist set is_done = 1 where id = ?";
        try {
            return jdbcTemplate.update(sql, id);
        }
        catch(Exception e){
            throw new InformationException("不可访问他人信息!");
        }
    }

    // 删除待办
    public int deleteTodo(Integer id){
        String sql = "delete from todolist where id = ?";
        try {
            return jdbcTemplate.update(sql, id);
        }
        catch(Exception e){
            throw new InformationException("不可访问他人信息!");
        }
    }

    public int updateTitle(Integer id, String title){
        String sql = "update todolist set title = ? where id = ?";
        return jdbcTemplate.update(sql,title,id);
    }

    public int updateContent(Integer id, String content){
        String sql = "update todolist set content = ? where id = ?";
        return jdbcTemplate.update(sql,content,id);
    }
}
