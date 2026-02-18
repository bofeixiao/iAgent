#!/bin/bash

# iData AI 平台 - 全量编译脚本
# 编译后端和前端所有模块

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"

echo "========================================="
echo "🔨 开始编译所有模块"
echo "========================================="
echo ""

# 编译后端
echo "📦 编译后端模块..."
echo "=================="
cd "$SCRIPT_DIR/backend"

if mvn clean package -DskipTests -T 1C; then
    echo "✅ 后端编译成功"
else
    echo "❌ 后端编译失败"
    exit 1
fi

# 创建lib目录并复制JAR文件
mkdir -p lib
cp idata-auth/target/idata-auth-1.0.0.jar lib/ 2>/dev/null || true
cp idata-system/target/idata-system-1.0.0.jar lib/ 2>/dev/null || true
cp idata-app/target/idata-app-1.0.0.jar lib/ 2>/dev/null || true
cp idata-content/target/idata-content-1.0.0.jar lib/ 2>/dev/null || true
cp idata-payment/target/idata-payment-1.0.0.jar lib/ 2>/dev/null || true
cp idata-marketing/target/idata-marketing-1.0.0.jar lib/ 2>/dev/null || true

echo ""

# 编译前端
echo "📦 编译前端模块..."
echo "=================="
cd "$SCRIPT_DIR/frontend"

if npm install && npm run build; then
    echo "✅ 前端编译成功"
else
    echo "❌ 前端编译失败"
    exit 1
fi

echo ""
echo "========================================="
echo "✨ 所有模块编译完成！"
echo "========================================="
echo ""
echo "后续步骤:"
echo "1. 启动服务: bash scripts/deploy/start-services.sh all"
echo "2. 检查状态: bash scripts/deploy/status.sh"
