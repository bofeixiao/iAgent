package com.idataai.system.domain.dto;

import com.idataai.common.core.domain.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询DTO
 *
 * @author iDataAI
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDTO extends PageQuery {

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;

    /**
     * VIP等级
     */
    private Integer vipLevel;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
}
