# iData AI 平台 - 脚本使用说明

本目录包含所有部署、构建和运维脚本。

## 📁 目录结构

```
scripts/
├── deploy/          # 部署和运维脚本
├── build/           # 编译构建脚本
├── systemd/         # Systemd 服务文件
└── nginx/           # Nginx 配置文件
```

## 🚀 快速命令

### 后端服务管理

```bash
# 启动所有服务
bash scripts/deploy/start-services.sh all

# 停止所有服务
bash scripts/deploy/stop-services.sh all

# 重启所有服务
bash scripts/deploy/restart-services.sh all

# 查看服务状态
bash scripts/deploy/status.sh

# 启动单个服务
bash scripts/deploy/start-services.sh auth    # 启动认证服务
bash scripts/deploy/start-services.sh system  # 启动系统服务
```

### 编译脚本

```bash
# 编译所有模块
bash scripts/build/build-all.sh

# 只编译后端
bash scripts/build/build-backend.sh

# 只编译前端
bash scripts/build/build-frontend.sh
```

### 数据库备份

```bash
# 执行备份
bash scripts/deploy/backup-database.sh

# 备份文件位置
ls -la backup/
```

## 📜 脚本详情

### deploy/ 目录

| 脚本 | 功能 | 用法 |
|-----|------|------|
| start-services.sh | 启动服务 | `bash start-services.sh [all\|service-name]` |
| stop-services.sh | 停止服务 | `bash stop-services.sh [all\|service-name]` |
| restart-services.sh | 重启服务 | `bash restart-services.sh [all\|service-name]` |
| status.sh | 查看状态 | `bash status.sh` |
| backup-database.sh | 数据库备份 | `bash backup-database.sh` |

### build/ 目录

| 脚本 | 功能 | 用法 |
|-----|------|------|
| build-all.sh | 编译所有 | `bash build-all.sh` |
| build-backend.sh | 编译后端 | `bash build-backend.sh` |
| build-frontend.sh | 编译前端 | `bash build-frontend.sh` |

## 🔧 常见场景

### 场景1: 首次部署

```bash
# 1. 编译
bash scripts/build/build-all.sh

# 2. 启动
bash scripts/deploy/start-services.sh all

# 3. 验证
bash scripts/deploy/status.sh
```

### 场景2: 更新代码后重新部署

```bash
# 1. 停止所有服务
bash scripts/deploy/stop-services.sh all

# 2. 编译新版本
bash scripts/build/build-all.sh

# 3. 启动新版本
bash scripts/deploy/start-services.sh all

# 4. 验证
bash scripts/deploy/status.sh
```

### 场景3: 只重启某个服务

```bash
# 重启认证服务
bash scripts/deploy/restart-services.sh auth

# 重启应用服务
bash scripts/deploy/restart-services.sh app
```

### 场景4: 数据库备份

```bash
# 手动备份
bash scripts/deploy/backup-database.sh

# 查看备份文件
ls -la backup/

# 定期备份(cron)
# 编辑 crontab -e，添加:
# 0 2 * * * cd /home/xiaobofei/apps/idata-ai-platform && bash scripts/deploy/backup-database.sh
```

## 📋 脚本权限

确保所有脚本都有执行权限:

```bash
chmod +x scripts/deploy/*.sh
chmod +x scripts/build/*.sh
```
