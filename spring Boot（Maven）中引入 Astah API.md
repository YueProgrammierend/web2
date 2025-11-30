## 在 Spring Boot（Maven）中引入 Astah API

### `pom.xml`  依赖项

API JAR 在：
`mac` （win 可能不同）

```
/Applications/professional/Contents/Java/api/
```

里面会有主要 jar：

```
astah-api.jar
astah-common.jar
astah-model.jar
astah-diagram.jar
jude-api.jar
```

 

在 Spring Boot 项目中创建` lib`，把以上内容复制过去：

```
your-app/
  lib/
  
    astah-api.jar
    astah-common.jar
    astah-model.jar
    astah-diagram.jar
```

```bash
➜  swt25w21-yue git:(yue) ✗ mkdir lib
```

```bash
cp "/Applications/astah professional/astah professional.app/Contents/Java/astah-api.jar" lib/

cp "/Applications/astah professional/astah professional.app/Contents/Java/astah-pro.jar" lib/

cp "/Applications/astah professional/astah professional.app/Contents/Java/lib/astah-layout-o-1.3.1.jar" lib/

cp "/Applications/astah professional/astah professional.app/Contents/Java/lib/yfiles-for-java-swing-o-3.3.0.1.1.jar" lib/


```



在 `pom.xml` 中加入：

```xml
<dependencies>

    <dependency>
        <groupId>com.change-vision</groupId>
        <artifactId>astah-api</artifactId>
        <version>1.0</version>
        <scope>system</scope>
        <systemPath>/Applications/astah professional/Contents/Java/api/astah-api.jar</systemPath>
    </dependency>

    <dependency>
        
			You can list the current dependencies and their versions using the
			`mvn dependency:list` command.

			Use Maven Central for package search (https://search.maven.org/).
		-->
        <!-- astah-api -->
        <dependency>
            <groupId>com.change-vision</groupId>
            <artifactId>astah-api</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/astah-api.jar</systemPath>
        </dependency>

        <!-- astah-uml -->
        <dependency>
            <groupId>com.change-vision</groupId>
            <artifactId>astah-uml</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/astah-uml.jar</systemPath>
        </dependency>
        
        <!-- another -->
        <dependency>
            <groupId>com.change-vision</groupId>
            <artifactId>astah-layout</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/astah-layout-o-1.3.1.jar</systemPath>
        </dependency>

        <dependency>
            <groupId>com.change-vision</groupId>
            <artifactId>yfiles-swing</artifactId>
            <version>3.3</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/yfiles-for-java-swing-o-3.3.0.1.1.jar</systemPath>
        </dependency>

        
        
</dependencies>
```



------

###  测试 Astah API 是否工作

建一个 Spring Boot 的测试类：

```java
import com.change_vision.jude.api.inf.Astah;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AstahTest implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ProjectAccessor pa = Astah.getProjectAccessor();
        System.out.println("Astah API loaded: " + pa);
    }
}
```

运行 Spring Boot → 若看到输出：

```
Astah API loaded: com.change_vision.jude.internal.api.project.ProjectAccessorImpl@xxxx
```

则表示 **Spring Boot + Astah API 在 macOS 上已成功集成**。

