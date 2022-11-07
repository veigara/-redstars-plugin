sharejdbc分表分库[`v5.2.0`](https://shardingsphere.apache.org/document/current/cn/dev-manual/sharding/#implementation-classes)



###1.内置分片算法
背景信息
ShardingSphere 内置提供了多种分片算法，按照类型可以划分为自动分片算法、标准分片算法、复合分
片算法和 Hint 分片算法，能够满足用户绝大多数业务场景的需要。此外，考虑到业务场景的复杂性，内
置算法也提供了自定义分片算法的方式，用户可以通过编写 Java 代码来完成复杂的分片逻辑。

注释：所有内置分片算法生成的分表名必须在设置的actual-data-nodes（表名范围）内
####1.1自动分片
#####1.1.1 取模分片算法
类型：MOD

全类名：[`org.apache.shardingsphere.sharding.algorithm.sharding.mod.ModShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/features/sharding/core/src/main/java/org/apache/shardingsphere/sharding/algorithm/sharding/mod/ModShardingAlgorithm.java)

配置属性：

| 属性名称           | 数据类型 | 说明          |
| -------------- | ---- | ----------- |
| sharding‐count | int  | 分片数量(分多少张表) |

示例

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
    username: root
    password: 123456
  config:
    use-legacy-processing: true
  shardingsphere:
    datasource:
      names: ds0
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root
        password: 123456
    #配置分片策略和主键策略
    rules:
      sharding:
        # 定义主键算法，key-algorithm为自定义算法名，这里采用了雪花算法，并添加了机器码标识(666)
        key-generators:
          key-algorithm:
            # 算法类型，目前只有雪花
            type: SNOWFLAKE
            # 算法属性
            props:
              worker-id: 66
        # 定义分配算法
        sharding-algorithms:
          mod-algorithm:
            # 算法类型:取模分片算法
            type: MOD
            # 算法属性
            props:
              sharding-count: 4
        ## 对各表进行策略配置，t_bill为数据库中物理表
        tables:
          t_bill:
            # 设置表名范围
            actual-data-nodes: ds0.t_bill,ds0.t_bill$->{0..10}
            # 设置分表(片)策略，及算法所需的字段名
            table-strategy:
              standard:
                sharding-column: user_id
                sharding-algorithm-name: mod-algorithm
            # 设置主键算法，及主键字段名
            key-generate-strategy:
              column: id
              key-generator-name: key-algorithm
    mode:
      #单机模式
      type: Standalone
      repository:
        type: JDBC
    props:
      #显示sql
      sql-show: true
```

##### 1.1.2 基于 CosId 的取模分片算法

基于 me.ahoo.cosid:cosid-core 的工具类实现的取模分片算法。参考 https://github.com/apache/

shardingsphere/issues/14047 的讨论。

类型：COSID_MOD

全类名：[`org.apache.shardingsphere.sharding.cosid.algorithm.sharding.mod.CosIdModShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/features/sharding/plugin/cosid/src/main/java/org/apache/shardingsphere/sharding/cosid/algorithm/sharding/mod/CosIdModShardingAlgorithm.java)

配置属性：

| 属性名称              | 数据类型   | 说明             |
| ----------------- | ------ | -------------- |
| mod               | int    | 分片数量(分多少张表)    |
| logic-name-prefix | String | 分片数据源或真实表的前缀格式 |

示例

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
    username: root
    password: 123456
  config:
    use-legacy-processing: true
  shardingsphere:
    datasource:
      names: ds0
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root
        password: 123456
    #配置分片策略和主键策略
    rules:
      sharding:
        # 定义主键算法，key-algorithm为自定义算法名，这里采用了雪花算法，并添加了机器码标识(666)
        key-generators:
          key-algorithm:
            # 算法类型，目前只有雪花
            type: SNOWFLAKE
            # 算法属性
            props:
              worker-id: 66
        # 定义分配算法
        sharding-algorithms:
          cosid-mod-algorithm:
            # 算法类型:基于 CosId 的取模分片算法
            type: COSID_MOD
            # 算法属性
            props:
              mod: 4
              logic-name-prefix: t_bill_
        ## 对各表进行策略配置，t_bill为数据库中物理表
        tables:
          t_bill:
            # 设置表名范围ds0.t_bill20210$->{1..9},ds0.t_bill20211$->{0..2}
            actual-data-nodes: ds0.t_bill,ds0.t_bill_$->{0..10}
            # 设置分表(片)策略，及算法所需的字段名
            table-strategy:
              standard:
                sharding-column: user_id
                sharding-algorithm-name: cosid-mod-algorithm
            # 设置主键算法，及主键字段名
            key-generate-strategy:
              column: id
              key-generator-name: key-algorithm
    mode:
      #单机模式
      type: Standalone
      repository:
        type: JDBC
    props:
      #显示sql
      sql-show: true
```

##### 1.1.3 哈希取模分片算法

类型：HASH_MOD

全类名：[`org.apache.shardingsphere.sharding.algorithm.sharding.mod.HashModShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/features/sharding/core/src/main/java/org/apache/shardingsphere/sharding/algorithm/sharding/mod/HashModShardingAlgorithm.java)


配置属性：

| 属性名称           | 数据类型 | 说明          |
| -------------- | ---- | ----------- |
| sharding‐count | int  | 分片数量(分多少张表) |

示例

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
    username: root
    password: 123456
  config:
    use-legacy-processing: true
  shardingsphere:
    datasource:
      names: ds0
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root
        password: 123456
    #配置分片策略和主键策略
    rules:
      sharding:
        # 定义主键算法，key-algorithm为自定义算法名，这里采用了雪花算法，并添加了机器码标识(666)
        key-generators:
          key-algorithm:
            # 算法类型，目前只有雪花
            type: SNOWFLAKE
            # 算法属性
            props:
              worker-id: 66
        # 定义分配算法
        sharding-algorithms:
          hash_mod-algorithm:
            # 算法类型:哈希取模分片算法
            type: HASH_MOD
            # 算法属性
            props:
              sharding-count: 4
        ## 对各表进行策略配置，t_bill为数据库中物理表
        tables:
          t_bill:
            # 设置表名范围ds0.t_bill20210$->{1..9},ds0.t_bill20211$->{0..2}
            actual-data-nodes: ds0.t_bill,ds0.t_bill$->{0..10}
            # 设置分表(片)策略，及算法所需的字段名
            table-strategy:
              standard:
                sharding-column: user_id
                sharding-algorithm-name: hash_mod-algorithm
            # 设置主键算法，及主键字段名
            key-generate-strategy:
              column: id
              key-generator-name: key-algorithm
    mode:
      #单机模式
      type: Standalone
      repository:
        type: JDBC
    props:
      #显示sql
      sql-show: true
```

##### 1.1.4 分片容量的范围分片算法

类型：VOLUME_RANGE

全类名：[`org.apache.shardingsphere.sharding.algorithm.sharding.range.VolumeBasedRangeShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/features/sharding/core/src/main/java/org/apache/shardingsphere/sharding/algorithm/sharding/range/VolumeBasedRangeShardingAlgorithm.java)

配置属性：

| 属性名称            | 数据类型 | 说明   |
| --------------- | ---- | ---- |
| range‐lower     | long | 范围下界 |
| range‐upper     | long | 范围上界 |
| sharding‐volume | long | 分片容量 |

示例

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
    username: root
    password: 123456
  config:
    use-legacy-processing: true
  shardingsphere:
    datasource:
      names: ds0
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root
        password: 123456
    #配置分片策略和主键策略
    rules:
      sharding:
        # 定义主键算法，key-algorithm为自定义算法名，这里采用了雪花算法，并添加了机器码标识(666)
        key-generators:
          key-algorithm:
            # 算法类型，目前只有雪花
            type: SNOWFLAKE
            # 算法属性
            props:
              worker-id: 66
        # 定义分配算法
        sharding-algorithms:
          volume_range-algorithm:
            # 算法类型:分片容量的范围分片算法
            type: VOLUME_RANGE
            # 算法属性
            props:
              range-lower: 2
              range-upper: 120
              sharding-volume: 60
        ## 对各表进行策略配置，t_bill为数据库中物理表
        tables:
          t_bill:
            # 设置表名范围ds0.t_bill20210$->{1..9},ds0.t_bill20211$->{0..2}
            actual-data-nodes: ds0.t_bill,ds0.t_bill$->{0..10}
            # 设置分表(片)策略，及算法所需的字段名
            table-strategy:
              standard:
                sharding-column: user_id
                sharding-algorithm-name: volume_range-algorithm
            # 设置主键算法，及主键字段名
            key-generate-strategy:
              column: id
              key-generator-name: key-algorithm
    mode:
      #单机模式
      type: Standalone
      repository:
        type: JDBC
    props:
      #显示sql
      sql-show: true
```

##### 1.1.5 基于分片边界的范围分片算法

类型：BOUNDARY_RANGE

全类名：[`org.apache.shardingsphere.sharding.algorithm.sharding.range.BoundaryBasedRangeShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/features/sharding/core/src/main/java/org/apache/shardingsphere/sharding/algorithm/sharding/range/BoundaryBasedRangeShardingAlgorithm.java)

配置属性：

| 属性名称            | 数据类型   | 说明                  |
| --------------- | ------ | ------------------- |
| sharding-ranges | String | 分片的范围边界，多个范围边界以逗号分隔 |

示例

```shell
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
    username: root
    password: 123456
  config:
    use-legacy-processing: true
  shardingsphere:
    datasource:
      names: ds0
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root
        password: 123456
    #配置分片策略和主键策略
    rules:
      sharding:
        # 定义主键算法，key-algorithm为自定义算法名，这里采用了雪花算法，并添加了机器码标识(666)
        key-generators:
          key-algorithm:
            # 算法类型，目前只有雪花
            type: SNOWFLAKE
            # 算法属性
            props:
              worker-id: 66
        # 定义分配算法
        sharding-algorithms:
          boundary_range-algorithm:
            # 算法类型:基于分片边界的范围分片算法,将划分为（-∞,2）,[2,50),[50,100),[100,+∞)4个分表
            type: BOUNDARY_RANGE
            # 算法属性
            props:
              sharding-ranges: 2,50,100
        ## 对各表进行策略配置，t_bill为数据库中物理表
        tables:
          t_bill:
            # 设置表名范围ds0.t_bill20210$->{1..9},ds0.t_bill20211$->{0..2}
            actual-data-nodes: ds0.t_bill,ds0.t_bill$->{0..10}
            # 设置分表(片)策略，及算法所需的字段名
            table-strategy:
              standard:
                sharding-column: user_id
                sharding-algorithm-name: boundary_range-algorithm
            # 设置主键算法，及主键字段名
            key-generate-strategy:
              column: id
              key-generator-name: key-algorithm
    mode:
      #单机模式
      type: Standalone
      repository:
        type: JDBC
    props:
      #显示sql
      sql-show: true
```

##### 1.1.6 自动时间段分片算法

类型：AUTO_INTERVAL

全类名：[`org.apache.shardingsphere.sharding.algorithm.sharding.datetime.AutoIntervalShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/features/sharding/core/src/main/java/org/apache/shardingsphere/sharding/algorithm/sharding/datetime/AutoIntervalShardingAlgorithm.java)

配置属性：

| 属性名称             | 数据类型   | 说明                                       |
| ---------------- | ------ | ---------------------------------------- |
| datetime-lower   | String | 分片的起始时间范围，时间戳格式：yyyy‐MM‐dd HH:mm:ss      |
| datetime-upper   | String | 分片的结束时间范围，时间戳格式：yyyy‐MM‐dd HH:mm:ss      |
| sharding-seconds | long   | 单一分片所能承载的最大时间，单位：秒，允许分片键的时间戳格式的秒带有时间精度，但秒后的时间精度会被自动抹去 |

示例

```shell
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
    username: root
    password: 123456
  config:
    use-legacy-processing: true
  shardingsphere:
    datasource:
      names: ds0
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root
        password: 123456
    #配置分片策略和主键策略
    rules:
      sharding:
        # 定义主键算法，key-algorithm为自定义算法名，这里采用了雪花算法，并添加了机器码标识(666)
        key-generators:
          key-algorithm:
            # 算法类型，目前只有雪花
            type: SNOWFLAKE
            # 算法属性
            props:
              worker-id: 66
        # 定义分配算法
        sharding-algorithms:
          auto_interval-algorithm:
            # 算法类型:自动时间段分片算法,4个月一张表
            type: AUTO_INTERVAL
            # 算法属性
            props:
              # 分片开始时间 要加""不然会被解析为非字符串类型导致拿不到值
              datetime-lower: "2021-01-06 00:00:00"
              # 分片秒数 这里是一天一张表 所以秒数为86400秒  要加""不然会被解析为非字符串类型导致拿不到值86400*30*4
              sharding-seconds: "10368000"
              # 分片结束时间 要加""不然会被解析为非字符串类型导致拿不到值
              datetime-upper: "2021-12-31 23:59:59"
        ## 对各表进行策略配置，t_bill为数据库中物理表
        tables:
          t_bill:
            # 设置表名范围ds0.t_bill20210$->{1..9},ds0.t_bill20211$->{0..2}
            actual-data-nodes: ds0.t_bill,ds0.t_bill$->{0..10}
            # 设置分表(片)策略，及算法所需的字段名
            table-strategy:
              standard:
                sharding-column: create_time
                sharding-algorithm-name: auto_interval-algorithm
            # 设置主键算法，及主键字段名
            key-generate-strategy:
              column: id
              key-generator-name: key-algorithm
    mode:
      #单机模式
      type: Standalone
      repository:
        type: JDBC
    props:
      #显示sql
      sql-show: true
```

#### 1.2 标准分片算法

##### 1.2.1行表达式分片算法

类型：INLINE

全类名：[`org.apache.shardingsphere.sharding.algorithm.sharding.inline.InlineShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/features/sharding/core/src/main/java/org/apache/shardingsphere/sharding/algorithm/sharding/inline/InlineShardingAlgorithm.java)

配置属性：

| 属性名称                                   | 数据类型    | 说明                            |
| -------------------------------------- | ------- | ----------------------------- |
| algorithm-expression                   | String  | 分片算法的行表达式                     |
| allow-range-query-with-inline-sharding | boolean | 是否允许范围查询。注意：范围查询会无视分片策略，进行全路由 |

示例

```shell
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
    username: root
    password: 123456
  config:
    use-legacy-processing: true
  shardingsphere:
    datasource:
      names: ds0
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root
        password: 123456
    #配置分片策略和主键策略
    rules:
      sharding:
        # 定义主键算法，key-algorithm为自定义算法名，这里采用了雪花算法，并添加了机器码标识(666)
        key-generators:
          key-algorithm:
            # 算法类型，目前只有雪花
            type: SNOWFLAKE
            # 算法属性
            props:
              worker-id: 66
        # 定义分配算法
        inline-algorithm:
          # 算法类型
          type: INLINE
          # 算法属性
          props:
            algorithm-expression: t_bill_$->{user_id % 2 + 1}
            #允许执行范围查询操作（BETWEEN AND、>、<、>=、<=）？
            allow-range-query-with-inline-sharding: true
        ## 对各表进行策略配置，t_bill为数据库中物理表
        tables:
          t_bill:
            # 设置表名范围ds0.t_bill20210$->{1..9},ds0.t_bill20211$->{0..2}
            actual-data-nodes: ds0.t_bill,ds0.t_bill$->{0..10}
            # 设置分表(片)策略，及算法所需的字段名
            table-strategy:
              standard:
                sharding-column: id
                sharding-algorithm-name: inline-algorithm
            # 设置主键算法，及主键字段名
            key-generate-strategy:
              column: id
              key-generator-name: key-algorithm
    mode:
      #单机模式
      type: Standalone
      repository:
        type: JDBC
    props:
      #显示sql
      sql-show: true
```

##### 1.2.1时间范围分片算法

类型：INTERVAL

全类名：[`org.apache.shardingsphere.sharding.algorithm.sharding.datetime.IntervalShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/features/sharding/core/src/main/java/org/apache/shardingsphere/sharding/algorithm/sharding/datetime/IntervalShardingAlgorithm.java)

配置属性：

| 属性名称                     | 数据类型   | 说明                                       |
| ------------------------ | ------ | ---------------------------------------- |
| datetime-pattern         | String | 分片时间戳格式                                  |
| datetime-lower           | String | 分片的开始时间范围，时间戳格式：如datetime-pattern        |
| datetime-upper           | String | 分片的结束时间范围，时间戳格式：如datetime-pattern        |
| sharding-suffix-pattern  | String | 分表前缀                                     |
| datetime-interval-amount | int    | 分片键时间间隔，超过该时间间隔将进入下一分片                   |
| datetime-interval-unit   | String | 分片键时间间隔单位，必须遵循 Java ChronoUnit 的枚举值。例如：Minutes,Days,Months,Weeks等等，其他见类名ChronoUnit |

示例

```shell
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
    username: root
    password: 123456
  config:
    use-legacy-processing: true
  shardingsphere:
    datasource:
      names: ds0
      ds0:
        type: com.zaxxer.hikari.HikariDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        jdbc-url: jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root
        password: 123456
    #配置分片策略和主键策略
    rules:
      sharding:
        # 定义主键算法，key-algorithm为自定义算法名，这里采用了雪花算法，并添加了机器码标识(666)
        key-generators:
          key-algorithm:
            # 算法类型，目前只有雪花
            type: SNOWFLAKE
            # 算法属性
            props:
              worker-id: 66
        # 定义分配算法
        month-algorithm:
          # 算法类型
          type: INTERVAL
          # 算法属性
          props:
            datetime-pattern: yyyy-MM-dd HH:mm:ss
            datetime-lower: 2021-01-07 00:00:00
            datetime-upper: 2021-12-31 23:59:59
            #分表前缀
            sharding-suffix-pattern: yyyyMM
            datetime-interval-amount: 1
            #步进
            datetime-interval-unit: MONTHS
        ## 对各表进行策略配置，t_bill为数据库中物理表
        tables:
          t_bill:
            # 设置表名范围ds0.t_bill20210$->{1..9},ds0.t_bill20211$->{0..2}
            actual-data-nodes: ds0.t_bill,ds0.t_bill$->{0..10}
            # 设置分表(片)策略，及算法所需的字段名
            table-strategy:
              standard:
                sharding-column: create_time
                sharding-algorithm-name: month-algorithm
            # 设置主键算法，及主键字段名
            key-generate-strategy:
              column: id
              key-generator-name: key-algorithm
    mode:
      #单机模式
      type: Standalone
      repository:
        type: JDBC
    props:
      #显示sql
      sql-show: true
```

#### 1.3  复合分片

类型：COMPLEX_INLINE

全类名：[`org.apache.shardingsphere.sharding.algorithm.sharding.complex.ComplexInlineShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/shardingsphere-features/shardingsphere-sharding/shardingsphere-sharding-core/src/main/java/org/apache/shardingsphere/sharding/algorithm/sharding/complex/ComplexInlineShardingAlgorithm.java)

配置属性：

| 属性名称                                   | 数据类型    | 说明                            |
| -------------------------------------- | ------- | ----------------------------- |
| sharding-columns                       | String  | 分片列名称，多个列用逗号分隔。如不配置无法则不能校验    |
| algorithm-expression                   | String  | 分片算法的行表达式                     |
| allow-range-query-with-inline-sharding | boolean | 是否允许范围查询。注意：范围查询会无视分片策略，进行全路由 |

#### 1.4  自定义类分片

通过配置分片策略类型和算法类名，实现自定义扩展。 `CLASS_BASED` 允许向算法类内传入额外的自定义属性，传入的属性可以通过属性名为 `props` 的 `java.util.Properties` 类实例取出。 参考 Git 的 `org.apache.shardingsphere.example.extension.sharding.algortihm.classbased.fixture.ClassBasedStandardShardingAlgorithmFixture`

类型：CLASS_BASED

全类名：[`org.apache.shardingsphere.sharding.algorithm.sharding.classbased.ClassBasedShardingAlgorithm`](https://github.com/apache/shardingsphere/blob/master/shardingsphere-features/shardingsphere-sharding/shardingsphere-sharding-core/src/main/java/org/apache/shardingsphere/sharding/algorithm/sharding/classbased/ClassBasedShardingAlgorithm.java)

配置属性：

| 属性名称               | 数据类型   | 说明                                       |
| ------------------ | ------ | ---------------------------------------- |
| strategy           | String | 分片策略类型，支持 STANDARD、COMPLEX 或 HINT（不区分大小写） |
| algorithmClassName | String | 分片算法全限定名                                 |