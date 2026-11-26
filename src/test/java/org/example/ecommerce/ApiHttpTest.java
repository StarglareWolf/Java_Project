package org.example.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



// 这个测试主要用来测没有登录时的操作以及简单的注册登录测试
@SpringBootTest
@AutoConfigureMockMvc
public class ApiHttpTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Test()throws Exception {

        // 非登录状态下访问各种信息
        mockMvc.perform(get("/api/todo/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("请登录后访问!"))
                .andDo(result -> System.out.println("未登录不可访问信息通过!,返回内容: " + result.getResponse().getContentAsString()));

        mockMvc.perform(get("/api/todo/select?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("请登录后查询!"))
                .andDo(result -> System.out.println("未登录不可访问信息通过!,返回内容: " + result.getResponse().getContentAsString()));

        mockMvc.perform(get("/api/todo/finish?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("请登录后操作!"))
                .andDo(result -> System.out.println("未登录不可访问信息通过!,返回内容: " + result.getResponse().getContentAsString()));

        mockMvc.perform(get("/api/todo/delete?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("请登录后操作!"))
                .andDo(result -> System.out.println("未登录不可访问信息通过!,返回内容: " + result.getResponse().getContentAsString()));

        mockMvc.perform(get("/api/todo/add"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("请登录后查询!"))
                .andDo(result -> System.out.println("未登录不可访问信息通过!,返回内容: " + result.getResponse().getContentAsString()));

        mockMvc.perform(get("/api/todo/delete?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("请登录后操作!"))
                .andDo(result -> System.out.println("未登录不可访问信息通过!,返回内容: " + result.getResponse().getContentAsString()));

        // 注册测试
        String randomName = RandomStr.RandomString(6);
        String randomPassword = RandomStr.RandomString(6);
        mockMvc.perform(get("/api/register?").param("username", randomName).param("password", randomPassword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("注册成功!"))
                .andDo(result -> System.out.println("注册测试通过!,返回内容: " + result.getResponse().getContentAsString()));

        // 重复注册测试
        mockMvc.perform(get("/api/register?").param("username", randomName).param("password", randomPassword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("该用户已经存在,注册失败!"))
                .andDo(result -> System.out.println("重复注册测试通过!,返回内容: " + result.getResponse().getContentAsString()));

        // 登录测试
        mockMvc.perform(get("/api/login?").param("username", randomName).param("password", randomPassword))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println("存在账户后登录成功,返回内容: " + result.getResponse().getContentAsString()));

        // 修改密码测试
        mockMvc.perform(get("/api/update?").param("username", randomName).param("password", randomPassword).param("newpassword", randomPassword + "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("密码修改完成!"))
                .andDo(result -> System.out.println("更改密码测试通过,返回内容: " + result.getResponse().getContentAsString()));
        randomPassword += "test";

        // 输入错误测试
        mockMvc.perform(get("/api/login?username=no_test&password=test_password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("用户名或密码错误,登录失败!"))
                .andDo(result -> System.out.println("登录失败通过,返回内容: " + result.getResponse().getContentAsString()));

        // 没有注册就登录测试
        mockMvc.perform(get("/api/update?username=no_such_test&password=test_password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("不存在该用户或用户名/密码错误!"))
                .andDo(result -> System.out.println("试图更改其他人账号失败通过,返回内容: " + result.getResponse().getContentAsString()));



        // 退出登录测试
        mockMvc.perform(get("/api/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("退出成功!若需访问内容请重新登录!"))
                .andDo(result -> System.out.println("存在账户后登录成功,返回内容: " + result.getResponse().getContentAsString()));
    }
}


    /*MockMvc 的适用场景

MockMvc 适用于以下场景：

单元测试：验证控制器的业务逻辑是否正确。

集成测试：测试控制器与其他组件的交互。

接口调试：在没有后端服务的情况下测试接口逻辑。

重要注意事项

由于 MockMvc 是基于模拟的测试工具，它不会调用真实的后端服务。如果需要测试真实接口的行为，可以使用工具如 Postman 或通过完整的集成测试框架来实现。

MockMvc 的优势在于快速、高效地验证控制器逻辑，而不依赖外部环境，因此非常适合开发阶段的单元测试和接口验证。*/