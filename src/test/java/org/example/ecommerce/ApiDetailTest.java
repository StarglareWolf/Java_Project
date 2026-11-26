package org.example.ecommerce;

import jakarta.servlet.http.HttpSession;
import org.example.ecommerce.SelfDefineException.InformationException;
import org.example.ecommerce.controller.TodoController;
import org.example.ecommerce.mapper.TodoMapper;
import org.example.ecommerce.mapper.UserMapper;
import org.example.ecommerce.entity.User;
import org.example.ecommerce.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ApiDetailTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TodoMapper todoMapper;

    @Test
    public void testFullFlow() {
        System.out.println("========== 开始自动化测试 ==========");

        String username = RandomStr.RandomString(6);
        String password =  RandomStr.RandomString(6);
        // 在一般化测试里面已经测过了，这里就直接注册登录了
        userMapper.registerUser(username, password);
        User user = userMapper.logIn(username, password);

        String title = "出去吃饭";
        String content = "和TOM一起";
        String newTitle = "出去玩";
        String newContent = "和Pipe一起";

        todoMapper.createTodo(user.getId(), title, content);

        // 查询列表测试
        List<Todo>todos = todoMapper.listTodo(user.getId());
        assertFalse(todos.isEmpty(),"查询所有列表失败");
        System.out.println("查询待办列表成功!");

        Todo todo = todos.getFirst();

        // 单独检索测试
        Todo test_todo = todoMapper.selectTodo(todo.getId());
        assertFalse(test_todo == null, "无法单独显示");
        System.out.println("单独检索成功!");

        // 试图查看他人信息测试
        InformationException ex = assertThrows(InformationException.class, () -> {
            todoMapper.selectTodo(1);
        });
        // 校验异常消息
        assertEquals("不可访问他人信息!", ex.getMessage());

        // 输入类型匹配测试
        InformationException inputError = assertThrows(InformationException.class, () ->{
            todoMapper.selectTodo(999999999);
        });

        // 更新标题测试
        todoMapper.updateTitle(test_todo.getId(), newTitle);
        test_todo = todoMapper.selectTodo(test_todo.getId());
        assertFalse(!test_todo.getTitle().equals(newTitle),"标题更新失败");
        System.out.println("标题更新成功!");


        // 更新内容测试
        todoMapper.updateContent(test_todo.getId(), newContent);
        test_todo = todoMapper.selectTodo(test_todo.getId());
        assertFalse(!test_todo.getContent().equals(newContent), "内容更新失败");
        System.out.println("内容更新成功!");

        // 更改状态测试
        todoMapper.finishTodo(test_todo.getId());
        test_todo = todoMapper.selectTodo(test_todo.getId());
        assertFalse(!test_todo.getIs_done(), "状态更改异常");
        System.out.println("状态更改成功!");

        // 删除待办测试
        todoMapper.deleteTodo(test_todo.getId());
        System.out.println("删除待办成功");

        System.out.println("========== 所有测试全部通过！ ==========");
    }
}