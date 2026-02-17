# iData AI创作平台 - 后端工程

## 项目介绍

iData AI创作平台后端工程，基于Spring Boot 3.2.x + MyBatis Plus构建的微服务架构。

## 技术栈

- **Java**: 17
- **框架**: Spring Boot 3.2.2, Spring Cloud 2023.0.0
- **数据库**: MySQL 8.0+, Redis 7.x
- **ORM**: MyBatis Plus 3.5.5
- **安全**: Spring Security 6.x, JWT
- **消息队列**: RabbitMQ 3.12+
- **工具**: Hutool, Lombok, FastJSON2

## 模块说明

| 模块 | 说明 | 端口 |
|-----|------|------|
| idata-common | 公共模块(工具类、基础类) | - |
| idata-gateway | 网关服务 | 8080 |
| idata-auth | 认证服务 | 8081 |
| idata-system | 系统管理服务 | 8082 |
| idata-app | 应用服务 | 8083 |
| idata-content | 内容服务 | 8084 |
| idata-payment | 支付服务 | 8085 |
| idata-ai | AI服务 | 8086 |
| idata-marketing | 营销服务 | 8087 |

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.x
- RabbitMQ 3.12+

### 构建项目

```bash
# 编译所有模块
mvn clean install

# 跳过测试编译
mvn clean install -DskipTests
```

### 运行服务

```bash
# 单独运行某个服务
cd idata-gateway
mvn spring-boot:run

# 或使用java -jar
java -jar target/idata-gateway-1.0.0.jar
```

### 配置文件

每个服务的配置文件位于 `src/main/resources/application.yml`，支持多环境配置：

- `application.yml`: 公共配置
- `application-dev.yml`: 开发环境
- `application-prod.yml`: 生产环境

## 开发规范

### 包命名规范

```
com.idataai.{module}
├── controller      // 控制器层
├── service         // 服务层
│   └── impl        // 服务实现
├── mapper          // 数据访问层
├── domain          // 领域模型
│   ├── entity      // 实体类
│   ├── dto         // 数据传输对象
│   └── vo          // 视图对象
├── config          // 配置类
└── util            // 工具类
```

### 接口规范

所有接口遵循RESTful风格:

- GET: 查询
- POST: 新增
- PUT: 修改
- DELETE: 删除

### 响应格式

统一使用 `Result<T>` 封装响应:

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

## 部署说明

详见 `/docs/deployment/` 目录下的部署文档。

---

**开发团队**: iDataAI  
**创建时间**: 2026-02-15
