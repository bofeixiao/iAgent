package com.idataai.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * ID生成器工具类
 * 使用雪花算法生成分布式唯一ID
 *
 * @author iDataAI
 */
public class IdGeneratorUtil {

    /**
     * 工作机器ID (0-31)
     */
    private static final long WORKER_ID = 1L;

    /**
     * 数据中心ID (0-31)
     */
    private static final long DATA_CENTER_ID = 1L;

    /**
     * 雪花算法实例
     */
    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(WORKER_ID, DATA_CENTER_ID);

    /**
     * 生成Long类型ID
     *
     * @return ID
     */
    public static Long nextId() {
        return SNOWFLAKE.nextId();
    }

    /**
     * 生成String类型ID
     *
     * @return ID
     */
    public static String nextIdStr() {
        return SNOWFLAKE.nextIdStr();
    }
}
