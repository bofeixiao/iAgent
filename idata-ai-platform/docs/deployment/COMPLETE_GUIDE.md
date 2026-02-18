# iData AI 平台 - 完整部署指南

📖 **这是最详细的部署文档**  
适用于第一次部署的用户，包含所有细节步骤。

---

## 📋 目录

1. [环境准备](#环境准备)
2. [中间件安装](#中间件安装)
3. [项目编译](#项目编译)
4. [服务配置](#服务配置)
5. [服务启动](#服务启动)
6. [前端部署](#前端部署)
7. [开机自启](#开机自启)
8. [故障排查](#故障排查)

---

## 环境准备

### 1.1 创建部署用户和目录

```bash
# 创建用户
sudo useradd -m -d /home/xiaobofei xiaobofei
sudo usermod -aG sudo xiaobofei

# 切换到部署用户
su - xiaobofei

# 创建部署目录
mkdir -p /home/xiaobofei/apps
```

### 1.2 安装 Java 17

```bash
# Ubuntu/Debian
sudo apt-get update
sudo apt-get install -y openjdk-17-jdk

java -version  # 验证
```

### 1.3 安装 Maven 3.8+

```bash
sudo apt-get install -y maven
mvn -version  # 验证
```

### 1.4 安装 Node.js 18+

```bash
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt-get install -y nodejs

node -v  # 验证
npm -v   # 验证
```

### 1.5 安装 Nginx

```bash
sudo apt-get install -y nginx
sudo systemctl start nginx
sudo systemctl enable nginx
```

---

## 中间件安装

### 2.1 MySQL 8.0

```bash
# 安装
sudo apt-get install -y mysql-server

# 启动
sudo systemctl start mysql
sudo systemctl enable mysql

# 初始化(修改root密码)
sudo mysql -u root

# 在MySQL中执行
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Root@123456';
FLUSH PRIVILEGES;
EXIT;

# 创建应用数据库
mysql -u root -p << EOF
CREATE DATABASE IF NOT EXISTS idata_ai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'idata'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Idata@123456';
GRANT ALL PRIVILEGES ON idata_ai.* TO 'idata'@'localhost';
FLUSH PRIVILEGES;
EXIT;
EOF

# 导入初始化脚本
cd /home/xiaobofei/apps/idata-ai-platform
mysql -u idata -p idata_ai < database/schema/init.sql
mysql -u idata -p idata_ai < database/schema/02_additional_tables.sql
```

### 2.2 Redis 7.x

```bash
# 安装
sudo apt-get install -y redis-server

# 启动
sudo systemctl start redis-server
sudo systemctl enable redis-server

# 验证
redis-cli ping  # 应返回 PONG
```

### 2.3 RabbitMQ 3.12

```bash
# 安装
sudo apt-get install -y erlang rabbitmq-server

# 启动
sudo systemctl start rabbitmq-server
sudo systemctl enable rabbitmq-server

# 启用管理插件
sudo rabbitmq-plugins enable rabbitmq_management

# 创建用户
sudo rabbitmqctl add_user idata Idata@123456
sudo rabbitmqctl set_permissions -p "/" idata ".*" ".*" ".*"

# 验证
# 访问: http://localhost:15672
# 账户: idata / Idata@123456
```

---

## 项目编译

### 3.1 获取项目源码

```bash
cd /home/xiaobofei/apps
# 上传项目或使用git克隆
# git clone <repo-url> idata-ai-platform
```

### 3.2 编译后端和前端

```bash
cd /home/xiaobofei/apps/idata-ai-platform

# 编译所有(后端+前端)
bash scripts/build/build-all.sh

# 仅编译后端
bash scripts/build/build-backend.sh

# 仅编译前端
bash scripts/build/build-frontend.sh
```

---

## 服务配置



---

## 服务启动

### 5.1 启动所有后端服务

```bash
cd /home/xiaobofei/apps/idata-ai-platform

# 启动所有服务
bash scripts/deploy/start-services.sh all

# 检查状态
bash scripts/deploy/status.sh

# 查看日志
tail -f logs/auth.log
```

---

## 前端部署

### 6.1 配置Nginx

```bash
# 复制配置文件
sudo cp scripts/nginx/idata-ai.conf /etc/nginx/conf.d/

# 验证配置
sudo nginx -t

# 重新加载
sudo systemctl reload nginx
```

### 6.2 访问应用

```bash
# 前端地址
http://localhost

# RabbitMQ管理
http://localhost:15672
```

---

## 开机自启

### 7.1 配置Systemd服务

```bash
# 复制服务文件
sudo cp scripts/systemd/idata-backend.service /etc/systemd/system/

# 重新加载
sudo systemctl daemon-reload

# 启用自启
sudo systemctl enable idata-backend.service

# 验证
sudo systemctl is-enabled idata-backend.service
```

---

## 故障排查

### 问题1: 无法连接数据库

```bash
# 检查MySQL是否运行
sudo systemctl status mysql

# 测试连接
mysql -u idata -pIdata@123456 idata_ai -e "SELECT 1;"

# 查看配置文件
grep -A5 datasource backend/idata-auth/src/main/resources/application.yml
```

### 问题2: 无法连接Redis

```bash
# 检查Redis是否运行
sudo systemctl status redis-server

# 测试连接
redis-cli ping
```

### 问题3: RabbitMQ连接失败

```bash
# 检查RabbitMQ是否运行
sudo systemctl status rabbitmq-server

# 查看用户和权限
sudo rabbitmqctl list_users
sudo rabbitmqctl list_permissions
```

### 问题4: 服务启动失败

```bash
# 查看详细日志
tail -100 logs/auth.log

# 检查JAR文件是否存在
ls -la backend/lib/

# 检查端口是否被占用
lsof -i :8081
```

---

## 常用命令速查

```bash
cd /home/xiaobofei/apps/idata-ai-platform

# 查看状态
bash scripts/deploy/status.sh

# 查看日志
tail -f logs/auth.log

# 启动所有服务
bash scripts/deploy/start-services.sh all

# 停止所有服务
bash scripts/deploy/stop-services.sh all

# 重启所有服务
bash scripts/deploy/restart-services.sh all

# 备份数据库
bash scripts/deploy/backup-database.sh
```

---

**文档版本**: v2.0  
**最后更新**: 2026-02-17
