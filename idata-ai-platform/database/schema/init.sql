-- ========================================
-- iData AI创作平台 - 数据库初始化脚本
-- ========================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `idata_ai` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `idata_ai`;

-- ========================================
-- 用户相关表
-- ========================================

-- 用户表
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` BIGINT NOT NULL COMMENT '用户ID',
  `username` VARCHAR(50) DEFAULT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `avatar_url` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `gender` TINYINT DEFAULT 0 COMMENT '性别:0-未知,1-男,2-女',
  `birthday` DATE DEFAULT NULL COMMENT '生日',
  `industry` VARCHAR(50) DEFAULT NULL COMMENT '所属行业',
  `status` TINYINT DEFAULT 1 COMMENT '账号状态:0-禁用,1-正常',
  `vip_level` TINYINT DEFAULT 0 COMMENT 'VIP等级:0-普通,1-银卡,2-金卡,3-铂金卡',
  `vip_expire_time` DATETIME DEFAULT NULL COMMENT 'VIP到期时间',
  `credits` INT DEFAULT 5 COMMENT '积分余额',
  `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后登录IP',
  `register_source` VARCHAR(20) DEFAULT 'web' COMMENT '注册来源:web,mobile,wechat',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记:0-未删除,1-已删除',
  `created_by` BIGINT DEFAULT NULL COMMENT '创建人ID',
  `updated_by` BIGINT DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_phone` (`phone`),
  KEY `idx_username` (`username`),
  KEY `idx_vip_level` (`vip_level`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 角色表
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` BIGINT NOT NULL COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  `status` TINYINT DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  `created_by` BIGINT DEFAULT NULL,
  `updated_by` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- 用户角色关联表
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` BIGINT NOT NULL COMMENT 'ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- ========================================
-- 应用相关表
-- ========================================

-- 应用分类表
DROP TABLE IF EXISTS `t_app_category`;
CREATE TABLE `t_app_category` (
  `id` BIGINT NOT NULL COMMENT '分类ID',
  `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父分类ID,0表示顶级',
  `icon` VARCHAR(100) DEFAULT NULL COMMENT '分类图标',
  `sort_order` INT DEFAULT 0 COMMENT '排序号',
  `status` TINYINT DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  `created_by` BIGINT DEFAULT NULL,
  `updated_by` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用分类表';

