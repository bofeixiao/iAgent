# 配置文件说明

本目录包含所有部署配置文件的模板和示例。

## 📁 文件说明

| 文件 | 说明 | 用途 |
|-----|------|------|
| application-template.yml | 配置模板(包含所有选项) | 参考和复制 |
| application-prod.yml | 生产环境示例 | 直接使用或参考 |
| application-env.yml | 环境变量说明 | 了解可用变量 |

## 🚀 使用流程

### 步骤1: 复制配置文件

```bash
cd /home/xiaobofei/apps/idata-ai-platform

# 复制到各服务
for service in idata-auth idata-system idata-app idata-content idata-payment idata-marketing; do
    cp config/application-prod.yml backend/$service/src/main/resources/application.yml
done
```

### 步骤2: 修改配置文件

编辑每个服务的配置文件，确保以下信息正确:

```yaml
# 数据库连接
spring.datasource.url: jdbc:mysql://localhost:3306/idata_ai
spring.datasource.username: idata
spring.datasource.password: Idata@123456

# Redis 连接
spring.redis.host: localhost
spring.redis.port: 6379

# RabbitMQ 连接
spring.rabbitmq.host: localhost
spring.rabbitmq.username: idata
spring.rabbitmq.password: Idata@123456

# JWT 密钥(必须修改!)
jwt.secret: Your-Custom-Secret-Key-32-Characters-Minimum
```

### 步骤3: 编译和启动

```bash
# 编译
bash scripts/build/build-all.sh

# 启动
bash scripts/deploy/start-services.sh all
```

## ⚙️ 服务端口配置

| 服务 | 配置文件 | 默认端口 |
|------|---------|---------|
| idata-auth | backend/idata-auth/src/main/resources/application.yml | 8081 |
| idata-system | backend/idata-system/src/main/resources/application.yml | 8082 |
| idata-app | backend/idata-app/src/main/resources/application.yml | 8083 |
| idata-content | backend/idata-content/src/main/resources/application.yml | 8084 |
| idata-payment | backend/idata-payment/src/main/resources/application.yml | 8085 |
| idata-marketing | backend/idata-marketing/src/main/resources/application.yml | 8087 |
