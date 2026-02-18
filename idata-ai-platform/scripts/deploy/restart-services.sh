#!/bin/bash

# iData AI 平台 - 后端服务重启脚本
# 用法: bash restart-services.sh [all|service-name]

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"

case "${1:-all}" in
    all)
        echo "================================"
        echo "🔄 重启所有服务"
        echo "================================"
        bash "$SCRIPT_DIR/scripts/deploy/stop-services.sh" all
        sleep 5
        bash "$SCRIPT_DIR/scripts/deploy/start-services.sh" all
        ;;
    *)
        echo "🔄 重启 $1..."
        bash "$SCRIPT_DIR/scripts/deploy/stop-services.sh" "$1"
        sleep 2
        bash "$SCRIPT_DIR/scripts/deploy/start-services.sh" "$1"
        ;;
esac

sleep 3
echo "✨ 重启完成"
bash "$SCRIPT_DIR/scripts/deploy/status.sh"
