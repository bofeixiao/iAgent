#!/bin/bash

# iData AI 平台 - 前端编译脚本

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"

echo "========================================="
echo "🔨 编译前端模块"
echo "========================================="
echo ""

cd "$SCRIPT_DIR/frontend"

echo "📥 安装依赖..."
npm install

echo ""
echo "🔨 构建生产版本..."
npm run build

echo ""
echo "✅ 前端编译成功"
echo ""
echo "生成的文件:"
ls -lh dist/ 2>/dev/null | head -20 || echo "   无构建文件生成"
