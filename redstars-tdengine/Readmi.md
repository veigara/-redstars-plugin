### 1、快速入门

#### 1.1、简介

[redstars-tdengine](https://gitee.com/vfcm/redstars-plugin/tree/master/redstars-tdengine) 是一款时序数据库[tdengine](https://www.taosdata.com/)轻量级ORM 框架，只需简单配置，即可快速进行单表 CRUD 操作，从而节省大量时间。支持数据库连接池和多数据源，无需整合多数据源框架。使用静态调用的方式，执行CRUD方法，随时切换数据源，简洁代码，提升效率。

#### 1.2、快速开始

我们将通过一个简单的 Demo 来阐述redstars-tdengine 的便捷功能，在此之前，我们假设您已经：

- 拥有 Java 开发环境以及相应 IDE
- 熟悉 Spring Boot
- 熟悉 Maven
- 熟悉 tdengine相关概念和语法

------

现有一张Meter表，其表结构如下：

| ts            | current | voltage | phase | groupId | location             |
| ------------- | ------- | ------- | ----- | ------- | -------------------- |
| 1687243516197 | 11      | 116     | 0.9   | 6       | California.Sunnyvale |
| 1687243516198 | 12      | 118     | 0.6   | 6       | California.Sunnyvale |

其对应的数据库 Schema 脚本如下：

```sql
CREATE STABLE `meters` (`ts` TIMESTAMP, `current` FLOAT, `voltage` INT, `phase` FLOAT) TAGS (`groupid` INT, `location` VARCHAR(24))
```

其对应的数据库 Data 脚本如下：

```sql
INSERT INTO d25 USING meters tags(6,'California.Sunnyvale') VALUES (1687243516197, 11,116, 0,9);

INSERT INTO d25 USING meters tags(6,'California.Sunnyvale') VALUES (1687243516198, 12,118, 0,6);
```

**初始化工程**

创建一个空的 Spring Boot 工程

**添加依赖**

引入 Spring Boot Starter 父工程：

```xml
 <parent>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-parent</artifactId>
     <version>2.5+版本</version>
     <relativePath/> 
 </parent>
```

引入tdengine-spring-boot-starter、druid-spring-boot-starter依赖

```xml
<dependency>
    <groupId>io.github.veigara</groupId>
    <artifactId>tdengine-spring-boot-starter</artifactId>
    <version>1.1.0</version>
</dependency>
<!--druid连接池-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.15</version>
</dependency>  
```

**配置**

在 `application.yml` 配置文件中添加tdengine数据库、 druid数据库连接池的相关配置：

```yaml
tdengine:
  datasource:
    dynamic: # 多数据源配置
      druid:
        # 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
        initialSize: 0
        # 最大连接池数量
        maxActive: 8
        # 最小连接池数量
        minIdle: 0
        # 获取连接时最大等待时间，单位毫秒。配置了maxWait之后， 缺省启用公平锁，并发效率会有所下降， 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
        maxWait: 0
        # 是否缓存preparedStatement，也就是PSCache。 PSCache对支持游标的数据库性能提升巨大，比如说oracle。 在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。作者在5.5版本中使用PSCache，通过监控界面发现PSCache有缓存命中率记录， 该应该是支持PSCache。
        poolPreparedStatements: false
        # 要启用PSCache，必须配置大于0，当大于0时， poolPreparedStatements自动触发修改为true。 在Druid中，不会存在Oracle下PSCache占用内存过多的问题， 可以把这个数值配置大一些，比如说100
        maxOpenPreparedStatements: -1
        # 用来检测连接是否有效的sql，要求是一个查询语句。 如果validationQuery为null，testOnBorrow、testOnReturn、 testWhileIdle都不会其作用。
        validationQuery: SELECT 1
        # 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        testOnBorrow: true
        # 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
        testOnReturn: false
        # 建议配置为true，不影响性能，并且保证安全性。 申请连接的时候检测，如果空闲时间大于 timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        testWhileIdle: false
        # 有两个含义： 1) Destroy线程会检测连接的间隔时间 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
        timeBetweenEvictionRunsMillis: 60000
        # 物理连接初始化的时候执行的sql
        connectionInitSqls: SELECT 1
        # 属性类型是字符串，通过别名的方式配置扩展插件， 常用的插件有： 监控统计用的filter:stat  日志用的filter:log4j 防御sql注入的filter:wall
        filters: stat
        # 类型是List<com.alibaba.druid.filter.Filter>， 如果同时配置了filters和proxyFilters， 是组合关系，并非替换关系
        proxyFilters:
      # 默认数据源  
      primary: group_tdengine
      datasource:
        group_tdengine:
          url: jdbc:TAOS-RS://192.168.2.60:6041/information_schema
          username: root
          password: taosdata
          driver-class-name: com.taosdata.jdbc.rs.RestfulDriver  
```

**编码**

编写实体类MeterEntity.java

```java
@Data
@TdengineTableName("meters")
public class MeterEntity {
    @TdengineTableId
    private Date ts;

    private Long current;

    private Long voltage;

    private Double phase;
	// 注明子表的名称，不在数据库字段中
    @TdengineSubTableName
    private String dbName;

    @TdengineTableTag
    private Integer groupId;

    @TdengineTableTag
    private String location;
}
```

**开始使用**

添加测试类，进行功能测试

```java
@SpringBootTest
public class SampleTest {
	// 也可以不用注入，直接new TdengineDb()
    @Resource
    private TdengineDb db;

    @Test
    public void save() {
        MeterEntity meterEntity = new MeterEntity();
        meterEntity.setTs(new Date());
        meterEntity.setCurrent(11L);
        meterEntity.setVoltage(116L);
        meterEntity.setPhase(0.9);
        meterEntity.setDbName("d25");
        meterEntity.setGroupId(6);
        meterEntity.setLocation("California.Sunnyvale");
        boolean save = db.save(meterEntity);
        System.out.println("插入结果:"+save);
    }
  
   @Test
    public void testSelect() {
       TdengineQueryWrapper<MeterEntity> queryWrapper = new TdengineQueryWrapper<>(MeterEntity.class);
        queryWrapper.eq("voltage",116);
        List<MeterEntity> list = db.list(queryWrapper);
        System.err.println("getList结果："+JSONObject.toJSONString(list));
    }

}
```

**提示**

完整的代码示例请移步： [时序数据库测试案例](https://gitee.com/vfcm/redstars-plugin/tree/master/redstars-tdengine/tdengine-api/src/main/java/com/redstars/tdengine/example)

#### 1.3 安装

环境：JDK 8+、Maven

```xml
 <dependency>
     <groupId>io.github.veigara</groupId>
     <artifactId>tdengine-spring-boot-starter</artifactId>
     <version>1.1.0</version>
 </dependency>
```



### 2 、核心功能

#### 2.1、注解

| 注解                    | 说明                      |
| --------------------- | ----------------------- |
| @tdengineSubTableName | 实体类中配置插入子表名称            |
| @tdengineTableId      | 实体类中时间主键标识              |
| @tdengineTableName    | 实体类中配置超级表的名称            |
| @tdengineTableTag     | 实体类中标明标签tag的标识          |
| @tdengineCovert       | 结果集中将byte[]类型转为String类型 |

#### 2.2、yml配置

```yaml
tdengine:
  datasource:
    dynamic: # 多数据源配置
      druid: # 指明连接池的名称，可选druid、dbcp、tomcat、hikari
        # 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
        initialSize: 0
        # 最大连接池数量
        maxActive: 8
        # 最小连接池数量
        minIdle: 0
        # 获取连接时最大等待时间，单位毫秒。配置了maxWait之后， 缺省启用公平锁，并发效率会有所下降， 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
        maxWait: 0
        # 是否缓存preparedStatement，也就是PSCache。 PSCache对支持游标的数据库性能提升巨大，比如说oracle。 在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。作者在5.5版本中使用PSCache，通过监控界面发现PSCache有缓存命中率记录， 该应该是支持PSCache。
        poolPreparedStatements: false
        # 要启用PSCache，必须配置大于0，当大于0时， poolPreparedStatements自动触发修改为true。 在Druid中，不会存在Oracle下PSCache占用内存过多的问题， 可以把这个数值配置大一些，比如说100
        maxOpenPreparedStatements: -1
        # 用来检测连接是否有效的sql，要求是一个查询语句。 如果validationQuery为null，testOnBorrow、testOnReturn、 testWhileIdle都不会其作用。
        validationQuery: SELECT 1
        # 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        testOnBorrow: true
        # 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
        testOnReturn: false
        # 建议配置为true，不影响性能，并且保证安全性。 申请连接的时候检测，如果空闲时间大于 timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        testWhileIdle: false
        # 有两个含义： 1) Destroy线程会检测连接的间隔时间 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
        timeBetweenEvictionRunsMillis: 60000
        # 物理连接初始化的时候执行的sql
        connectionInitSqls: SELECT 1
        # 属性类型是字符串，通过别名的方式配置扩展插件， 常用的插件有： 监控统计用的filter:stat  日志用的filter:log4j 防御sql注入的filter:wall
        filters: stat
        # 类型是List<com.alibaba.druid.filter.Filter>， 如果同时配置了filters和proxyFilters， 是组合关系，并非替换关系
        proxyFilters:
      # 默认数据源  
      primary: group_tdengine
      datasource:
        # 数据源名称，可多个
        group_tdengine:
          url: jdbc:TAOS-RS://192.168.2.60:6041/information_schema
          username: root
          password: taosdata
          # 驱动名称
          driver-class-name: com.taosdata.jdbc.rs.RestfulDriver
        group_tdengine2:
          url: jdbc:TAOS-RS://192.168.2.60:6041/information_schema
          username: root
          password: taosdata
          driver-class-name: com.taosdata.jdbc.rs.RestfulDriver  
```

**连接池配置示例**

HikariCP连接池(hikari)

```yaml
hikari: # hikari 【连接池】相关的全局配置
  # 自动提交
  auto-commit: true
  # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
  connection-timeout: 30000
  # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:
  idle-timeout: 600000
  # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like '%timeout%';）
  max-lifetime: 1800000
  # 获取连接前的测试SQL
  connection-test-query: SELECT 1
  # 最小闲置连接数
  minimumIdle: 10
  # 连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
  maximumPoolSize: 10
  # 连接只读数据库时配置为true， 保证安全
  readOnly: false
```

Tomcat JDBC Pool连接池(tomcat)

```yaml
      tomcat:
        # (boolean) 连接池创建的连接的默认的auto-commit 状态
        defaultAutoCommit: true
        # (boolean) 连接池创建的连接的默认的read-only 状态。 如果没有设置则setReadOnly 方法将不会被调用。 ( 某些驱动不支持只读模式， 比如：Informix)
        defaultReadOnly: false
        # (int) 初始化连接： 连接池启动时创建的初始化连接数量，1。2 版本后支持
        initialSize: 10
        # (int) 最大活动连接： 连接池在同一时间能够分配的最大活动连接的数量， 如果设置为非正数则表示不限制
        maxActive: 100
        # (int) 最大空闲连接： 连接池中容许保持空闲状态的最大连接数量， 超过的空闲连接将被释放， 如果设置为负数表示不限制 如果启用，将定期检查限制连接，如果空闲时间超过minEvictableIdleTimeMillis 则释放连接 （ 参考testWhileIdle ）
        maxIdle: 8
        # (int) 最小空闲连接： 连接池中容许保持空闲状态的最小连接数量， 低于这个数量将创建新的连接， 如果设置为0 则不创建 如果连接验证失败将缩小这个值（ 参考testWhileIdle ）
        minIdle: 0
        # (int) 最大等待时间： 当没有可用连接时， 连接池等待连接被归还的最大时间( 以毫秒计数)， 超过时间则抛出异常， 如果设置为-1 表示无限等待
        maxWait: 30000
        # (String) SQL 查询， 用来验证从连接池取出的连接， 在将连接返回给调用者之前。 如果指定， 则查询必须是一个SQL SELECT 并且必须返回至少一行记录 查询不必返回记录，但这样将不能抛出SQL异常
        validationQuery: SELECT 1
        # (boolean) 指明是否在从池中取出连接前进行检验， 如果检验失败， 则从池中去除连接并尝试取出另一个。注意： 设置为true 后如果要生效，validationQuery 参数必须设置为非空字符串 参考validationInterval以获得更有效的验证
        testOnBorrow: false
        # (boolean) 指明是否在归还到池中前进行检验 注意： 设置为true 后如果要生效，validationQuery 参数必须设置为非空字符串
        testOnReturn: false
        # (boolean) 指明连接是否被空闲连接回收器( 如果有) 进行检验。 如果检测失败， 则连接将被从池中去除。注意： 设置为true 后如果要生效，validationQuery 参数必须设置为非空字符串
        testWhileIdle: false
```

DBCP连接池(dbcp)

```yaml
     dbcp:
       # (boolean) 连接池创建的连接的默认的auto-commit 状态
       defaultAutoCommit: true
       # (boolean) 连接池创建的连接的默认的read-only 状态。 如果没有设置则setReadOnly 方法将不会被调用。 ( 某些驱动不支持只读模式， 比如：Informix)
       defaultReadOnly: false
       # (int) 初始化连接： 连接池启动时创建的初始化连接数量，1。2 版本后支持
       initialSize: 10
       # (int) 最大活动连接： 连接池在同一时间能够分配的最大活动连接的数量， 如果设置为非正数则表示不限制
       maxActive: 100
       # (int) 最大空闲连接： 连接池中容许保持空闲状态的最大连接数量， 超过的空闲连接将被释放， 如果设置为负数表示不限制 如果启用，将定期检查限制连接，如果空闲时间超过minEvictableIdleTimeMillis 则释放连接 （ 参考testWhileIdle ）
       maxIdle: 8
       # (int) 最小空闲连接： 连接池中容许保持空闲状态的最小连接数量， 低于这个数量将创建新的连接， 如果设置为0 则不创建 如果连接验证失败将缩小这个值（ 参考testWhileIdle ）
       minIdle: 0
       # (int) 最大等待时间： 当没有可用连接时， 连接池等待连接被归还的最大时间( 以毫秒计数)， 超过时间则抛出异常， 如果设置为-1 表示无限等待
       maxWait: 30000
       # (String) SQL 查询， 用来验证从连接池取出的连接， 在将连接返回给调用者之前。 如果指定， 则查询必须是一个SQL SELECT 并且必须返回至少一行记录 查询不必返回记录，但这样将不能抛出SQL异常
       validationQuery: SELECT 1
       # (boolean) 指明是否在从池中取出连接前进行检验， 如果检验失败， 则从池中去除连接并尝试取出另一个。注意： 设置为true 后如果要生效，validationQuery 参数必须设置为非空字符串 参考validationInterval以获得更有效的验证
       testOnBorrow: false
       # (boolean) 指明是否在归还到池中前进行检验 注意： 设置为true 后如果要生效，validationQuery 参数必须设置为非空字符串
       testOnReturn: false
       # (boolean) 指明连接是否被空闲连接回收器( 如果有) 进行检验。 如果检测失败， 则连接将被从池中去除。注意： 设置为true 后如果要生效，validationQuery 参数必须设置为非空字符串
       testWhileIdle: false
```

Druid连接池(druid)

```yaml
      druid:
        # 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
        initialSize: 0
        # 最大连接池数量
        maxActive: 8
        # 最小连接池数量
        minIdle: 0
        # 获取连接时最大等待时间，单位毫秒。配置了maxWait之后， 缺省启用公平锁，并发效率会有所下降， 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
        maxWait: 0
        # 是否缓存preparedStatement，也就是PSCache。 PSCache对支持游标的数据库性能提升巨大，比如说oracle。 在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。作者在5.5版本中使用PSCache，通过监控界面发现PSCache有缓存命中率记录， 该应该是支持PSCache。
        poolPreparedStatements: false
        # 要启用PSCache，必须配置大于0，当大于0时， poolPreparedStatements自动触发修改为true。 在Druid中，不会存在Oracle下PSCache占用内存过多的问题， 可以把这个数值配置大一些，比如说100
        maxOpenPreparedStatements: -1
        # 用来检测连接是否有效的sql，要求是一个查询语句。 如果validationQuery为null，testOnBorrow、testOnReturn、 testWhileIdle都不会其作用。
        validationQuery: SELECT 1
        # 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        testOnBorrow: true
        # 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
        testOnReturn: false
        # 建议配置为true，不影响性能，并且保证安全性。 申请连接的时候检测，如果空闲时间大于 timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        testWhileIdle: false
        # 有两个含义： 1) Destroy线程会检测连接的间隔时间 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
        timeBetweenEvictionRunsMillis: 60000
        # 物理连接初始化的时候执行的sql
        connectionInitSqls: SELECT 1
        # 属性类型是字符串，通过别名的方式配置扩展插件， 常用的插件有： 监控统计用的filter:stat  日志用的filter:log4j 防御sql注入的filter:wall
        filters: stat
        # 类型是List<com.alibaba.druid.filter.Filter>， 如果同时配置了filters和proxyFilters， 是组合关系，并非替换关系
        proxyFilters:
```

注意：druid-spring-boot-starter 连接池最高支持1.2.15

#### 2.3、CRUD 接口

使用com.redstars.tdengine.api.TdengineDb类的方法调用接口

```java
方式一：
@Resource
TdengineDb db;
        方式二：
        TdengineDb db = new TdengineDb();
```

**Save**

```java
// 使用默认数据源插入
boolean save(Object entity);
// 使用指定数据源插入  
        boolean save(String dsName,Object entity);
// 使用默认数据源，根据sql插入
        boolean insertOrUpdate(String sql, Object... params);
// 使用指定数据源，根据sql插入   
        boolean insertOrUpdate(String dbName,String sql, Object... params);
```

参数说明

| 类型     | 参数名    | 描述              |
| ------ | ------ | --------------- |
| Object | entity | 实体类对象           |
| String | dsName | 数据源名称           |
| String | sql    | 自定义sql语句        |
| String | params | 自定义sql语句中的?对应的值 |

**Remove**

```java
//使用默认数据源,根据 queryWrapper 设置的条件，删除记录
boolean remove(TdengineQueryWrapper<?> queryWrapper);
//使用指定数据源,根据 queryWrapper 设置的条件，删除记录
        boolean remove(String dsName, TdengineQueryWrapper<?> queryWrapper);
```

参数说明

| 类型                   | 参数名          | 描述                         |
| -------------------- | ------------ | -------------------------- |
| TdengineQueryWrapper | queryWrapper | 实体包装类 TdengineQueryWrapper |
| String               | dsName       | 数据源名称                      |

**Get**

```java
// 使用默认数据源,根据 queryWrapper，查询一条记录
T getOne(TdengineQueryWrapper<T> queryWrapper);
// 使用指定数据源,根据 queryWrapper，查询一条记录
        T getOne(String dsName,TdengineQueryWrapper<T> queryWrapper);
// 使用默认数据源,根据sql，查询一条记录,并转换成目标对象
        T getOne(String sql, Class<T> beanClass, Object... params);
// 使用指定数据源,根据sql，查询一条记录,并转换成目标对象
        T getOne(String dbName,String sql, Class<T> beanClass, Object... params);
// 使用默认数据源,根据sql，查询一条记录,并转换成Entity对象
        Entity getOne(String sql, Object... params);
// 使用指定数据源,根据sql，查询一条记录,并转换成Entity对象
        Entity getOne(String dbName, String sql, Object... params);
// 使用默认数据源,根据queryWrapper，查询一条记录,并转换成Map对象
        Map<String, Object> getMap(TdengineQueryWrapper<?> queryWrapper);
// 使用指定数据源,根据queryWrapper，查询一条记录,并转换成Map对象
        Map<String, Object> getMap(String dsName,TdengineQueryWrapper<?> queryWrapper);
// 使用默认数据源,根据sql，查询一条记录中某列值,并转换成Number对象
        Number getNumber(String sql, Object... params);
// 使用指定数据源,根据sql，查询一条记录中某列值,并转换成Number对象
        Number getNumber(String dbName,String sql, Object... params);
// 使用默认数据源,根据queryWrapper，查询一条记录中某列值,并转换成Number对象
        Number getNumber(TdengineQueryWrapper<?> queryWrapper);
// 使用指定数据源,根据queryWrapper，查询一条记录中某列值,并转换成Number对象
        Number getNumber(String dsName,TdengineQueryWrapper<?> queryWrapper);
// 使用默认数据源,根据sql，查询一条记录中某列值,并转换成String对象
        String getString(String sql, Object... params);
// 使用指定数据源,根据sql，查询一条记录中某列值,并转换成String对象
        String getString(String dbName,String sql, Object... params);
// 使用默认数据源,根据queryWrapper，查询一条记录中某列值,并转换成String对象
        String getString(TdengineQueryWrapper<?> queryWrapper);
// 使用指定数据源,根据queryWrapper，查询一条记录中某列值,并转换成String对象
        String getString(String dbName,TdengineQueryWrapper<?> queryWrapper);
```

参数说明

| 类型                   | 参数名          | 描述                         |
| -------------------- | ------------ | -------------------------- |
| TdengineQueryWrapper | queryWrapper | 实体包装类 TdengineQueryWrapper |
| String               | dsName       | 数据源名称                      |
| String               | sql          | 自定义sql语句                   |
| String               | params       | 自定义sql语句中的?对应的值            |
| Class<T>             | beanClass    | 结果集转换目标对象的class            |

**List**

```java
// 使用默认数据源,根据queryWrapper条件，查询全部记录
List<T> list(TdengineQueryWrapper<T> queryWrapper);
// 使用指定数据源,根据queryWrapper条件，查询全部记录
        List<T> list(String dsName,TdengineQueryWrapper<T> queryWrapper);
// 使用默认数据源,根据sql，查询全部记录,并转换成目标对象
        List<T> list(String sql, Class<T> beanClass, Object... params)
// 使用指定数据源,根据sql，查询全部记录,并转换成目标对象
        List<T> list(String dbName,String sql, Class<T> beanClass, Object... params);
// 使用默认数据源,根据sql，查询全部记录,并转换成Entity对象
        List<Entity> list(String sql, Object... params);
// 使用指定数据源,根据sql，查询全部记录,并转换成Entity对象
        List<Entity> list(String dbName,String sql, Object... params);
// 使用默认数据源,根据sql，查询全部记录,并转换成Entity对象
        List<Entity> list(String sql, Map<String, Object> params);
// 使用指定数据源,根据sql，查询全部记录,并转换成Entity对象
        List<Entity> list(String dbName,String sql, Map<String, Object> params);
// 使用默认数据源,根据queryWrapper条件，查询全部记录,并转换成Entity对象
        List<Entity> listMaps(TdengineQueryWrapper<?> queryWrapper);
// 使用指定数据源,根据queryWrapper条件，查询全部记录,并转换成Entity对象
        List<Entity> listMaps(String dsName,TdengineQueryWrapper<?> queryWrapper);
```

参数说明

| 类型                   | 参数名          | 描述                         |
| -------------------- | ------------ | -------------------------- |
| TdengineQueryWrapper | queryWrapper | 实体包装类 TdengineQueryWrapper |
| String               | dsName       | 数据源名称                      |
| String               | sql          | 自定义sql语句                   |
| String               | params       | 自定义sql语句中的?对应的值            |
| Map<String, Object>  | params       | 自定义sql语句中的?对应的值            |
| Class<T>             | beanClass    | 结果集转换目标对象的class            |

**Page**

```java
// 使用默认数据源,根据queryWrapper条件，分页查询
PageResult<T> page(Page page, TdengineQueryWrapper<T> queryWrapper);
// 使用指定数据源,根据queryWrapper条件，分页查询
        PageResult<T> page(String dsName,Page page, TdengineQueryWrapper<T> queryWrapper);
// 使用默认数据源,根据sql，分页查询,并转换成目标对象
        PageResult<T> page(Page page, Class<T> beanClass, String sql, Object... params);
// 使用指定数据源,根据sql，分页查询,并转换成目标对象
        PageResult<T> page(String dbName,Page page, Class<T> beanClass, String sql, Object... params);
// 使用默认数据源,根据sql，分页查询,并转换成Entity对象
        PageResult<Entity> page(Page page, String sql, Object... params);
// 使用指定数据源,根据sql，分页查询,并转换成Entity对象
        PageResult<Entity> page(String dbName,Page page, String sql, Object... params);

```

参数说明

| 类型                   | 参数名          | 描述                         |
| -------------------- | ------------ | -------------------------- |
| TdengineQueryWrapper | queryWrapper | 实体包装类 TdengineQueryWrapper |
| String               | dsName       | 数据源名称                      |
| String               | sql          | 不带分页的自定义sql语句              |
| String               | params       | 自定义sql语句中的?对应的值            |
| Page                 | page         | 翻页对象                       |

**Count**

```java
// 使用默认数据源,根据 Wrapper 条件，查询总记录数
long count(TdengineQueryWrapper<?> queryWrapper);
// 使用指定数据源,根据 Wrapper 条件，查询总记录数
        long count(String dsName,TdengineQueryWrapper<?> queryWrapper);
```

参数说明

| 类型                   | 参数名          | 描述                         |
| -------------------- | ------------ | -------------------------- |
| TdengineQueryWrapper | queryWrapper | 实体包装类 TdengineQueryWrapper |
| String               | dsName       | 数据源名称                      |

**Db类**

使用静态调用的方式，获取数据源对象Db

```java
// 获取默认数据源对象
Db use();
// 获取指定数据源对象
        Db use(String dsName)
```

参数说明

| 类型     | 参数名    | 描述    |
| ------ | ------ | ----- |
| String | dsName | 数据源名称 |