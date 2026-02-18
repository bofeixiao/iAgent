#!/bin/bash

# iData AI 平台 - 后端编译脚本

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"

echo "========================================="
echo "🔨 编译后端模块"
echo "========================================="
echo ""

cd "$SCRIPT_DIR/backend"

# 编译所有模块
mvn clean package -DskipTests -T 1C

# 创建lib目录并复制JAR文件
mkdir -p lib
cp idata-auth/target/idata-auth-1.0.0.jar lib/ 2>/dev/null || true
cp idata-system/target/idata-system-1.0.0.jar lib/ 2>/dev/null || true
cp idata-app/target/idata-app-1.0.0.jar lib/ 2>/dev/null || true
cp idata-content/target/idata-content-1.0.0.jar lib/ 2>/dev/null || true
cp idata-payment/target/idata-payment-1.0.0.jar lib/ 2>/dev/null || true
cp idata-marketing/target/idata-marketing-1.0.0.jar lib/ 2>/dev/null || true

echo ""
echo "✅ 后端编译成功"
echo ""
echo "生成的 JAR 文件:"
ls -lh lib/*.jar 2>/dev/null || echo "   无 JAR 文件生成"
