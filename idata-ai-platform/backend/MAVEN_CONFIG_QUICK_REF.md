# Maven 统一输出目录配置 - 快速参考

## ✅ 配置已生效

所有 JAR 文件现在输出到：`d:\target\idata-ai-platform`

## 快速命令

| 操作 | 命令 |
|------|------|
| 标准打包 | `mvn clean package` |
| 快速打包 | `mvn clean package -DskipTests` |
| 仅编译 | `mvn clean compile` |
| 清理输出 | `mvn clean` 或 `rm -Force -Recurse d:\target\idata-ai-platform` |
| 查看 JAR | `dir d:\target\idata-ai-platform /s /b *.jar` |

## JAR 文件位置

```
d:\target\idata-ai-platform\
├── idata-common\idata-common-1.0.0.jar (35.8 KB)
├── idata-auth\idata-auth-1.0.0.jar (64.9 MB)
├── idata-system\idata-system-1.0.0.jar (65.0 MB)
├── idata-app\idata-app-1.0.0.jar (65.0 MB)
├── idata-content\idata-content-1.0.0.jar (65.0 MB)
├── idata-payment\idata-payment-1.0.0.jar (65.0 MB)
└── idata-marketing\idata-marketing-1.0.0.jar (65.0 MB)
```

## 配置修改位置

**文件:** `backend/pom.xml`

**修改内容:**
1. 在 `<properties>` 中添加共享输出目录定义
2. 在 `<build>` 中配置 JAR 和 Spring Boot 插件的输出位置

## 优势

✓ 所有 JAR 集中在一个位置  
✓ 便于部署和收集  
✓ 节省磁盘空间  
✓ 易于清理  
✓ 所有子模块自动继承  

## 无需修改

- ✓ 子模块 pom.xml 无需修改
- ✓ 编译命令完全相同
- ✓ 测试命令完全相同

---

**完整配置文档:** 参考 `MAVEN_BUILD_OUTPUT_CONFIG.md`
