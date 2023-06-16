Tdengine 时序数据库
官网文档：https://docs.taosdata.com/intro/

jdbc的配置建hutool jdbc数据源

降采样：https://zhuanlan.zhihu.com/p/433441334
聚合

查询的时间戳跟客户端的时区的一致



#创建超级表
CREATE STABLE  test.service_data_station (create_time timestamp,bid BIGINT,batch_id BIGINT,data_type INT,is_handle INT,data_content VARCHAR(12000),serial_num VARCHAR(200),version bigint,deleted int,creator VARCHAR(20),update_time  timestamp)
TAGS (station_id VARCHAR(20))

#自动创建子表
INSERT INTO test.service_data_station_HN0001  USING test.service_data_station tags('HN0001') VALUES ('2023-02-24 09:48:20.000', 110785559110225920, 202322494820349, 1, 0, '{\"ssid\":\"cqyq002\",\"stationId\":\"HN0001\",\"dataType\":\"1\",\"batchId\":\"202322494820.349\",\"data\":{\"serviceDataAcdc\":[{\"bid\":\"A111\",\"stationId\":\"B222\",\"ChargId\":\"C333\",\"CabinetId\":\"D444\",\"batchId\":\"E555\",\"ACDCId\":\"F666\",\"acdcStatus\":\"G777\",\"DCStatus\":0,\"ACRunStatus\":0,\"OutOverVoltage\":0,\"OverTempProtect\":0,\"ModuleFault\":0,\"GearState\":0,\"FanFault\":0,\"TempStatus\":0,\"TempDeraStatus\":0,\"PowLimitStatus\":0,\"ModuleStatus\":0,\"OverPowProtect\":0,\"OutVoltage\":0,\"ModAddConflict\":0,\"AveCurImbalance\":0,\"CommunicationFails\":0,\"ShortCircLimitStatus\":0,\"PFCFault\":0,\"ACInputPhase\":0,\"ACInputPhaseA\":0,\"ACInputPhaseB\":0,\"ACInputPhaseC\":0,\"BusOverVoltage\":0,\"BusVoltage\":0,\"BusVoltageUnbalan\":0,\"BusVoltageUnbalanNum\":0,\"ACInputOverload\":0,\"ACInputOverloadNum\":0,\"ACOverVoltage\":0,\"ACVoltage\":0,\"ACInputFreqFault\":0,\"ACHardWareFault\":0,\"ACVoltageUnbalan\":0,\"ACPhaseLockFailed\":0,\"ACVoltageFailsRapidly\":0,\"ACInputNormal\":0,\"OutputVoltage\":\"null\",\"OutputCurrent\":\"null\",\"version\":\"null\",\"deleted\":\"null\",\"creator\":\"null\",\"createTime\":\"null\",\"updater\":\"null\",\"updateTime\":\"null\"},{\"bid\":\"A111\",\"stationId\":\"B222\",\"ChargId\":\"C333\",\"CabinetId\":\"D444\",\"batchId\":\"E555\",\"ACDCId\":\"F666\",\"acdcStatus\":\"G777\",\"DCStatus\":0,\"ACRunStatus\":0,\"OutOverVoltage\":0,\"OverTempProtect\":0,\"ModuleFault\":0,\"GearState\":0,\"FanFault\":0,\"TempStatus\":0,\"TempDeraStatus\":0,\"PowLimitStatus\":0,\"ModuleStatus\":0,\"OverPowProtect\":0,\"OutVoltage\":0,\"ModAddConflict\":0,\"AveCurImbalance\":0,\"CommunicationFails\":0,\"ShortCircLimitStatus\":0,\"PFCFault\":0,\"ACInputPhase\":0,\"ACInputPhaseA\":0,\"ACInputPhaseB\":0,\"ACInputPhaseC\":0,\"BusOverVoltage\":0,\"BusVoltage\":0,\"BusVoltageUnbalan\":0,\"BusVoltageUnbalanNum\":0,\"ACInputOverload\":0,\"ACInputOverloadNum\":0,\"ACOverVoltage\":0,\"ACVoltage\":0,\"ACInputFreqFault\":0,\"ACHardWareFault\":0,\"ACVoltageUnbalan\":0,\"ACPhaseLockFailed\":0,\"ACVoltageFailsRapidly\":0,\"ACInputNormal\":0,\"OutputVoltage\":\"null\",\"OutputCurrent\":\"null\",\"version\":\"null\",\"deleted\":\"null\",\"creator\":\"null\",\"createTime\":\"null\",\"updater\":\"null\",\"updateTime\":\"null\"},{\"bid\":\"A111\",\"stationId\":\"B222\",\"ChargId\":\"C333\",\"CabinetId\":\"D444\",\"batchId\":\"E555\",\"ACDCId\":\"F666\",\"acdcStatus\":\"G777\",\"DCStatus\":0,\"ACRunStatus\":0,\"OutOverVoltage\":0,\"OverTempProtect\":0,\"ModuleFault\":0,\"GearState\":0,\"FanFault\":0,\"TempStatus\":0,\"TempDeraStatus\":0,\"PowLimitStatus\":0,\"ModuleStatus\":0,\"OverPowProtect\":0,\"OutVoltage\":0,\"ModAddConflict\":0,\"AveCurImbalance\":0,\"CommunicationFails\":0,\"ShortCircLimitStatus\":0,\"PFCFault\":0,\"ACInputPhase\":0,\"ACInputPhaseA\":0,\"ACInputPhaseB\":0,\"ACInputPhaseC\":0,\"BusOverVoltage\":0,\"BusVoltage\":0,\"BusVoltageUnbalan\":0,\"BusVoltageUnbalanNum\":0,\"ACInputOverload\":0,\"ACInputOverloadNum\":0,\"ACOverVoltage\":0,\"ACVoltage\":0,\"ACInputFreqFault\":0,\"ACHardWareFault\":0,\"ACVoltageUnbalan\":0,\"ACPhaseLockFailed\":0,\"ACVoltageFailsRapidly\":0,\"ACInputNormal\":0,\"OutputVoltage\":\"null\",\"OutputCurrent\":\"null\",\"version\":\"null\",\"deleted\":\"null\",\"creator\":\"null\",\"createTime\":\"null\",\"updater\":\"null\",\"updateTime\":\"null\"},{\"bid\":\"A111\",\"stationId\":\"B222\",\"ChargId\":\"C333\",\"CabinetId\":\"D444\",\"batchId\":\"E555\",\"ACDCId\":\"F666\",\"acdcStatus\":\"G777\",\"DCStatus\":0,\"ACRunStatus\":0,\"OutOverVoltage\":0,\"OverTempProtect\":0,\"ModuleFault\":0,\"GearState\":0,\"FanFault\":0,\"TempStatus\":0,\"TempDeraStatus\":0,\"PowLimitStatus\":0,\"ModuleStatus\":0,\"OverPowProtect\":0,\"OutVoltage\":0,\"ModAddConflict\":0,\"AveCurImbalance\":0,\"CommunicationFails\":0,\"ShortCircLimitStatus\":0,\"PFCFault\":0,\"ACInputPhase\":0,\"ACInputPhaseA\":0,\"ACInputPhaseB\":0,\"ACInputPhaseC\":0,\"BusOverVoltage\":0,\"BusVoltage\":0,\"BusVoltageUnbalan\":0,\"BusVoltageUnbalanNum\":0,\"ACInputOverload\":0,\"ACInputOverloadNum\":0,\"ACOverVoltage\":0,\"ACVoltage\":0,\"ACInputFreqFault\":0,\"ACHardWareFault\":0,\"ACVoltageUnbalan\":0,\"ACPhaseLockFailed\":0,\"ACVoltageFailsRapidly\":0,\"ACInputNormal\":0,\"OutputVoltage\":\"null\",\"OutputCurrent\":\"null\",\"version\":\"null\",\"deleted\":\"null\",\"creator\":\"null\",\"createTime\":\"null\",\"updater\":\"null\",\"updateTime\":\"null\"}],\"serviceDataDc\":{\"bid\":\"null\",\"stationId\":\"123456789\",\"batchId\":\"null\",\"chargId\":\"null\",\"cabinetId\":\"null\",\"dcId\":\"null\",\"dcStatus\":\"null\",\"outputVoltage\":\"0\",\"outputCurrent\":\"0\",\"effectivePowerValue\":\"0\",\"currentTotalForwardPower\":\"null\",\"currentNegativeTotaldPower\":\"null\",\"highVoltageAlarm\":0,\"lowVoltageAlarm\":0,\"highCurrentAlarm\":0,\"lowCurrentAlarm\":0,\"highPowerAlarm\":0,\"lowPowerAlarm\":0,\"version\":\"null\",\"deleted\":0,\"creator\":\"null\",\"createTime\":\"null\",\"updater\":\"null\",\"updateTime\":\"null\"},\"serviceDataBMS\":{\"bid\":\"null\",\"stationId\":\"null\",\"batchId\":\"null\",\"chargId\":\"null\",\"CabinetId\":\"null\",\"bmsId\":\"null\",\"bmsStatus\":0,\"maxAllowChargeVoltage\":\"null\",\"BMSProtocolVersion\":\"null\",\"cellsAllowChargingAtMaxVoltage\":\"null\",\"maxAllowableChargingCurrent\":\"null\",\"nominalTotalEnergyPowerBattery\":\"null\",\"maxTotalChargeVoltage\":\"null\",\"maxPermissibleTemperature\":\"null\",\"stateOfChargeSocOfVehiclePowerBattery\":\"null\",\"currentBatteryVoltageOfVehiclePowerBattery\":\"null\",\"batteryType\":0,\"batteryRatedCapacity\":\"null\",\"batteryRatedVoltage\":\"null\",\"batteryManufacture\":\"null\",\"batteryStringNo\":\"null\",\"Year\":0,\"Month\":0,\"day\":0,\"BMSChargeReady\":0,\"demandVoltage\":\"null\",\"demandCurrent\":\"null\",\"chargeMode\":0,\"chargeVoltage\":\"null\",\"chargeCurrent\":\"null\",\"maxCellVoltageNo\":\"null\",\"maxCellChargeVoltage\":\"null\",\"batteryCurrentSoc\":\"0\",\"chargeRemainTime\":\"null\",\"maxBatteryNo\":\"null\",\"maxBatteryTemperature\":\"null\",\"maxBatteryTemperatureNo\":\"null\",\"minBatteryTemperaturn\":\"null\",\"minBatteryTemperaturnNo\":\"null\",\"batteryVoltage\":0,\"batterySoc\":0,\"batteryCurrent\":0,\"batteryTemperature\":0,\"baterryInsulationStatus\":0,\"batteryConnectionStatus\":1,\"allowCharg\":0,\"isRequiredSoc\":0,\"isRequiredTotalVoltage\":0,\"isRequiredSingleVoltage\":0,\"chargerActivelyStop\":0,\"insulationFault\":0,\"connectorTemperatureFault\":0,\"bmsConnectorOverheated\":0,\"chargeConnectorFault\":0,\"batteryStringTemperatureHigh\":0,\"highVoltageFault\":0,\"otherFault\":0,\"overCurrent\":0,\"voltageAbnormal\":0,\"abortSocState\":\"null\",\"maxBatteryVoltage\":\"null\",\"minBatteryVoltage\":\"null\",\"BatteryminTemperature\":\"null\",\"BatterymaxTemperature\":\"null\",\"Crm00OverTime\":0,\"CrmAaOverTime\":0,\"ctsCmlOverTime\":0,\"croOverTime\":0,\"ccsOverTime\":0,\"ctsOverTime\":0,\"csdOverTime\":0,\"otherOverTime\":0,\"version\":\"null\",\"deleted\":0,\"creator\":\"null\",\"createTime\":\"null\",\"updater\":\"null\",\"updateTime\":\"null\"},\"serviceDataBatcharge\":{\"bid\":\"null\",\"stationId\":\"null\",\"batchId\":0,\"chargId\":\"null\",\"CabinetId\":\"null\",\"batteryChargerId\":\"null\",\"batteryChargeStatus\":\"null\",\"chargeCommunicationProtocolVersion\":\"null\",\"chargerIdentificationResults\":0,\"chargerNo\":\"null\",\"chargerAreaCode\":\"null\",\"maxOutputCurrentOfCharger\":\"null\",\"minOutputCurrentOfCharger\":\"null\",\"chargingPreparationOfCharger\":0,\"outputVoltageValue\":\"null\",\"outputCurrentValue\":\"null\",\"accumulatedChargingTime\":\"null\",\"allowCharging\":0,\"reachConditionSetChargAbort\":0,\"manualTermination\":0,\"faultAbort\":0,\"bmsActiveAbort\":0,\"chargerTemperatureFault\":0,\"chargerConnectorFailure\":0,\"internalOverTemperatureFaultOfCharger\":0,\"chargingConnectorFailure\":0,\"batteryPackTemperatureFault\":0,\"highVoltageRelayFault\":0,\"detectionPoint2VoltageFault\":0,\"otherFaults\":0,\"currentMismatch\":0,\"abnormalVoltage\":0,\"chargingTime\":\"null\",\"outputEnergy\":\"null\",\"ChargerNo2\":\"null\",\"brmTimeout\":0,\"bcpTimeout\":0,\"broTimeout\":0,\"bcsTimeout\":0,\"bclTimeout\":0,\"bstTimeout\":0,\"bsdTimeout\":0,\"deleted\":0,\"creator\":\"null\",\"createTime\":\"null\",\"updater\":\"null\",\"updateTime\":\"null\"},\"serviceDataCabinet\":{\"bid\":\"null\",\"stationId\":\"null\",\"chargId\":\"null\",\"batchId\":\"0\",\"cabinetId\":\"null\",\"cabinetStatus\":0,\"enablingPotential\":0,\"operatingMode\":0,\"stopChargingDischargingConditions\":0,\"shargingDischargingCurrent\":0,\"chargingGeneratingVoltageSettingFeedback\":\"null\",\"chargingGeneratingCurrentSettingFeedback\":\"null\",\"chargingGeneratingOutputVoltage\":\"null\",\"chargingGeneratingOutputCurrent\":\"null\",\"ISOQCC\":0,\"TOPPOQCC\":\"null\",\"TONPOQCC\":\"null\",\"batteryID\":\"null\",\"SOC\":\"null\",\"totalVoltage\":\"null\",\"batteryStatusCode\":0,\"liquidLevelOfChamber1BatteryMake_UpKettle\":0,\"RS485Status\":0,\"chargingModuleCANStatus\":0,\"fastChargingGunCANStatus\":0,\"batteryChargingCANStatusNS\":0,\"batteryChargingDataCANStatus\":0,\"chargingChamberOutputMainRelayPositive\":0,\"chargingChamberOutputMainRelayNegative\":0,\"fastChargingGunOutputMainRelayPositive\":0,\"fastChargingGunOutputMainRelayNegative\":0,\"localAbsorptionOutputMainRelayPositive\":0,\"localAbsorptionOutputMainRelayNegative\":0,\"ACContactor\":0,\"compartmentFuse\":0,\"kc0Fault\":0,\"kc1Fault\":0,\"kc1InsulationDetectionValue\":0,\"kc1ConnectorTemperature\":0,\"kc1ChargingModule1Fault\":0,\"kc1ChargingModule2Fault\":0,\"kc1ChargingModule3Fault\":0,\"kc1ChargingModule4Fault\":0,\"kc1ACContactorFault\":0,\"kc1FaultOfMainPositiveRelayOfFastChargingGun\":0,\"kc1FaultOfMainNegativeRelayOfFastChargingGun\":0,\"kc1FuseFault\":0,\"kc2Fault\":0,\"kc2InsulationDetectionValue\":0,\"kc2ConnectorTemperature\":0,\"kc2ChargingModule1Fault\":0,\"kc2ChargingModule2Fault\":0,\"kc2ChargingModule3Fault\":0,\"kc2ChargingModule4Fault\":0,\"kc3Fault\":0,\"kc3InsulationDetectionValue\":0,\"kc3ConnectorTemperature\":0,\"kc3ChargingModule1Fault\":0,\"kc3ChargingModule2Fault\":0,\"kc3ChargingModule3Fault\":0,\"kc3ChargingModule4Fault\":0,\"zn0Fault\":0,\"zn1Fault\":0,\"zn1InsulationDetectionValue\":0,\"zn1ConnectorTemperature\":0,\"zn1ChargingModule1Fault\":0,\"zn1ChargingModule2Fault\":0,\"zn1ChargingModule3Fault\":0,\"zn1ChargingModule4Fault\":0,\"zn1ACContactorFault\":0,\"zn1TheMainPositiveRelayOfTheChargingChamberIsFaulty\":0,\"zn1TheMainNegativeRelayOfTheChargingChamberIsFaulty\":0,\"zn1FuseFault\":0,\"zn2Fault\":0,\"zn2InsulationDetectionValue\":0,\"zn2ConnectorTemperature\":0,\"zn2ChargingModule1Fault\":0,\"zn2ChargingModule2Fault\":0,\"zn2ChargingModule3Fault\":0,\"zn2ChargingModule4Fault\":0,\"zn3Fault\":0,\"zn3InsulationDetectionValue\":0,\"zn3ConnectorTemperature\":0,\"zn3ChargingModule1Fault\":0,\"zn3ChargingModule2Fault\":0,\"zn3ChargingModule3Fault\":0,\"zn3ChargingModule4Fault\":0,\"jd0Fault\":0,\"jd1Fault\":0,\"jd1InsulationDetectionValue\":0,\"jd1ConnectorTemperature\":0,\"jd1TheMainPositiveRelayOfTheChargingChamberIsFaulty\":0,\"jd1TheMainNegativeRelayOfTheChargingChamberIsFaulty\":0,\"jd1FaultOfLocalMainPositiveRelay\":0,\"jd1LocalEliminationOfMainNegativeRelayFault\":0,\"jd2Fault\":0,\"jd2InsulationDetectionValue\":0,\"jd2ConnectorTemperature\":0,\"jd3Fault\":0,\"jd3InsulationDetectionValue\":0,\"jd3ConnectorTemperature\":0,\"c0Fault\":0,\"c1Fault\":0,\"c1Level1FaultInStationchargingMode\":0,\"c2fault\":0,\"c2Level1FaultOfFastChargingGunMode\":0,\"c2Level2FaultOfFastChargingGunMode\":0,\"c2Level2FaultInStationChargingMode\":0,\"c2Level1FaultOfLocalAbsorptionMode\":0,\"c2Level2FaultOfLocalAbsorptionMode\":0,\"c3fault\":0,\"c3Level3FaultOfFastChargingGunMode\":0,\"c3Level3FaultInStationChargingMode\":0,\"c3Level3FaultOfLocalAbsorptionMode\":0,\"deleted\":0,\"creator\":\"null\",\"createTime\":\"null\",\"updater\":\"null\",\"updateTime\":\"null\"}},\"starttime\":\"2023-2-24 9:48:20.348\",\"endtime\":\"2023-2-24 9:48:20.349\"}', NULL, NULL, 0, NULL, '2023-02-24 09:48:20');

