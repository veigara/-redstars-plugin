package com.redstars.tdengine.core.autoconfig;

import cn.hutool.setting.Setting;

/**
 * @author : zhouhx
 * @date : 2023/6/9 15:45
 */
public interface InitSetting {
    /**
     *
     * 将配置文件转换成Setting文件
     * @author zhuohx
     * @param
     * @return cn.hutool.setting.Setting
     * @throws
     * @version 1.0
     * @since  2023/6/9 15:46
     */
    Setting covertSetting();
}
