版本

Maven 4.0.0

SpringBoot 3.5.13

Java 25

MySQL 8.0 Command Line Client

数据库初始化: 打开 schema.sql 并将其中的代码复制到 mysql 运行

访问地址： http://localhost:8080 
```bash
# 后缀
UserController 层面：
1. /api/register?username=你的注册账号名&password=你的注册密码
2. /api/login?username=你的登录账号名&password=你的登录密码
3. /api/update?username=你的账号名&password=你的旧密码&newpassword=你的新密码
4. /api/logout # 登出

TodoController 层面
1. /api/todo/add?title=你的待办事项标题&content=你的待办事项内容
2. /api/todo/list  # 罗列你的所有待办事项
3. /api/todo/select?id=你的待办事项序号
4. /api/todo/finish?id=你的待办事项序号
5. /api/todo/delete?id=你的待办事项序号
6. /api/todo/update/title?id=待更新序号&title=新标题
7. /api/todo/update/content?id=待更新序号&content=新内容
8. /api/todo/delete?id=需要删除的事项序号
```

启动步骤

1. 初始化数据库
2. 进入 resources 的application.yml 将 username 和 password 改成你自己的数据库的用户名和密码
3. 打开 EcommerceApplication 并运行
4. 打开测试项 ApiHttpTest 进行测试运行，测试通过即为连接成功

测试方式(代码必须处于运行当中，通过 EcommerceApplication 运行)

1. 浏览器手动测试：打开浏览器，先通过上述 register 和 login 登录后，按照上述给定网址进行相应操作测试
2. [apifox 手动测试](https://docs.apifox.com/doc-5743620) 按照文档执行完毕后选中 UserController 和 TodoController 右键选择 upload to apifox 即可开始测试
3. apifox 自动测试: 打开你的 Apifox, 导入文件中的 ecommerce项目测试.apifox.json,然后先通过 register 和 login 测试后再进行其它测试
3. [网页测试](http://localhost:8080/login.html) 注册登录后进行测试

实际运行结果：未登录/登录信息错误时不能处理任何东西，也不能查看任何信息，自己无法查询他人信息

**注意事项**

待办事项编号是逐步递增的，并不是每次都从 1 开始，因此需要进行增删查改时，最好先通过 list 查看自己的已有事项的 id,不要错看成 user_id

没有使用 mybatis-plus,而是使用 springboot 自带的 jdbcTemplate