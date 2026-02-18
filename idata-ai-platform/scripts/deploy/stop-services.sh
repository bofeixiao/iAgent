#!/bin/bash

# iData AI 平台 - 后端服务停止脚本
# 用法: bash stop-services.sh [all|auth|system|app|content|payment|marketing]

set -e

PIDS_DIR="/tmp/idata-pids"
SERVICES=("auth" "system" "app" "content" "payment" "marketing")

stop_service() {
    local service_name=$1
    local pid_file="$PIDS_DIR/${service_name}.pid"
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if kill -0 "$pid" 2>/dev/null; then
            echo "⏹️  停止 $service_name (PID: $pid)..."
            kill "$pid"
            sleep 2
            
            # 如果还没停止，强制杀死
            if kill -0 "$pid" 2>/dev/null; then
                kill -9 "$pid"
            fi
            
            rm -f "$pid_file"
            echo "✅ $service_name 已停止"
        else
            rm -f "$pid_file"
            echo "⚠️  $service_name 未在运行"
        fi
    else
        echo "⚠️  $service_name 未在运行"
    fi
}

case "${1:-all}" in
    all)
        echo "================================"
        echo "🔄 停止所有服务"
        echo "================================"
        for service in "${SERVICES[@]}"; do
            stop_service "$service"
        done
        echo ""
        echo "✨ 所有服务已停止"
        ;;
    *)
        if [[ " ${SERVICES[@]} " =~ " ${1} " ]]; then
            stop_service "$1"
        else
            echo "❌ 未知的服务: $1"
            echo "支持的服务: all ${SERVICES[@]}"
            exit 1
        fi
        ;;
esac
