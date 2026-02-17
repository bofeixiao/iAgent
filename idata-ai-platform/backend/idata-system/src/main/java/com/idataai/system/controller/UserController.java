package com.idataai.system.controller;

import com.idataai.common.core.domain.PageResult;
import com.idataai.common.core.domain.Result;
import com.idataai.system.domain.dto.UserDTO;
import com.idataai.system.domain.dto.UserQueryDTO;
import com.idataai.system.domain.vo.UserVO;
import com.idataai.system.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 *
 * @author iDataAI
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/list")
    public Result<PageResult<UserVO>> list(UserQueryDTO queryDTO) {
        PageResult<UserVO> pageResult = userService.getUserList(queryDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{id}")
    public Result<UserVO> getById(@PathVariable Long id) {
        UserVO userVO = userService.getUserById(id);
        return Result.success(userVO);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return Result.success("更新成功");
    }

    /**
     * 更新用户状态
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateUserStatus(id, status);
        return Result.success("更新成功");
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success("删除成功");
    }
}
