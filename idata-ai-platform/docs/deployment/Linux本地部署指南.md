# Linux 本地部署指南

**部署环境**: Linux (CentOS/Ubuntu)  
**部署方式**: 本地运行(无Docker)  
**部署目录**: `/home/xiaobofei/apps`

---

## 📋 部署目录规划

```
/home/xiaobofei/apps/idata-ai-platform/
├── backend/                    # 后端代码
├── frontend/                   # 前端代码
├── database/                   # 数据库脚本
├── scripts/                    # 所有脚本
│   ├── deploy/                # 部署脚本
│   ├── build/                 # 编译脚本
│   ├── systemd/               # systemd服务
│   └── nginx/                 # nginx配置
├── config/                    # 配置文件
├── docs/                      # 文档
├── logs/                      # 日志目录
├── backup/                    # 备份目录
└── .env.prod                  # 生产环境变量
```

---

## 1️⃣ 前置环境准备

### 1.1 成功的部署指示

```bash
# 系统要求
uname -a              # Linux系统
cat /etc/os-release   # Ubuntu 20.04+ 或 CentOS 7+

# 用户准备  
id xiaobofei          # 用户存在
ls -ld /home/xiaobofei/apps  # 目录存在
```

### 1.2 软件环装检查

```bash
# Java 17
java -version         # openjdk 17.x.x

# Maven
mvn -version          # Apache Maven 3.8+

# Node.js
node -v               # v18.x.x+
npm -v                # 9.x.x+

# MySQL
mysql --version       # mysql  Ver 8.0+

# Redis
redis-server --version  # Redis server v=7.x.x

# RabbitMQ
rabbitmqctl version   # RabbitMQ 3.12+
```

---

## 2️⃣ 中间件部署

### 2.1 MySQL 初始化

```bash
# 创建数据库
mysql -u root -p << 'EOF'
CREATE DATABASE idata_ai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'idata'@'localhost' IDENTIFIED BY 'Idata@123456';
GRANT ALL PRIVILEGES ON idata_ai.* TO 'idata'@'localhost';
FLUSH PRIVILEGES;
EOF

# 导入脚本
mysql -u idata -pIdata@123456 idata_ai < database/schema/init.sql
mysql -u idata -pIdata@123456 idata_ai < database/schema/02_additional_tables.sql

# 验证
mysql -u idata -pIdata@123456 idata_ai -e "SHOW TABLES;"
```

### 2.2 Redis 配置

```bash
# 启动和启用
sudo systemctl start redis-server
sudo systemctl enable redis-server

# 验证
redis-cli ping        # 应返回 PONG
```

### 2.3 RabbitMQ 配置

```bash
# 启动和启用
sudo systemctl start rabbitmq-server
sudo systemctl enable rabbitmq-server

# 创建用户
sudo rabbitmqctl add_user idata Idata@123456
sudo rabbitmqctl set_permissions -p "/" idata ".*" ".*" ".*"

# 验证
curl -u idata:Idata@123456 http://localhost:15672/api/aliveness-test
```

---

## 3️⃣ 后端部署

### 3.1 编译

```bash
cd /home/xiaobofei/apps/idata-ai-platform

bash scripts/build/build-all.sh    # 5-10分钟

# 验证JAR文件
ls -lh backend/lib/*.jar
```

### 3.2 配置

```bash
# 复制配置到各服务
for service in idata-auth idata-system idata-app idata-content idata-payment idata-marketing; do
    cp config/application-prod.yml backend/$service/src/main/resources/application.yml
done

# 编辑并检查关键配置
grep -E "^(server|spring|jwt)" backend/idata-auth/src/main/resources/application.yml
```

### 3.3 启动

```bash
# 启动所有服务
bash scripts/deploy/start-services.sh all

# 等待10秒
sleep 10

# 检查状态
bash scripts/deploy/status.sh

# 应该看到所有6个服务都运行中
```

---

## 4️⃣ 前端部署

### 4.1 构建

```bash
cd frontend
npm install
npm run build

# 验证构建
ls -la dist/
```

### 4.2 Nginx配置

```bash
# 复制配置
sudo cp scripts/nginx/idata-ai.conf /etc/nginx/conf.d/

# 验证
sudo nginx -t

# 启动
sudo systemctl reload nginx
```

### 4.3 验证访问

```bash
# 前端
curl http://localhost | head -20

# API代理
curl http://localhost/api/auth/login
```

---

## 5️⃣ 开机自启

### 5.1 配置systemd

```bash
# 复制服务文件
sudo cp scripts/systemd/idata-backend.service /etc/systemd/system/

# 启用
sudo systemctl daemon-reload
sudo systemctl enable idata-backend.service

# 验证
sudo systemctl is-enabled idata-backend.service  # 应显示 enabled
```

### 5.2 定期备份

```bash
# 手动备份
bash scripts/deploy/backup-database.sh

# 定期备份(cron)
crontab -e
# 添加: 0 2 * * * cd /home/xiaobofei/apps/idata-ai-platform && bash scripts/deploy/backup-database.sh
```

---

## 6️⃣ 日常维护

### 6.1 常用命令

```bash
cd /home/xiaobofei/apps/idata-ai-platform

# 查看状态
bash scripts/deploy/status.sh

# 查看日志
tail -f logs/auth.log

# 重启服务
bash scripts/deploy/restart-services.sh all

# 数据备份
bash scripts/deploy/backup-database.sh
```

### 6.2 故障排查

```bash
# 数据库连接失败
mysql -u idata -pIdata@123456 idata_ai -e "SELECT 1;"

# Redis连接失败
redis-cli ping

# RabbitMQ连接失败
curl -u idata:Idata@123456 http://localhost:15672/api/aliveness-test

# 服务启动失败
tail -100 logs/auth.log
```

---

## 📚 相关文档

- **QUICK_START.md** - 5分钟快速开始
- **DEPLOYMENT_CHECKLIST.md** - 部署检查清单
- **config/README.md** - 配置说明
- **scripts/deploy/README.md** - 脚本说明

---

**文档版本**: v2.0  
**最后更新**: 2026-02-17
