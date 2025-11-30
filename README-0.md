[TOC]

```
Festival fest = festivalRepo.findById(id).orElse(null);
if (fest == null) {
    return "redirect:/festival?error=festival_not_found";
}
```

这个表明实际情况中festival发生了改变（比如重启仓库）



###### 本地ssh相关

```bash
git config --global user.name "YueProgrammierend"
git config --global user.email "   "
# git@github.com:<>/<>.git

# 有多个git账户
git config --local
#git@two.github.com:<>/<>.git
```

```bash
git init

git remote add origin git@two.github.com:<>/<>.git
#如果已有想要更改，⬆️会报错，使用⬇️
git remote set-url origin git@two.github.com:<>/<>.git
```



###### 拉取 pull

```bash
git remote -v #查看远程仓库状态

git branch --set-upstream-to=origin/documentation documentation
#origin/main main

git pull
```

###### 推送 push

（常规流程：）

```bash
git add -A
git commit -m "Initial commit"
git branch -M main

git push -u origin main
git push -u origin main -force #强制覆盖云端（更改不会呈现）
```





```bash
git add -A
git add .
git add -u

# 重命名
git commit --amend -m "neuer Upload-Versuch"

git branch -M main
# 使用主分支，命名为main
# 默认关联origin/main main 如何改分支branch见pull
# git branch -M 表示强制重命名
git checkout -b yue
# 新建分支 有分支报错
git switch -c yue
# 转换分支，没有则新建
```

###### .gitignore

```bash
#

# IntelliJ
out/
.idea/
*.iml

# VS Code
.vscode/

# MagicDraw
*.mdzip.bak

# OS specific
.DS_Store
Thumbs.db
Desktop.ini

```



## Woche 1



