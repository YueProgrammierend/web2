


```bash
$ ./mvnw clean package
$ ./mvnw spring-boot:run
```

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"; start http://localhost:8080

```

```bash
git config --global user.name "YueProgrammierend"
git config --global user.email "   "

git add .
git commit -m "Initial commit"
git branch -M main
git push -u origin main

git push -u origin main -force #强制覆盖云端
```


![](picture/image-1.png)

![](picture/image-2.png)

| 没有网关                             | 有网关       |
| -------------------------------- | --------- |
| 用户必须记住每个服务地址（如 `:8081`, `:8082`） | 用户只访问一个域名 |
| 每个服务都要处理鉴权、跨域、日志                 | 网关统一处理    |
| 系统扩展时需要修改很多客户端配置                 | 只改网关路由即可  |
