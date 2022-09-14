### [原项目](https://github.com/zlt2000/springs-boot-plugin-test)
#### 目录结构
- **plugin-impl**：插件实现
- **plugin-users**：插件使用样例
- **plugins**：插件包存放目录

#### 测试 Spring Boot 动态加载 Jar 包
1. 启动时动态加载
2. 运行时动态加载

#### 测试步骤
进入 plugin-users 工程
1. 启动 plugin-users 工程
2. 测试启动时动态加载jar：`http://127.0.0.1:8080/hello`
3. 测试运行时动态加载jar：`http://127.0.0.1:8080/reload`