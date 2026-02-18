# iData AI Platform - 项目总览

**项目版本**: v2.0 | **文档更新**: 2026-02-17

---

## 📋 项目概述

iData AI创作平台是一个完整的AI创作服务系统，采用前后端分离架构，支持1000并发用户。

**关键数据**：
- 🏗️ 6个微服务 + 1个前端 + 3个数据存储
- 📊 并发能力：1000+ 用户
- ⚡ 响应速度：<200ms
- 🔄 服务可用性：99.5%+

---

## 🚀 快速开始（选择一种方式）

### 方式1: 非Docker本地部署 ⭐ **推荐**

对于Linux服务器上的本地运行（无Docker）：

👉 **[Linux本地部署指南](deployment/Linux本地部署指南.md)** - 20分钟完整部署  
👉 **[QUICK_START.md](../QUICK_START.md)** - 5分钟快速体验  
👉 **[完整部署指南](guides/COMPLETE_GUIDE.md)** - 详细的完整步骤

**典型流程**：
```bash
bash scripts/build/build-all.sh      # 编译 (5-10分钟)
bash scripts/deploy/start-services.sh all  # 启动所有服务
```

### 方式2: Docker部署

对于使用Docker容器化的部署，参考 [Docker部署参考](deployment/部署文档.md)

---

## 📚 文档导航

**👉 查看完整文档导航**: [docs/README.md](README.md)

### 📖 部署文档
| 文档 | 用途 | 用户 |
|------|------|------|
| [QUICK_START.md](../QUICK_START.md) | 5分钟快速开始 | **所有人** |
| [部署检查清单](guides/DEPLOYMENT_CHECKLIST.md) | 6阶段检查清单验收 | 部署人员 |
| [完整部署指南](guides/COMPLETE_GUIDE.md) | 详细部署步骤 | 新手入门 |
| [Linux本地部署指南](deployment/Linux本地部署指南.md) | Linux环境部署全指南 | Linux运维 |
| [配置参考手册](deployment/配置参考手册.md) | 配置文件参数详解 | 配置管理员 |
| [scripts/README.md](../scripts/README.md) | 所有脚本的使用说明 | 自动化运维 |

### 🔧 开发文档
| 文档 | 用途 |
|------|------|
| [开发指南](development/后续开发指南.md) | 后端、前端开发计划和代码示例 |
| [系统架构](architecture/) | 系统设计、数据库设计、项目结构 |

---

## 🏗️ 技术栈

### 后端
- **框架**: Java 17 + Spring Boot 3.2.2 + MyBatis Plus 3.5.5
- **服务**: 6个微服务（认证、系统、应用、内容、支付、营销）
- **通信**: REST API + RabbitMQ 异步消息
- **缓存**: Redis 7.x
- **数据库**: MySQL 8.0

### 前端
- **框架**: Vue 3.4 + TypeScript + Vite 5
- **UI库**: Element Plus
- **构建**: npm + Vite

### 基础设施
- **Web服务**: Nginx 1.24+
- **进程管理**: systemd
- **消息队列**: RabbitMQ 3.12+
- **部署方式**: 本地运行 (非Docker)

---

## 🎯 核心功能清单

### ✅ 已实现功能

#### 认证服务 (idata-auth:8081)
- ✅ 用户注册（邮箱/手机号验证）
- ✅ 用户登录（邮箱/手机号/用户名）
- ✅ Token管理（生成、刷新、验证）
- ✅ 用户信息获取（Redis缓存）

#### 系统管理服务 (idata-system:8082)
- ✅ 用户 CRUD（分页、筛选）
- ✅ 角色管理
- ✅ 配置管理
- ✅ 操作日志

#### 应用服务 (idata-app:8083)
- ✅ 应用列表（分页、分类、搜索）
- ✅ 应用详情
- ✅ 热门推荐
- ✅ 实例创建（异步MQ处理）
- ✅ 实例管理

#### 内容服务 (idata-content:8084)
- ✅ 文章列表
- ✅ 文章详情
- ✅ 思维分析

#### 支付服务 (idata-payment:8085)
- ✅ 订单创建
- ✅ 订单查询
- ✅ 积分管理

#### 营销服务 (idata-marketing:8087)
- ✅ 热点列表
- ✅ 邀请管理
- ✅ 活动运营

#### 前端
- ✅ 布局组件（Header、Footer）
- ✅ 登录/注册页
- ✅ 首页（轮播、推荐）
- ✅ 应用列表页

