package org.example.ecommerce;

import org.example.ecommerce.entity.Todo;
import org.example.ecommerce.mapper.TodoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest   // 启动测试
public class SecurityTest {
    @Autowired    // 自动调用相应类中的方法进行初始化
    private TodoMapper todoMapper;
    @Test     // 测试方法
    public void testSecurityRules() {
        System.out.println("开始测试");

        Integer fakeUserId = 999999;
        List<Todo> todos = todoMapper.listTodo(fakeUserId);
        assertTrue(todos == null || todos.isEmpty(), "测试失败，不存在的用户竟然包含待办!");

        int result = todoMapper.deleteTodo(1);
        assertEquals(0, result, "空账号竟然能够删除别人的待办？？？");

        System.out.println("测试通过");

    }
}
