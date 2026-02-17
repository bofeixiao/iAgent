package com.idataai.system.service;

import com.idataai.common.core.domain.PageResult;
import com.idataai.system.domain.dto.UserDTO;
import com.idataai.system.domain.dto.UserQueryDTO;
import com.idataai.system.domain.vo.UserVO;

/**
 * 用户服务接口
 *
 * @author iDataAI
 */
public interface IUserService {

    /**
     * 分页查询用户列表
     *
     * @param queryDTO 查询条件
     * @return 用户列表
     */
    PageResult<UserVO> getUserList(UserQueryDTO queryDTO);

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    UserVO getUserById(Long id);

    /**
     * 更新用户信息
     *
     * @param id      用户ID
     * @param userDTO 用户信息
     */
    void updateUser(Long id, UserDTO userDTO);

    /**
     * 更新用户状态
     *
     * @param id     用户ID
     * @param status 状态
     */
    void updateUserStatus(Long id, Integer status);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);
}