### 📋 数据库表（17张）
- ✅ 用户相关（user, role, user_role）
- ✅ 应用相关（app_category, app_application, app_instance）
- ✅ 内容相关（content_article, content_thinking）
- ✅ 支付相关（payment_order, payment_membership_level, payment_credit_log, payment_coupon）
- ✅ 营销相关（marketing_campaign, marketing_invitation, marketing_trending）
- ✅ 系统相关（sys_config, sys_log）

---

## 📦 项目结构

```
idata-ai-platform/
├── backend/                    # 后端代码
│   ├── idata-common/          # 公共模块
│   ├── idata-auth/            # 认证服务
│   ├── idata-system/          # 系统服务
│   ├── idata-app/             # 应用服务
│   ├── idata-content/         # 内容服务
│   ├── idata-payment/         # 支付服务
│   ├── idata-marketing/       # 营销服务
│   └── lib/                   # 编译后JAR文件
├── frontend/                  # 前端代码
├── database/                  # 数据库脚本
│   ├── schema/                # 表结构
│   └── data/                  # 初始数据
├── scripts/                   # 部署脚本
│   ├── build/                 # 编译脚本
│   ├── deploy/                # 部署脚本
│   ├── systemd/               # systemd配置
│   └── nginx/                 # nginx配置
├── config/                    # 配置文件
├── docs/                      # 文档（按功能分类）
├── logs/                      # 日志目录
├── backup/                    # 备份目录
├── QUICK_START.md             # 快速开始
└── .env.prod                  # 生产环境变量
```

---

## 🔌 微服务架构

```
┌─────────────────────────────────────┐
│         Nginx (80/443)              │
│    前端 + API反向代理               │
└────────────┬────────────────────────┘
             │
    ┌────────┴────────┬─────────────────┬──────────────┬──────────────┐
    │                 │                 │              │              │
┌──▼──┐           ┌──▼──┐          ┌──▼──┐       ┌──▼──┐        ┌──▼──┐
│8081 │           │8082 │          │8083 │       │8084 │        │8085 │
│Auth │           │Sys  │          │App  │       │Cont │        │Pay  │
└──┬──┘           └──┬──┘          └──┬──┘       └─────┘        └──┬──┘
   │                 │                │                            │
   └─────────────────┼────────────────┼────────────────┬───────────┘
                     │                │                │
              ┌──────▼────────────────▼────────────────▼──┐
              │    MySQL 8.0 / Redis 7.x / RabbitMQ    │
              └──────────────────────────────────────────┘
```

---

## 🚦 部署状态

- ✅ **环境准备脚本** - 自动化检查和安装
- ✅ **编译脚本** - build-all.sh 一键编译
- ✅ **启动脚本** - start-services.sh 一键启动全部
- ✅ **监控脚本** - status.sh 实时健康检查
- ✅ **备份脚本** - backup-database.sh 自动备份
- ✅ **systemd配置** - 开机自启管理
- ✅ **Nginx配置** - 完整的反向代理配置
- ✅ **配置模板** - 开箱即用的配置文件

---

## 💡 常见场景

### 场景1: 快速评估系统
→ **[QUICK_START.md](../QUICK_START.md)** (5分钟)

### 场景2: 部署到Linux服务器
→ **[Linux本地部署指南](deployment/Linux本地部署指南.md)** (20分钟)

### 场景3: 验证部署完整性
→ **[部署检查清单](guides/DEPLOYMENT_CHECKLIST.md)** (检查清单)

### 场景4: 了解所有配置选项
→ **[配置参考手册](deployment/配置参考手册.md)** (参考手册)

### 场景5: 添加新功能
→ **[开发指南](development/后续开发指南.md)** (开发计划)

---

## 📞 获取帮助

### 文档获取
- 📖 完整文档导航 → [docs/README.md](README.md)
- 🔧 脚本使用说明 → [scripts/README.md](../scripts/README.md)

### 问题反馈
- 🐛 部署问题 → 查看 [部署检查清单](guides/DEPLOYMENT_CHECKLIST.md)
- 🛠️ 配置问题 → 查看 [配置参考手册](deployment/配置参考手册.md)
- 💻 开发问题 → 查看 [开发指南](development/后续开发指南.md)

---

**文档版本**: v2.0  
**更新日期**: 2026-02-17  
**维护者**: iData AI Team

---

> 💡 **提示**: 查看 [docs/README.md](README.md) 了解完整的文档体系
