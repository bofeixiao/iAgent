# iData AI 平台 - 部署检查清单

✅ 完成部分请打勾 ☐  
部署日期: ________  
部署人员: ________

---

## 第一阶段: 环境检查

### 系统和用户
- ☐ Linux 系统已准备 (Ubuntu 20.04+ 或 CentOS 7+)
- ☐ 用户 xiaobofei 已创建
- ☐ 目录 /home/xiaobofei/apps/ 已创建并赋予权限
- ☐ 项目目录 /home/xiaobofei/apps/idata-ai-platform 已准备

### 必需软件
- ☐ JDK 17+ 已安装: `java -version`
- ☐ Maven 3.8+ 已安装: `mvn -version`
- ☐ Node.js 18+ 已安装: `node -v`
- ☐ MySQL 8.0 已安装: `mysql --version`
- ☐ Redis 7.x 已安装: `redis-server --version`
- ☐ RabbitMQ 3.12 已安装: `rabbitmq-server --version`
- ☐ Nginx 已安装: `nginx -v`

---

## 第二阶段: 中间件配置

### MySQL
- ☐ MySQL 服务启动: `sudo systemctl status mysql`
- ☐ 数据库 idata_ai 已创建
- ☐ 用户 idata 已创建，密码为: Idata@123456
- ☐ 初始化脚本已执行: init.sql
- ☐ 附加脚本已执行: 02_additional_tables.sql
- ☐ 连接测试通过: `mysql -u idata -pIdata@123456 idata_ai -e "SELECT 1;"`

### Redis
- ☐ Redis 服务启动: `sudo systemctl status redis-server`
- ☐ 连接测试通过: `redis-cli ping` → PONG

### RabbitMQ
- ☐ RabbitMQ 服务启动: `sudo systemctl status rabbitmq-server`
- ☐ 管理插件已启用: `sudo rabbitmq-plugins enable rabbitmq_management`
- ☐ 用户 idata 已创建，密码为: Idata@123456
- ☐ Web管理界面可访问: http://localhost:15672

---

## 第三阶段: 后端构建

### 编译
- ☐ 进入项目目录: `cd /home/xiaobofei/apps/idata-ai-platform`
- ☐ 执行编译脚本: `bash scripts/build/build-all.sh`
- ☐ 编译成功，无ERROR
- ☐ JAR 文件已生成在各服务的 target/ 目录

### 配置
- ☐ 配置文件已复制: `config/application-prod.yml` → 各服务
- ☐ MySQL 连接信息已修改为正确值
- ☐ Redis 连接信息已修改为正确值
- ☐ RabbitMQ 连接信息已修改为正确值
- ☐ JWT 密钥已修改为自定义值

### 启动
- ☐ 启动脚本执行: `bash scripts/deploy/start-services.sh all`
- ☐ 等待 10 秒后检查状态
- ☐ 执行检查: `bash scripts/deploy/status.sh`
- ☐ 所有 6 个服务显示 ✅ 运行中

### 验证
- ☐ 认证服务 (8081): `curl http://localhost:8081/api/auth/login`
- ☐ 系统服务 (8082): `curl http://localhost:8082/api/user/list`
- ☐ 应用服务 (8083): `curl http://localhost:8083/api/application/list`

---

## 第四阶段: 前端构建和部署

### 构建
- ☐ 进入前端目录: `cd frontend`
- ☐ 安装依赖: `npm install`
- ☐ 编译前端: `npm run build`
- ☐ 生成 dist/ 目录

### Nginx 配置
- ☐ Nginx 已安装
- ☐ 配置文件已复制: `scripts/nginx/idata-ai.conf` → `/etc/nginx/conf.d/`
- ☐ Nginx 语法检查: `sudo nginx -t` → OK
- ☐ Nginx 已重启: `sudo systemctl reload nginx`

---

## 第五阶段: 综合验证

### 功能测试
- ☐ 访问前端: http://localhost
- ☐ 能看到登录页面
- ☐ API 请求返回正常响应

### 日志检查
- ☐ 后端日志无 ERROR: `tail -100 logs/auth.log`
- ☐ Nginx 错误日志无 ERROR: `sudo tail -100 /var/log/nginx/error.log`

### 中间件验证
- ☐ MySQL 正常: `mysql -u idata -pIdata@123456 idata_ai -e "SELECT COUNT(*) FROM t_user;"`
- ☐ Redis 正常: `redis-cli ping`
- ☐ RabbitMQ 正常: 访问 http://localhost:15672

---

## 第六阶段: 运维配置

### 开机自启
- ☐ Systemd 服务文件已配置: `scripts/systemd/idata-backend.service`
- ☐ 复制到系统: `sudo cp scripts/systemd/idata-backend.service /etc/systemd/system/`
- ☐ 重加载: `sudo systemctl daemon-reload`
- ☐ 启用自启: `sudo systemctl enable idata-backend.service`

---

## ✅ 部署完成确认

| 项目 | 状态 |
|-----|------|
| 后端服务 | ✅ 已启动 / ⏳ 进行中 / ❌ 失败 |
| 前端应用 | ✅ 已部署 / ⏳ 进行中 / ❌ 失败 |
| 中间件 | ✅ 正常 / ⏳ 进行中 / ❌ 异常 |
| 总体状态 | ✅ 完成 / ⏳ 进行中 / ❌ 失败 |

---

## 📝 备忘信息

**部署目录**: /home/xiaobofei/apps/idata-ai-platform  
**部署用户**: xiaobofei  
**数据库密码**: Idata@123456  
**RabbitMQ密码**: Idata@123456  
**前端地址**: http://localhost  

---

**最终检查日期**: __________  
**检查人员**: __________  
**签字确认**: __________  

---

**文档版本**: v2.0  
**最后更新**: 2026-02-17
