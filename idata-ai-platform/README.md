# iData AI Platform 创作平台

> 企业级AI内容创作系统 | Java 17 + Spring Boot 3.2 + Vue 3.4

---

## 🎯 快速导航

### 🚀 我是新手，想快速开始
**→ [QUICK_START.md](QUICK_START.md)** (5分钟内启动系统)

### 📖 我想部署到生产环境
**→ [部署指南](docs/deployment/README.md)** 
- 推荐: [Linux本地部署](docs/deployment/Linux本地部署指南.md) (20分钟)
- 参考: [Docker部署](docs/deployment/部署文档.md)

### 💻 我想开发新功能
**→ [开发指南](docs/development/后续开发指南.md)** + [系统架构](docs/architecture/)

### 📚 我想了解项目详情
**→ [项目总览](docs/PROJECT_OVERVIEW.md)** | [完整文档中心](docs/README.md)

### 🔍 我需要查找特定文档
**→ [文档导览](docs/navigation/文档导览图.md)** | [可视化图表](docs/navigation/文档导览图-可视化.md)

---

## 📚 文档中心

**所有文档已整理到 `docs/` 目录，按功能分类：**

```
docs/
├─ README.md              📖 文档中心首页(从这里开始!)
├─ PROJECT_OVERVIEW.md    📌 项目总览
├─ guides/                ⚡ 快速指南
├─ deployment/            🚀 部署文档
├─ development/           💻 开发指南
├─ architecture/          🏗️ 架构设计
└─ navigation/            📍 文档导览
```

**→ [进入文档中心](docs/README.md)**

---

## 🏗️ 项目特点

### 微服务架构
- ✅ 6个独立微服务 (认证、系统、应用、内容、支付、营销)
- ✅ 异步消息处理 (RabbitMQ)
- ✅ 缓存加速 (Redis)
- ✅ 完整的API体系

### 技术栈
- **后端**: Java 17 + Spring Boot 3.2.2 + MyBatis Plus
- **前端**: Vue 3.4 + TypeScript + Vite 5
- **数据**: MySQL 8.0 + Redis 7.x
- **消息**: RabbitMQ 3.12
- **部署**: Nginx + Systemd (非Docker方案推荐)

### 开发特性
- 📊 完整的数据库设计 (17张表)
- 🔐 JWT认证 + Redis缓存
- 🎨 Element Plus UI组件库
- 🔄 自动化部署脚本
- ✅ 6个主要服务已完成
- 📝 详细的架构和开发文档

---

## 🚀 零依赖快速开始 (5分钟)

```bash
# 1. 快速部署
bash scripts/build/build-all.sh   # 编译
bash scripts/deploy/start-services.sh all  # 启动

# 2. 访问系统
http://localhost   # 前端
```

→ **[详细快速开始指南](QUICK_START.md)**

---

## 📦 项目结构

```
idata-ai-platform/
├── backend/              # Java后端代码 (6个微服务)
├── frontend/             # Vue 3.4前端代码
├── database/             # MySQL数据库脚本
├── scripts/              # 部署和构建脚本
├── config/               # 配置文件模板
├── docs/                 # 📖 完整文档 (按功能分类)
├── QUICK_START.md        # ⚡ 5分钟快速开始
└── README.md            # 本文件
```

---

## 💡 推荐流程

### 👨‍💻 对于新手
```
QUICK_START.md (5分钟)
    ↓
docs/PROJECT_OVERVIEW.md (5分钟)
    ↓
docs/deployment/Linux本地部署指南.md (20分钟)
    ↓
✅ 系统启动!
```

### 🛠️ 对于运维
```
docs/deployment/Linux本地部署指南.md (20分钟)
    ↓
docs/deployment/配置参考手册.md (10分钟)
    ↓
docs/guides/DEPLOYMENT_CHECKLIST.md (10分钟)
    ↓
✅ 部署完成!
```

### 👨‍💼 对于开发
```
docs/PROJECT_OVERVIEW.md (5分钟)
    ↓
docs/architecture/02-技术架构设计.md (20分钟)
    ↓
docs/development/后续开发指南.md (20分钟)
    ↓
✅ 开发准备完成!
```

---

## 🔗 关键文档链接

### ⚡ 快速开始
- [QUICK_START.md](QUICK_START.md) - 5分钟快速部署
- [项目总览](docs/PROJECT_OVERVIEW.md) - 了解项目

### 🚀 部署文档
- [部署导航](docs/deployment/README.md) - 选择部署方案
- [Linux本地部署](docs/deployment/Linux本地部署指南.md) ⭐ 推荐
- [配置参考](docs/deployment/配置参考手册.md) - 参数详解
- [部署检查清单](docs/guides/DEPLOYMENT_CHECKLIST.md) - 验收检查

### 💻 开发文档
- [开发指南](docs/development/后续开发指南.md) - 功能开发计划
- [技术架构](docs/architecture/02-技术架构设计.md) - 系统设计
- [数据库设计](docs/architecture/03-数据库设计.md) - DB结构

### 📚 完整文档
- [文档中心](docs/README.md) - 所有文档导航首页
- [文档导览图](docs/navigation/文档导览图.md) - 文本版导览
- [可视化图表](docs/navigation/文档导览图-可视化.md) - Mermaid流程图

---

## 📌 重要提示

✅ **推荐的部署方案**: 
- Linux本地部署 (性能最优，脚本完整) → [查看](docs/deployment/Linux本地部署指南.md)
- Docker部署作为参考 → [查看](docs/deployment/部署文档.md)

📖 **不知道从哪开始?**
- 快速开始: [QUICK_START.md](QUICK_START.md)
- 完整导航: [docs/README.md](docs/README.md)
- 文档导览: [docs/navigation/文档导览图.md](docs/navigation/文档导览图.md)

🔄 **所有文档已整理**
- 根目录: 项目首页和快速开始
- `docs/`: 所有详细文档按分类

---

## 🎯 核心功能

| 服务 | 端口 | 功能 | 状态 |
|------|------|------|------|
| 认证服务 | 8081 | 用户登注认证 | ✅ |
| 系统管理 | 8082 | 用户角色管理 | ✅ |
| 应用服务 | 8083 | AI应用模板 | ✅ |
| 内容服务 | 8084 | 文章思维管理 | ✅ |
| 支付服务 | 8085 | 订单积分 | ✅ |
| 营销服务 | 8087 | 推荐邀请 | ✅ |
| 前端 | 80 | Web用户界面 | ✅ |

---

## 📞 获取帮助

- 📖 **查看文档**: [docs/README.md](docs/README.md)
- 🤔 **查找教程**: [文档导览图](docs/navigation/文档导览图.md)
- 🐛 **部署问题**: [部署检查清单](docs/guides/DEPLOYMENT_CHECKLIST.md)
- ⚙️ **配置问题**: [配置参考手册](docs/deployment/配置参考手册.md)

---

## 📚 更多信息

- **项目版本**: v2.0
- **最后更新**: 2026-02-17
- **文档位置**: [docs/](docs/)
- **快速开始**: [QUICK_START.md](QUICK_START.md)

---

**立即开始**➜ [快速开始指南](QUICK_START.md) ⏱️ 只需5分钟!
