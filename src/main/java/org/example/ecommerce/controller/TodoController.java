package org.example.ecommerce.controller;

import jakarta.servlet.http.HttpSession;
import org.example.ecommerce.ResultJs;
import org.example.ecommerce.SelfDefineException.InformationException;
import org.example.ecommerce.entity.Todo;
import org.example.ecommerce.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/todo")  // https://localhost:8080/api/todo
@ResponseBody
public class TodoController {
    @Autowired
    private TodoMapper todoMapper;

    @GetMapping("/add")        // https://localhost:8080/api/todo/add?
    public ResultJs<String> add(String title, String content, HttpSession session){
        Integer userId = (Integer) session.getAttribute("LOGIN_USER_ID");
        if(userId == null){
            throw new InformationException("请登录后查询!");
        }
        if(content == null){
            throw new InformationException("缺少内容!");
        }
        todoMapper.createTodo(userId, title, content);
        return new ResultJs<>("待办添加成功!");
    }

    @GetMapping("/list")     // https://localhost:8080/api/todo/list?
    public ResultJs<List<Todo>> list(HttpSession session){
        Integer userId = (Integer) session.getAttribute("LOGIN_USER_ID");
        if(userId == null) throw new InformationException("请登录后访问!");
        System.out.println(todoMapper.listTodo(userId));
        return new ResultJs<>(todoMapper.listTodo(userId));
    }

    // 选择查看信息
    @GetMapping("/select")
    public ResultJs<Todo> select(Integer id, HttpSession session){
        if(id.getClass() != Integer.class){throw new InformationException("输入类型不匹配!");}

        Integer userId = (Integer) session.getAttribute("LOGIN_USER_ID");
        if(userId == null) throw new InformationException("请登录后查询!");

        Todo todo = todoMapper.selectTodo(id);
        if(!todo.getUserId().equals(userId)) throw new InformationException("只能访问自己的待办信息!");

        return new ResultJs<>(todoMapper.selectTodo(id));
    }

    @GetMapping("/finish")     // https://localhost:8080/api/finish?
    public ResultJs<String> finish(Integer id, HttpSession session){
        if(id.getClass() != Integer.class){throw new InformationException("输入类型不匹配!");}

        Integer userId = (Integer) session.getAttribute("LOGIN_USER_ID");
        if(userId == null) throw new InformationException("请登录后操作!");

        Todo todo = todoMapper.selectTodo(id);
        if(!todo.getUserId().equals(userId)) throw new InformationException("只能访问自己的待办信息!");

        todoMapper.finishTodo(id);
        return new ResultJs<>("待办已完成!");
    }

    @GetMapping("/delete")    // https://localhost:8080/api/delete?
    public ResultJs<String> delete(Integer id, HttpSession session){

        Integer userId = (Integer) session.getAttribute("LOGIN_USER_ID");
        if(userId == null) throw new InformationException("请登录后操作!");

        Todo todo = todoMapper.selectTodo(id);
        if(!todo.getUserId().equals(userId)) throw new InformationException("只能修改自己的待办信息!");

        todoMapper.deleteTodo(id);
        return new ResultJs<>("待办已删除!");
    }

    @GetMapping("/update/title")
    public ResultJs<String> updateTitle(Integer id, String title, HttpSession session){

        Integer userId = (Integer) session.getAttribute("LOGIN_USER_ID");

        Todo todo = todoMapper.selectTodo(id);
        if(!todo.getUserId().equals(userId)) throw new InformationException("只能修改自己的待办信息!");

        todoMapper.updateTitle(id, title);
        return new ResultJs<>("标题更改完成!");
    }

    @GetMapping("/update/content")
    public ResultJs<String> updateContent(Integer id, String content, HttpSession session){
        if(id.getClass() != Integer.class){throw new InformationException("输入类型不匹配!");}

        Integer userId = (Integer) session.getAttribute("LOGIN_USER_ID");
        if(userId == null) throw new InformationException("请登录后操作!");

        Todo todo = todoMapper.selectTodo(id);
        if(!todo.getUserId().equals(userId)) throw new InformationException("只能修改自己的待办信息!");

        todoMapper.updateContent(id, content);
        return new ResultJs<>("内容修改完成!");
    }
}
