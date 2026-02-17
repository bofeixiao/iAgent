-- ========================================
-- 示例数据插入
-- ========================================

-- 插入示例文章
INSERT INTO t_content_article (id, title, content, author_id, cover_image, category, tags, view_count, like_count, is_featured, is_published, publish_time) VALUES
(1, 'AI创作的未来趋势', '随着人工智能技术的不断发展，AI创作正在改变内容生产的方式...', 1, 'https://example.com/cover1.jpg', '技术分享', 'AI,创作,趋势', 1520, 85, 1, 1, NOW()),
(2, '如何用AI提升文案效率', '本文将介绍如何利用AI工具快速生成高质量文案...', 1, 'https://example.com/cover2.jpg', '教程', 'AI,文案,效率', 980, 62, 1, 1, NOW()),
(3, 'AI绘画入门指南', '从零开始学习AI绘画，让艺术创作变得更简单...', 1, 'https://example.com/cover3.jpg', '教程', 'AI,绘画,入门', 1250, 73, 0, 1, NOW()),
(4, '营销文案的AI化改造', '传统营销文案如何通过AI技术进行升级...', 1, 'https://example.com/cover4.jpg', '营销', 'AI,营销,文案', 890, 51, 1, 1, NOW()),
(5, 'AI视频制作工具对比', '市面上主流的AI视频制作工具深度对比分析...', 1, 'https://example.com/cover5.jpg', '工具评测', 'AI,视频,工具', 1120, 68, 0, 1, NOW());

-- 插入示例热点
INSERT INTO t_marketing_trending (id, title, source, hot_value, link, fetch_time, is_pinned) VALUES
(1, 'ChatGPT推出最新版本', 'weibo', 985000, 'https://weibo.com/example1', NOW(), 1),
(2, 'AI绘画工具Midjourney更新', 'douyin', 876000, 'https://douyin.com/example2', NOW(), 0),
(3, '国内AI大模型竞争加剧', 'wechat', 756000, 'https://wechat.com/example3', NOW(), 0),
(4, 'AI创作版权问题引发热议', 'zhihu', 652000, 'https://zhihu.com/example4', NOW(), 0),
(5, '短视频AI剪辑成新趋势', 'douyin', 598000, 'https://douyin.com/example5', NOW(), 0),
(6, 'AI写作助手市场分析', 'wechat', 543000, 'https://wechat.com/example6', NOW(), 0),
(7, 'AIGC行业报告发布', 'weibo', 487000, 'https://example.com/news7', NOW(), 0),
(8, 'AI音频生成技术突破', 'zhihu', 432000, 'https://zhihu.com/example8', NOW(), 0),
(9, '企业如何应用AI提效', 'wechat', 398000, 'https://wechat.com/example9', NOW(), 0),
(10, 'AI生成内容监管政策', 'weibo', 365000, 'https://weibo.com/example10', NOW(), 0);

-- 插入系统配置
INSERT INTO t_sys_config (id, config_key, config_value, config_type, description) VALUES
(1, 'platform.name', 'iData AI创作平台', 'string', '平台名称'),
(2, 'platform.logo', 'https://example.com/logo.png', 'string', '平台Logo'),
(3, 'register.default_credits', '5', 'number', '注册默认积分'),
(4, 'invitation.reward_credits', '10', 'number', '邀请奖励积分'),
(5, 'max_concurrent_tasks', '3', 'number', '最大并发任务数'),
(6, 'ai.api_endpoint', 'https://api.openai.com', 'string', 'AI API地址'),
(7, 'payment.alipay_enabled', 'true', 'boolean', '是否启用支付宝'),
(8, 'payment.wechat_enabled', 'true', 'boolean', '是否启用微信支付');

-- 插入营销活动示例
INSERT INTO t_marketing_campaign (id, campaign_name, campaign_type, start_time, end_time, rules, status) VALUES
(1, '新用户注册送积分', 3, '2024-01-01', '2024-12-31', '{"reward_credits": 5, "description": "新用户注册即送5积分"}', 1),
(2, '春节限时充值优惠', 1, '2024-02-01', '2024-02-15', '{"discount": 0.8, "description": "春节期间充值8折"}', 2),
(3, '会员限时优惠', 2, '2024-03-01', '2024-03-31', '{"threshold": 100, "discount": 20, "description": "充值满100元减20元"}', 1);