```bash
$ =./mvnw clean package
$ ./mvnw spring-boot:run
```

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"; start http://localhost:8081
```



###### 网关

| 没有网关                             | 有网关       |
| -------------------------------- | --------- |
| 用户必须记住每个服务地址（如 `:8081`, `:8082`） | 用户只访问一个域名 |
| 每个服务都要处理鉴权、跨域、日志                 | 网关统一处理    |
| 系统扩展时需要修改很多客户端配置                 | 只改网关路由即可  |



### 🧩  [https://start.spring.io/](https://start.spring.io/) 在线生成基于该脚手架的项目

#### Spring Boot ↑

#### 1. Spring Framework

#### 2. Spring Data JPA

#### 3. Thymeleaf

#### 4. Semantic UI and Bootstrap

#### 5. HTMX









## Woche 2

- [x] 修改`Catalog.html`
- [x] 新建java文件 / <mark>导入已有</mark>（`CatalogCollect.java`）
    - [x] 导入搜索、分类
    - [x]  用到了什么插件？  `Thymeleaf`
- [ ] 是否需要修改其他java、html？
    - 不需要

### [filters](https://docs.spring.io/spring-framework/reference/web/webmvc/filters.html)

| 注解              | 请求方法 | 作用                                         |
| ----------------- | -------- | -------------------------------------------- |
| `@GetMapping`     | GET      | 从服务器获取资源（浏览、查询）               |
| `@PostMapping`    | POST     | 向服务器提交数据（表单、创建对象）           |
| `@RequestMapping` | 任意方法 | 上面这些的“通用父类”，可以指定 method 参数   |
| `@PutMapping`     | PUT      | 更新资源（整体替换）                         |
| `@PatchMapping`   | PATCH    | 局部更新资源                                 |
| `@DeleteMapping`  | DELETE   | 删除资源                                     |
| `@PathVariable`   | -        | 把 URL 里的 `{变量}` 部分绑定到方法参数      |
| `@RequestParam`   | -        | 把 URL 查询参数 `key=value` 绑定到方法参数   |
| `@RequestBody`    | -        | 从请求体里读取 JSON/XML 并自动反序列化为对象 |



```html
project/
│
├── api/
│   ├── account/
│   │   ├── UserController.java
│   │   ├── RegistrationForm.java
│   │   └── UserForm.java
│   │
│   ├── bereichsplanung/
│   │   └── BereichController.java
│   │
│   ├── catering/
│   │   └── LagerController.java
│   │
│   ├── festival/
│   │   ├── FestivalController.java
│   │   └── FestivalForm.java
│   │
│   ├── location/
│   │   ├── LocationController.java
│   │   └── LocationForm.java
│   │
│   ├── staffmanagement/
│   │   ├── kuenstler/
│   │   │   └── (如果你有 Controller 则放这里)
│   │   └── personal/
│   │       └── (如果你有 Controller 则放这里)
│   │
│   ├── stageassignment/
│   │   ├── StageController.java
│   │   └── SlotForm.java
│   │
│   ├── tickets/
│   │   └── TicketController.java
│   │
│   └── welcome/
│       └── WelcomeController.java
│
│
├── application/
│   ├── account/
│   │   ├── UserManagement.java
│   │   └── UserDataInitializer.java
│   │
│   ├── bereichsplanung/
│   │   ├── BereichManager.java
│   │   ├── BereichInitializer.java
│   │   └── BereichInitializerManager.java
│   │
│   ├── catering/
│   │   ├── StorageManagement.java
│   │   └── StorageInitializer.java
│   │
│   ├── festival/
│   │   ├── FestivalManagement.java
│   │   └── FestivalInitializer.java
│   │
│   ├── location/
│   │   ├── LocationManagement.java
│   │   ├── LocationInitializer.java
│   │   └── LocationDataInitializer.java
│   │
│   ├── staffmanagement/
│   │   ├── kuenstler/
│   │   │   ├── KuenstlerRegler.java
│   │   │   └── KuenstlerKatalogInitialisator.java
│   │   └── personal/
│   │       ├── PersonalRegler.java
│   │       └── PersonalKatalogInitialisator.java
│   │
│   ├── stageassignment/
│   │   └── StagePlanService.java
│   │
│   └── tickets/
│       ├── TicketManagement.java
│       └── TicketInitializer.java
│
│
├── domain/
│   ├── account/
│   │   ├── User.java
│   │   ├── UserIdentifier.java
│   │   └── UserRole.java
│   │
│   ├── bereichsplanung/
│   │   ├── Bereich.java
│   │   ├── Bereichtyp.java
│   │   ├── Buehne.java
│   │   ├── Camping.java
│   │   ├── Catering.java
│   │   ├── ErsteHilfeZelt.java
│   │   ├── Toiletten.java
│   │   └── Wiese.java
│   │
│   ├── catering/
│   │   └── Lebensmittel.java
│   │
│   ├── festival/
│   │   ├── Festival.java
│   │   └── FestivalIdentifier.java
│   │
│   ├── location/
│   │   ├── Location.java
│   │   └── LocationIdentifier.java
│   │
│   ├── staffmanagement/
│   │   ├── kuenstler/
│   │   │   ├── Band.java
│   │   │   ├── Kuenstler.java
│   │   │   ├── KuenstlerTyp.java
│   │   │   └── SoloKuenstler.java
│   │   └── personal/
│   │       ├── AbstractEinstellbar.java
│   │       ├── EinstellStatus.java
│   │       ├── Personal.java
│   │       ├── PersonalTyp.java
│   │       ├── CateringPersonal.java
│   │       ├── SecurityPersonal.java
│   │       ├── Technician.java
│   │       └── VerkaufsPersonal.java
│   │
│   ├── stageassignment/
│   │   ├── StageSlot.java
│   │   └── Slot.java  
│   │
│   └── tickets/
│       ├── Ticket.java
│       └── SoldTicket.java
│
│
├── infrastructure/
│   ├── account/
│   │   └── UserRepository.java
│   │
│   ├── bereichsplanung/
│   │   └── BereichRepository.java
│   │
│   ├── catering/
│   │   └── LebensmittelRepository.java
│   │
│   ├── festival/
│   │   └── FestivalRepository.java
│   │
│   ├── location/
│   │   └── LocationRepository.java
│   │
│   ├── staffmanagement/
│   │   ├── kuenstler/
│   │   │   └── KuenstlerKatalog.java
│   │   └── personal/
│   │       └── PersonalKatalog.java
│   │
│   ├── stageassignment/
│   │   └── SlotRepo.java
│   │
│   └── tickets/
│       ├── TicketRepository.java
│       └── SoldTicketRepository.java
│
└── Application.java   ← 启动类

```



以下为德语翻译：

------

Ein weiteres großes logisches Problem ist, dass **UserAccount** aus dem *Salespoint Framework* möglicherweise in vielen Funktionen **nicht synchron** mit unserem eigenen **User** ist.
 Die Methoden `public UserRole getRole()` und `public void setRole()` im **UserForm** sind daher möglicherweise **nicht notwendig**.

Wenn diese obenen Fragen werden, könnten der gesamte **User**, **UserForm**, sowie **Identifier** und **Repository** möglicherweise überflüssig sein, und man könnte direkt **UserAccount** verwenden und dann **Festival** an **UserAccount** binden.

**UserManagement**, **UserDataInitializer**, **UserRole** und **UserDataInitializer** sind dagegen notwendig.

------

如需我再做润色、专业化，或写成邮件格式，也可以告诉我。
