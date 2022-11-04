sharejdbc分表分库v5.2.0

###1.内置分片算法
背景信息
ShardingSphere 内置提供了多种分片算法，按照类型可以划分为自动分片算法、标准分片算法、复合分
片算法和 Hint 分片算法，能够满足用户绝大多数业务场景的需要。此外，考虑到业务场景的复杂性，内
置算法也提供了自定义分片算法的方式，用户可以通过编写 Java 代码来完成复杂的分片逻辑。

注释：所有内置分片算法生成的分表名必须在设置的actual-data-nodes（表名范围）内
####1.1自动分片
#####1.1.1 取模分片算法
类型：MOD
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

配置属性：

| 属性名称            | 数据类型 | 说明   |
| --------------- | ---- | ---- |
| range‐lower     | long | 范围下界 |
| range‐upper     | long | 范围上界 |
| sharding‐volume | long | 分片容量 |

示例