-- 应用表
DROP TABLE IF EXISTS `t_app_application`;
CREATE TABLE `t_app_application` (
  `id` BIGINT NOT NULL COMMENT '应用ID',
  `app_name` VARCHAR(100) NOT NULL COMMENT '应用名称',
  `app_icon` VARCHAR(100) DEFAULT NULL COMMENT '应用图标',
  `description` TEXT COMMENT '应用描述',
  `category_id` BIGINT DEFAULT NULL COMMENT '分类ID',
  `app_type` VARCHAR(20) DEFAULT 'text' COMMENT '应用类型:text,image,video',
  `config_schema` JSON DEFAULT NULL COMMENT '配置Schema',
  `input_fields` JSON NOT NULL COMMENT '输入字段配置',
  `output_template` TEXT DEFAULT NULL COMMENT '输出模板',
  `ai_model` VARCHAR(50) DEFAULT 'gpt-3.5' COMMENT 'AI模型',
  `credits_cost` INT DEFAULT 1 COMMENT '消耗积分数',
  `status` TINYINT DEFAULT 1 COMMENT '状态:0-下架,1-上架',
  `is_hot` TINYINT DEFAULT 0 COMMENT '是否热门:0-否,1-是',
  `use_count` INT DEFAULT 0 COMMENT '使用次数',
  `sort_order` INT DEFAULT 0 COMMENT '排序号',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  `created_by` BIGINT DEFAULT NULL,
  `updated_by` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_app_type` (`app_type`),
  KEY `idx_is_hot` (`is_hot`),
  KEY `idx_use_count` (`use_count`),
  KEY `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用模板表';

-- 实例表
DROP TABLE IF EXISTS `t_app_instance`;
CREATE TABLE `t_app_instance` (
  `id` BIGINT NOT NULL COMMENT '实例ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `app_id` BIGINT NOT NULL COMMENT '应用ID',
  `title` VARCHAR(200) DEFAULT NULL COMMENT '作品标题',
  `input_params` JSON NOT NULL COMMENT '输入参数',
  `output_result` TEXT DEFAULT NULL COMMENT '输出结果',
  `output_files` JSON DEFAULT NULL COMMENT '输出文件列表',
  `status` TINYINT DEFAULT 0 COMMENT '状态:0-处理中,1-成功,2-失败',
  `error_message` TEXT DEFAULT NULL COMMENT '错误信息',
  `credits_cost` INT DEFAULT 0 COMMENT '实际消耗积分',
  `processing_time` INT DEFAULT 0 COMMENT '处理耗时(秒)',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  `created_by` BIGINT DEFAULT NULL,
  `updated_by` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_app_id` (`app_id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用实例表(用户作品)';

-- ========================================
-- 支付相关表
-- ========================================

-- 会员等级表
DROP TABLE IF EXISTS `t_payment_membership_level`;
CREATE TABLE `t_payment_membership_level` (
  `id` BIGINT NOT NULL COMMENT '等级ID',
  `level_code` VARCHAR(20) NOT NULL COMMENT '等级编码',
  `level_name` VARCHAR(50) NOT NULL COMMENT '等级名称',
  `level_value` TINYINT NOT NULL COMMENT '等级值',
  `price` DECIMAL(10,2) NOT NULL COMMENT '价格(元)',
  `duration_days` INT NOT NULL COMMENT '有效期(天)',
  `credits_gift` INT DEFAULT 0 COMMENT '赠送积分',
  `benefits` JSON DEFAULT NULL COMMENT '权益描述',
  `sort_order` INT DEFAULT 0 COMMENT '排序号',
  `status` TINYINT DEFAULT 1 COMMENT '状态:0-下架,1-上架',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  `created_by` BIGINT DEFAULT NULL,
  `updated_by` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_level_code` (`level_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会员等级表';

-- ========================================
-- 初始化数据
-- ========================================

-- 插入默认角色
INSERT INTO `t_role` (`id`, `role_name`, `role_code`, `description`, `status`) VALUES
(1, '超级管理员', 'SUPER_ADMIN', '拥有所有权限', 1),
(2, '管理员', 'ADMIN', '管理员权限', 1),
(3, '普通用户', 'USER', '普通用户权限', 1);

-- 插入会员等级
INSERT INTO `t_payment_membership_level` (`id`, `level_code`, `level_name`, `level_value`, `price`, `duration_days`, `credits_gift`, `benefits`, `sort_order`, `status`) VALUES
(1, 'BASIC', '普通用户', 0, 0.00, 0, 5, '{"features": ["基础AI应用", "5积分/注册"]}', 0, 1),
(2, 'SILVER', '银卡会员', 1, 99.00, 30, 100, '{"features": ["全部AI应用", "100积分月卡", "优先客服"]}', 1, 1),
(3, 'GOLD', '金卡会员', 2, 499.00, 180, 600, '{"features": ["全部AI应用", "600积分半年卡", "优先客服", "专属应用"]}', 2, 1),
(4, 'PLATINUM', '铂金会员', 3, 999.00, 365, 1500, '{"features": ["全部AI应用", "1500积分年卡", "VIP客服", "专属应用", "高级功能"]}', 3, 1);

-- 插入应用分类
INSERT INTO `t_app_category` (`id`, `category_name`, `parent_id`, `icon`, `sort_order`, `status`) VALUES
(1, '文案创作', 0, '📝', 1, 1),
(2, '图片生成', 0, '🎨', 2, 1),
(3, '视频制作', 0, '🎬', 3, 1),
(4, '营销推广', 0, '📢', 4, 1);

-- 插入示例应用
INSERT INTO `t_app_application` (`id`, `app_name`, `app_icon`, `description`, `category_id`, `app_type`, `input_fields`, `credits_cost`, `status`, `is_hot`, `sort_order`) VALUES
(1, '奇幻照相馆', '🎨', '将照片转换为15种不同风格的奇幻作品', 2, 'image', 
 '{"fields": [{"name": "photo", "type": "image", "label": "上传照片"}, {"name": "style", "type": "select", "label": "选择风格", "options": ["哈利波特", "加勒比海盗", "甄嬛传"]}]}',
 3, 1, 1, 1);
