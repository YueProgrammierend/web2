## Woche 1


```bash
$ =./mvnw clean package
$ ./mvnw spring-boot:run
```

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"; start http://localhost:8080

```



###### 本地ssh相关

```bash
git config --global user.name "YueProgrammierend"
git config --global user.email "   "
# git@github.com:<>/<>.git



# git config --local
git@two.github.com:<>/<>.git
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

git commit --amend -m "neuer Upload-Versuch"

git branch -M main
# 没有则新建 默认关联origin/main main 如何改见pull
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



###### 

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

| 注解                | 请求方法   | 作用                              |
| ----------------- | ------ | ------------------------------- |
| `@GetMapping`     | GET    | 从服务器获取资源（浏览、查询）                 |
| `@PostMapping`    | POST   | 向服务器提交数据（表单、创建对象）               |
| `@PutMapping`     | PUT    | 更新资源（整体替换）                      |
| `@PatchMapping`   | PATCH  | 局部更新资源                          |
| `@DeleteMapping`  | DELETE | 删除资源                            |
| `@RequestMapping` | 任意方法   | 上面这些的“通用父类”，可以指定 method 参数      |
| `@PathVariable`   | -      | 把 URL 里的 `{变量}` 部分绑定到方法参数       |
| `@RequestParam`   | -      | 把 URL 查询参数 `?key=value` 绑定到方法参数 |
| `@RequestBody`    | -      | 从请求体里读取 JSON/XML 并自动反序列化为对象     |



| 安全主题         | Node.js（JavaScript）           | Java Spring（Java）             |
| ---------------- | ------------------------------- | ------------------------------- |
| **认证**         | 使用 `Passport.js` + JWT/OAuth2 | 使用 Spring Security + OAuth2   |
| **输入验证**     | `express-validator`、`Joi`      | Hibernate Validator             |
| **加密**         | `bcryptjs`、`crypto` 模块       | `BCryptPasswordEncoder`         |
| **CSRF 防护**    | `csurf` 中间件                  | `spring-security-csrf`          |
| **HTTPS / TLS**  | `https` 模块 + 证书             | `server.ssl.*` 配置             |
| **配置管理**     | `.env` + dotenv                 | `application.properties`        |
| **依赖安全检查** | `npm audit`、`snyk`             | `OWASP Dependency-Check`        |
| **安全测试**     | `jest` + SAST 工具              | JUnit + SonarQube Security Scan |













| 关系         | LaTeX       | 含义           |
| ------------ | ----------- | -------------- |
| 真子集       | `\subset`   | A ⊂ B 且 A ≠ B |
| 子集（含等） | `\subseteq` | A ⊆ B          |
| 真超集       | `\supset`   | A ⊃ B 且 A ≠ B |
| 超集（含等） | `\supseteq` | A ⊇ B          |





## 🟥 **Festivalerstellung / Festivalverwaltung（Yue & Haakon）**

| 用例编号   | 名称                        |
| ---------- | --------------------------- |
| **UC0400** | Festival anlegen            |
| **UC0401** | Festival bearbeiten         |
| **UC0402** | Location buchen             |
| **UC0403** | Festival anzeigen           |
| **UC0410** | Festivalplanung abschließen |
| **UC0411** | Termin festlegen            |
| **UC0412** | Festival löschen            |

------

## 🟦 **Bereichsplanung / Personalplanung（Laran & Alex）**

### Bereichsplanung

| 用例编号   | 名称                          |
| ---------- | ----------------------------- |
| **UC0600** | Bereiche sperren              |
| **UC0611** | Bühnenpositionierung anpassen |
| **UC0610** | Bühnen mieten                 |
| **UC0621** | Cateringstände platzieren     |
| **UC0631** | Toilettenaufstellung anpassen |
| **UC0630** | Cateringstände mieten         |

### Personalplanung

| 用例编号   | 名称                          |
| ---------- | ----------------------------- |
| **UC0700** | Künstlerangebote holen        |
| **UC0701** | Künstler einstellen           |
| **UC0720** | Personal erstellen            |
| **UC0710** | Spielplan für Bühne erstellen |

------

## 🟩 **Cateringmanagement（Vanessa & Zoe）**

| 用例编号   | 名称                                 |
| ---------- | ------------------------------------ |
| **UC0011** | Speisen auswählen und abrechnen      |
| **UC0010** | Lagerbestände einsehen               |
| **UC0012** | Vom Lagerbestand abziehen            |
| **UC0111** | Nachbestellungen tätigen             |
| **UC0131** | Mitteilung an Festivalleitung senden |

------

## 🟢 **Kartenverkauf / Ticketmanagement**

| 用例编号    | 名称                           |
| ----------- | ------------------------------ |
| **UC0030**  | Kartenpreis festlegen          |
| **UC0031**  | Karten an Abendkasse verkaufen |
| **UC0032**  | Karten in Filiale verkaufen    |
| **UC0033**  | Tickets verkaufen              |
| **UC0030'** | Ticket auf Gültigkeit prüfen   |
| **UC0311**  | Tickets ausdrucken             |
| **UC0312**  | Tickets vom Bestand abziehen   |

------

## 🟩 **Verwaltung（kleiner grüner Bereich）**

| 用例编号   | 名称                           |
| ---------- | ------------------------------ |
| **UC0000** | Mitarbeiter-Logins verteilen   |
| **UC0120** | Nachrichten senden und ansehen |
| **UC0111** | Nachbestellungen tätigen       |

------

## 📊 **Datenvisualisierung（无明确负责人）**

| 用例编号   | 名称                                                 |
| ---------- | ---------------------------------------------------- |
| **UC0200** | Betriebswirtschaftliche Daten grafisch visualisieren |
| **UC0201** | Kostenaufstellung einsehen                           |
| **UC0203** | Aktuelle Besucherzahlen einsehen                     |
| **UC0204** | Verkaufszahlen des Caterings einsehen                |
| **UC0220** | Aktuelle Bühnenbelegung einsehen                     |

------

## 🧾 **Terminals / Gast**

| 用例编号   | 名称                         |
| ---------- | ---------------------------- |
| **UC0600** | Festivalgeländeplan einsehen |
| **UC0610** | Spielplan einsehen           |
| **UC0620** | Einloggen                    |
