# Lap Trinh Java

Repo tong de nop cac bai lab mon Lap trinh Java.

Hien tai repo co 3 project Maven rieng tu file `Ch2_Lab_Spring_Core_DI.pdf`:

- Lab 1: Tao bean, constructor injection, interface abstraction, `@Qualifier`.
- Lab 2: Bean lifecycle, `@PostConstruct`, `@PreDestroy`, singleton va prototype.
- Lab 3: Spring Boot co ban, `application.properties`, `@Value`, `CommandLineRunner`, profiles.

## Yeu cau

- JDK 17 tro len.
- Maven 3.8 tro len.

## Cach chay

Build project:

```powershell
mvn clean package
```

Chay Lab 1:

```powershell
mvn -pl Lab-1 exec:java "-Dexec.mainClass=com.lab.Main"
```

Chay Lab 2:

```powershell
mvn -pl Lab-2 exec:java "-Dexec.mainClass=com.lab.lifecycle.Main"
```

Chay Lab 3 voi profile mac dinh `dev`:

```powershell
mvn -pl Lab-3 spring-boot:run
```

Chay Lab 3 voi profile `prod`:

```powershell
mvn -pl Lab-3 spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=prod"
```

## Cau hoi on tap

### Lab 1

1. `@Component` danh dau class cua minh de Spring tu quet va tao bean. `@Bean` dung trong class `@Configuration` de khai bao bean thu cong, phu hop khi class den tu thu vien ngoai hoac can logic tao object rieng.
2. Constructor injection de hon vi dependency co the la `final`, object duoc tao o trang thai hop le, de unit test va khong can reflection.
3. `@Qualifier` chon dung bean khi co nhieu bean cung interface. `@Primary` dat mot bean mac dinh, con `@Qualifier` ro rang hon tai diem inject.
4. `Comment` khong nen la bean vi no la object du lieu theo tung request/use case, khong phai service dung chung do Spring quan ly.

### Lab 2

1. `ServiceB` duoc tao truoc `ServiceA` vi `ServiceA` can `ServiceB` trong constructor.
2. Constructor tao object, con `@PostConstruct` chay sau khi Spring da inject xong dependency, phu hop de validate config hoac khoi tao tai nguyen can dependency.
3. Singleton bean co mutable state co the bi nhieu thread thay doi cung luc, gay race condition va du lieu sai.
4. Inject prototype vao singleton bang `@Autowired` chi lay prototype mot lan khi singleton duoc tao, nen cac lan dung sau van la cung instance. Can lay prototype moi qua `ApplicationContext`, `ObjectProvider`, hoac lookup method.

### Lab 3

1. `@SpringBootApplication` gom `@Configuration`, `@ComponentScan`, va `@EnableAutoConfiguration`.
2. `@Value("${key}")` bat buoc key ton tai. `@Value("${key:default}")` dung gia tri mac dinh neu key khong ton tai.
3. `CommandLineRunner.run()` duoc goi sau khi Spring context san sang.
4. Khi profile `prod` duoc kich hoat, `application.properties` van duoc doc truoc, sau do `application-prod.properties` ghi de cac key trung.
