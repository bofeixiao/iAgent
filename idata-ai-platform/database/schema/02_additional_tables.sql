-- ========================================
-- 内容相关表
-- ========================================

-- 文章表
CREATE TABLE IF NOT EXISTS t_content_article (
    id BIGINT PRIMARY KEY COMMENT '文章ID',
    title VARCHAR(200) NOT NULL COMMENT '文章标题',
    content TEXT NOT NULL COMMENT '文章内容',
    author_id BIGINT NOT NULL COMMENT '作者ID',
    cover_image VARCHAR(500) COMMENT '封面图片',
    category VARCHAR(50) COMMENT '分类',
    tags VARCHAR(200) COMMENT '标签（逗号分隔）',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    like_count INT DEFAULT 0 COMMENT '点赞次数',
    is_featured TINYINT(1) DEFAULT 0 COMMENT '是否精选',
    is_published TINYINT(1) DEFAULT 0 COMMENT '是否发布',
    publish_time DATETIME COMMENT '发布时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    created_by BIGINT COMMENT '创建人',
    updated_by BIGINT COMMENT '更新人',
    INDEX idx_author (author_id),
    INDEX idx_category (category),
    INDEX idx_featured (is_featured),
    INDEX idx_publish_time (publish_time),
    FULLTEXT idx_fulltext (title, content)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';

-- 思维分析表
CREATE TABLE IF NOT EXISTS t_content_thinking (
    id BIGINT PRIMARY KEY COMMENT '思维分析ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    industry VARCHAR(50) COMMENT '行业',
    questionnaire_answers JSON COMMENT '问卷答案',
    writing_style VARCHAR(50) COMMENT '写作风格',
    resources JSON COMMENT '资源列表',
    status TINYINT DEFAULT 0 COMMENT '状态：0-未分析，1-分析中，2-已完成',
    analyzed_time DATETIME COMMENT '分析时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    created_by BIGINT COMMENT '创建人',
    updated_by BIGINT COMMENT '更新人',
    UNIQUE KEY uk_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='思维分析表';

-- ========================================
-- 支付相关表
-- ========================================

-- 订单表
CREATE TABLE IF NOT EXISTS t_payment_order (
    id BIGINT PRIMARY KEY COMMENT '订单ID',
    order_no VARCHAR(32) NOT NULL COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    order_type TINYINT NOT NULL COMMENT '订单类型：1-积分充值，2-会员购买',
    product_id BIGINT COMMENT '产品ID',
    amount DECIMAL(10,2) NOT NULL COMMENT '订单金额',
    payment_method TINYINT COMMENT '支付方式：1-支付宝，2-微信，3-余额',
    status TINYINT DEFAULT 0 COMMENT '订单状态：0-待支付，1-已支付，2-已关闭，3-已退款',
    payment_time DATETIME COMMENT '支付时间',
    third_party_order_no VARCHAR(64) COMMENT '第三方订单号',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    created_by BIGINT COMMENT '创建人',
    updated_by BIGINT COMMENT '更新人',
    UNIQUE KEY uk_order_no (order_no),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 积分流水表
CREATE TABLE IF NOT EXISTS t_payment_credit_log (
    id BIGINT PRIMARY KEY COMMENT '流水ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    change_type TINYINT NOT NULL COMMENT '变更类型：1-充值，2-消费，3-退款，4-赠送，5-注册奖励',
    change_amount INT NOT NULL COMMENT '变更金额（正数为增加，负数为减少）',
    balance_before INT NOT NULL COMMENT '变更前余额',
    balance_after INT NOT NULL COMMENT '变更后余额',
    related_id BIGINT COMMENT '关联ID（订单ID或实例ID）',
    related_type VARCHAR(20) COMMENT '关联类型',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    created_by BIGINT COMMENT '创建人',
    updated_by BIGINT COMMENT '更新人',
    INDEX idx_user_id (user_id),
    INDEX idx_change_type (change_type),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='积分流水表';

-- 优惠券表
CREATE TABLE IF NOT EXISTS t_payment_coupon (
    id BIGINT PRIMARY KEY COMMENT '优惠券ID',
    coupon_code VARCHAR(32) NOT NULL COMMENT '优惠券码',
    discount_value DECIMAL(10,2) NOT NULL COMMENT '折扣金额',
    valid_from DATETIME NOT NULL COMMENT '有效期开始',
    valid_until DATETIME NOT NULL COMMENT '有效期结束',
    total_quantity INT NOT NULL COMMENT '总数量',
    used_quantity INT DEFAULT 0 COMMENT '已使用数量',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    created_by BIGINT COMMENT '创建人',
    updated_by BIGINT COMMENT '更新人',
    UNIQUE KEY uk_coupon_code (coupon_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券表';

-- ========================================
-- 营销相关表
-- ========================================

-- 营销活动表
CREATE TABLE IF NOT EXISTS t_marketing_campaign (
    id BIGINT PRIMARY KEY COMMENT '活动ID',
    campaign_name VARCHAR(100) NOT NULL COMMENT '活动名称',
    campaign_type TINYINT NOT NULL COMMENT '活动类型：1-限时折扣，2-满减优惠，3-积分奖励',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    rules JSON COMMENT '活动规则',
    status TINYINT DEFAULT 1 COMMENT '状态：0-未开始，1-进行中，2-已结束',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    created_by BIGINT COMMENT '创建人',
    updated_by BIGINT COMMENT '更新人',
    INDEX idx_status (status),
    INDEX idx_time_range (start_time, end_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='营销活动表';

-- 邀请记录表
CREATE TABLE IF NOT EXISTS t_marketing_invitation (
    id BIGINT PRIMARY KEY COMMENT '邀请记录ID',
    inviter_id BIGINT NOT NULL COMMENT '邀请人ID',
    invitee_id BIGINT COMMENT '被邀请人ID',
    invitation_code VARCHAR(20) NOT NULL COMMENT '邀请码',
    reward_credits INT DEFAULT 0 COMMENT '奖励积分',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待完成，1-已完成',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    created_by BIGINT COMMENT '创建人',
    updated_by BIGINT COMMENT '更新人',
    INDEX idx_inviter (inviter_id),
    INDEX idx_invitation_code (invitation_code),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邀请记录表';

-- 热点表
CREATE TABLE IF NOT EXISTS t_marketing_trending (
    id BIGINT PRIMARY KEY COMMENT '热点ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    source VARCHAR(50) NOT NULL COMMENT '来源：douyin, weibo, wechat, zhihu',
    hot_value INT DEFAULT 0 COMMENT '热度值',
    link VARCHAR(500) COMMENT '链接',
    fetch_time DATETIME NOT NULL COMMENT '抓取时间',
    is_pinned TINYINT(1) DEFAULT 0 COMMENT '是否置顶',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否删除',
    INDEX idx_source (source),
    INDEX idx_hot_value (hot_value),
    INDEX idx_fetch_time (fetch_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='热点表';

-- ========================================
-- 系统相关表
-- ========================================

-- 系统配置表
CREATE TABLE IF NOT EXISTS t_sys_config (
    id BIGINT PRIMARY KEY COMMENT '配置ID',
    config_key VARCHAR(100) NOT NULL COMMENT '配置键',
    config_value TEXT NOT NULL COMMENT '配置值',
    config_type VARCHAR(20) NOT NULL COMMENT '配置类型：string, number, boolean, json',
    description VARCHAR(200) COMMENT '描述',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- 系统日志表
CREATE TABLE IF NOT EXISTS t_sys_log (
    id BIGINT PRIMARY KEY COMMENT '日志ID',
    user_id BIGINT COMMENT '用户ID',
    operation VARCHAR(100) NOT NULL COMMENT '操作',
    params TEXT COMMENT '参数',
    ip VARCHAR(50) COMMENT 'IP地址',
    execute_time INT DEFAULT 0 COMMENT '执行时间(ms)',
    status TINYINT DEFAULT 1 COMMENT '状态：0-失败，1-成功',
    error_msg TEXT COMMENT '错误信息',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统日志表';
