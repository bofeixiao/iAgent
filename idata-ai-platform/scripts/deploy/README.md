# 部署脚本详细说明

本目录包含所有后端服务的启动、停止、状态检查和数据库备份脚本。

## 📜 脚本列表

### start-services.sh - 启动服务

**功能**: 启动一个或多个后端服务

**用法**:
```bash
# 启动所有6个服务
bash start-services.sh all

# 启动单个服务
bash start-services.sh auth      # 启动认证服务(8081)
bash start-services.sh system    # 启动系统服务(8082)
bash start-services.sh app       # 启动应用服务(8083)
bash start-services.sh content   # 启动内容服务(8084)
bash start-services.sh payment   # 启动支付服务(8085)
bash start-services.sh marketing # 启动营销服务(8087)
```

### stop-services.sh - 停止服务

**功能**: 停止一个或多个后端服务

**用法**:
```bash
# 停止所有服务
bash stop-services.sh all

# 停止单个服务
bash stop-services.sh auth
```

### restart-services.sh - 重启服务

**功能**: 重启一个或多个后端服务(先停止再启动)

**用法**:
```bash
# 重启所有服务
bash restart-services.sh all

# 重启单个服务
bash restart-services.sh auth
```

### status.sh - 检查状态

**功能**: 检查所有后端服务和中间件的运行状态

**用法**:
```bash
bash status.sh
```

### backup-database.sh - 数据库备份

**功能**: 备份 MySQL 数据库，保留最近7天的备份

**用法**:
```bash
# 执行备份
bash backup-database.sh
```