druid-spring-boot-starter 连接池最高支持1.2.15
目前只支持hikari，druid,dbcp2数据源
--------配置yml----------
````
tdengine:
  datasource:
    dynamic: # 多数据源配置
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
      primary: group_tdengine
      datasource:
        group_tdengine:
          url: jdbc:TAOS-RS://192.168.0.60:6041/information_schema
          username: root
          password: taosdata
          #driver-class-name: com.taosdata.jdbc.rs.RestfulDriver
        group_tdengine2:
          url: jdbc:TAOS://127.0.0.1:6041/test?charset=UTF-8
          username: root
          password: taosdata
          #driver-class-name: com.taosdata.jdbc.rs.RestfulDriver
````
tdengine:
datasource:
dynamic: # 多数据源配置
#      druid:
#        # 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
#        initialSize: 0
#        # 最大连接池数量
#        maxActive: 8
#        # 最小连接池数量
#        minIdle: 0
#        # 获取连接时最大等待时间，单位毫秒。配置了maxWait之后， 缺省启用公平锁，并发效率会有所下降， 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
#        maxWait: 0
#        # 是否缓存preparedStatement，也就是PSCache。 PSCache对支持游标的数据库性能提升巨大，比如说oracle。 在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。作者在5.5版本中使用PSCache，通过监控界面发现PSCache有缓存命中率记录， 该应该是支持PSCache。
#        poolPreparedStatements: false
#        # 要启用PSCache，必须配置大于0，当大于0时， poolPreparedStatements自动触发修改为true。 在Druid中，不会存在Oracle下PSCache占用内存过多的问题， 可以把这个数值配置大一些，比如说100
#        maxOpenPreparedStatements: -1
#        # 用来检测连接是否有效的sql，要求是一个查询语句。 如果validationQuery为null，testOnBorrow、testOnReturn、 testWhileIdle都不会其作用。
#        validationQuery: SELECT 1
#        # 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
#        testOnBorrow: true
#        # 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
#        testOnReturn: false
#        # 建议配置为true，不影响性能，并且保证安全性。 申请连接的时候检测，如果空闲时间大于 timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
#        testWhileIdle: false
#        # 有两个含义： 1) Destroy线程会检测连接的间隔时间 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
#        timeBetweenEvictionRunsMillis: 60000
#        # 物理连接初始化的时候执行的sql
#        connectionInitSqls: SELECT 1
#        # 属性类型是字符串，通过别名的方式配置扩展插件， 常用的插件有： 监控统计用的filter:stat  日志用的filter:log4j 防御sql注入的filter:wall
#        filters: stat
#        # 类型是List<com.alibaba.druid.filter.Filter>， 如果同时配置了filters和proxyFilters， 是组合关系，并非替换关系
#        proxyFilters:
#      tomcat:
#        # (boolean) 连接池创建的连接的默认的auto-commit 状态
#        defaultAutoCommit: true
#        # (boolean) 连接池创建的连接的默认的read-only 状态。 如果没有设置则setReadOnly 方法将不会被调用。 ( 某些驱动不支持只读模式， 比如：Informix)
#        defaultReadOnly: false
#        # (int) 初始化连接： 连接池启动时创建的初始化连接数量，1。2 版本后支持
#        initialSize: 10
#        # (int) 最大活动连接： 连接池在同一时间能够分配的最大活动连接的数量， 如果设置为非正数则表示不限制
#        maxActive: 100
#        # (int) 最大空闲连接： 连接池中容许保持空闲状态的最大连接数量， 超过的空闲连接将被释放， 如果设置为负数表示不限制 如果启用，将定期检查限制连接，如果空闲时间超过minEvictableIdleTimeMillis 则释放连接 （ 参考testWhileIdle ）
#        maxIdle: 8
#        # (int) 最小空闲连接： 连接池中容许保持空闲状态的最小连接数量， 低于这个数量将创建新的连接， 如果设置为0 则不创建 如果连接验证失败将缩小这个值（ 参考testWhileIdle ）
#        minIdle: 0
#        # (int) 最大等待时间： 当没有可用连接时， 连接池等待连接被归还的最大时间( 以毫秒计数)， 超过时间则抛出异常， 如果设置为-1 表示无限等待
#        maxWait: 30000
#        # (String) SQL 查询， 用来验证从连接池取出的连接， 在将连接返回给调用者之前。 如果指定， 则查询必须是一个SQL SELECT 并且必须返回至少一行记录 查询不必返回记录，但这样将不能抛出SQL异常
#        validationQuery: SELECT 1
#        # (boolean) 指明是否在从池中取出连接前进行检验， 如果检验失败， 则从池中去除连接并尝试取出另一个。注意： 设置为true 后如果要生效，validationQuery 参数必须设置为非空字符串 参考validationInterval以获得更有效的验证
#        testOnBorrow: false
#        # (boolean) 指明是否在归还到池中前进行检验 注意： 设置为true 后如果要生效，validationQuery 参数必须设置为非空字符串
#        testOnReturn: false
#        # (boolean) 指明连接是否被空闲连接回收器( 如果有) 进行检验。 如果检测失败， 则连接将被从池中去除。注意： 设置为true 后如果要生效，validationQuery 参数必须设置为非空字符串
#        testWhileIdle: false
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
      primary: group_tdengine
      datasource:
        group_tdengine:
          url: jdbc:TAOS-RS://192.168.2.60:6041/test
          username: root
          password: taosdata
          driver-class-name: com.taosdata.jdbc.rs.RestfulDriver
#        group_tdengine2:
#          url: jdbc:TAOS://127.0.0.1:6041/test?charset=UTF-8
#          username: root
#          password: taosdata
#          #driver-class-name: com.taosdata.jdbc.rs.RestfulDriver


<!--druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.15</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.tomcat/tomcat-jdbc -->
        <dependency>
            <groupId>org.apache.tomcat</groupId>
            <artifactId>tomcat-jdbc</artifactId>
            <version>10.1.7</version>
        </dependency>
        <!--dbcp数据库连接池-->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-dbcp2</artifactId>
            <version>2.7.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
            <version>2.9.0</version>
        </dependency>