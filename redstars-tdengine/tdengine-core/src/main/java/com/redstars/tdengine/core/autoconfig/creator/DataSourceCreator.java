/*
 * Copyright © 2018 organization baomidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redstars.tdengine.core.autoconfig.creator;


import com.redstars.tdengine.core.autoconfig.HutoolDynamicDataSourceProperties;

/**
 * 默认按照以下顺序创建数据源:
 * <pre>
 * 	JNDI(1000) &gt; DRUID(2000) &gt; HIKARI(3000) &gt; BASIC(5000)
 * </pre>
 *
 * @author ls9527
 */
public interface DataSourceCreator {

    /**
     * 通过属性创建数据源
     *
     * @param dataSourceProperty 数据源属性
     * @return 被创建的数据源
     */
    void createDataSource(HutoolDynamicDataSourceProperties dataSourceProperty);

}
