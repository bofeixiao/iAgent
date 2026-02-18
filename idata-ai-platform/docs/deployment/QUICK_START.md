# iData AI 平台 - 快速开始指南

⏱️ **预计耗时**: 5 分钟阅读 + 15 分钟部署

## 🎯 快速开始流程

### 前置条件
- 已安装: JDK 17、Maven 3.8+、Node.js 18+
- 已安装: MySQL 8.0、Redis 7.x、RabbitMQ 3.12
- 已启动: MySQL、Redis、RabbitMQ 服务
- 已创建数据库和用户(见下面步骤1)

### 步骤1: 初始化数据库(首次部署)

```bash
cd /home/xiaobofei/apps/idata-ai-platform

# 创建数据库和用户
mysql -u root -p << EOF
CREATE DATABASE idata_ai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'idata'@'localhost' IDENTIFIED BY 'Idata@123456';
GRANT ALL PRIVILEGES ON idata_ai.* TO 'idata'@'localhost';
FLUSH PRIVILEGES;
EXIT;
EOF

# 导入初始化脚本
mysql -u idata -pIdata@123456 idata_ai < database/schema/init.sql
mysql -u idata -pIdata@123456 idata_ai < database/schema/02_additional_tables.sql
```

### 步骤2: 编译项目

```bash
cd /home/xiaobofei/apps/idata-ai-platform

# 一键编译所有模块
bash scripts/build/build-all.sh

# 耗时 5-10 分钟，成功时末尾显示: ✨ 所有模块编译完成！
```

### 步骤3: 配置服务

```bash
# 复制配置模板到各服务
for service in idata-auth idata-system idata-app idata-content idata-payment idata-marketing; do
    cp config/application-prod.yml backend/$service/src/main/resources/application.yml
done

# 编辑每个服务的配置文件，确保以下信息正确:
# - spring.datasource 数据库连接
# - spring.redis 缓存连接
# - spring.rabbitmq 消息队列连接
# - jwt.secret JWT密钥(必须修改!)
```

### 步骤4: 启动后端服务

```bash
# 启动所有6个后端服务
bash scripts/deploy/start-services.sh all

# 等待 5-10 秒，所有服务启动成功
# 输出类似: ✅ idata-auth 启动成功 (PID: xxxx, Port: 8081)
```

### 步骤5: 验证后端

```bash
# 查看所有服务状态
bash scripts/deploy/status.sh

# 应该看到所有6个服务都是 ✅ 运行中
# 和所有中间件都是 ✅ 正常
```

### 步骤6: 构建前端

```bash
cd frontend

# 编译前端
npm install
npm run build

# 成功时生成 dist/ 目录
```

### 步骤7: 部署前端和Nginx

```bash
# 配置Nginx
sudo cp scripts/nginx/idata-ai.conf /etc/nginx/conf.d/

# 验证Nginx配置
sudo nginx -t

# 重新加载Nginx
sudo systemctl reload nginx

# 访问应用
# 前端: http://localhost
# RabbitMQ管理: http://localhost:15672
```

### 步骤8: 测试应用

```bash
# 在浏览器中打开
# http://localhost

# 或用curl测试API
curl http://localhost/api/auth/login

# 应该返回 200 或预期的错误响应(如缺少参数)
```

---

## ✅ 完成！

恭喜！您已成功部署 iData AI 平台！

## 📚 后续查看

- **需要配置帮助?** → 查看 `config/README.md`
- **需要详细步骤?** → 查看 `docs/deployment/Linux本地部署指南.md`
- **需要检查清单?** → 查看 `DEPLOYMENT_CHECKLIST.md`
- **脚本出问题?** → 查看 `scripts/deploy/README.md`

## 🔧 常见命令

```bash
cd /home/xiaobofei/apps/idata-ai-platform

# 查看状态
bash scripts/deploy/status.sh

# 查看日志
tail -f logs/auth.log

# 停止所有服务
bash scripts/deploy/stop-services.sh all

# 重启所有服务
bash scripts/deploy/restart-services.sh all

# 备份数据库
bash scripts/deploy/backup-database.sh
```

---

**文档版本**: v2.0  
**更新日期**: 2026-02-17  
**部署方式**: Linux 本地部署(非Docker)  
**部署路径**: /home/xiaobofei/apps/idata-ai-platform
