#!/bin/bash

# iData AI 平台 - 后端服务启动脚本
# 用法: bash start-services.sh [all|auth|system|app|content|payment|marketing]

set -e

# 获取脚本所在目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
BASE_DIR="$SCRIPT_DIR"
BACKEND_DIR="$BASE_DIR/backend"
LOGS_DIR="$BASE_DIR/logs"
PIDS_DIR="/tmp/idata-pids"

# 创建必要目录
mkdir -p "$LOGS_DIR" "$PIDS_DIR"

# 配置 JVM 参数
JVM_OPTS="-Xms512m -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# 定义服务(格式: 服务名=端口)
declare -A SERVICES=(
    ["auth"]="8081"
    ["system"]="8082"
    ["app"]="8083"
    ["content"]="8084"
    ["payment"]="8085"
    ["marketing"]="8087"
)

# 获取 JAR 文件路径
get_jar_path() {
    local service=$1
    # 首先尝试从 backend/lib 目录获取
    if [ -f "$BACKEND_DIR/lib/idata-${service}-1.0.0.jar" ]; then
        echo "$BACKEND_DIR/lib/idata-${service}-1.0.0.jar"
    else
        # 如果不存在，尝试从各服务的 target 目录获取
        echo "$BACKEND_DIR/idata-${service}/target/idata-${service}-1.0.0.jar"
    fi
}

# 启动单个服务函数
start_service() {
    local service_name=$1
    local port=$2
    local jar_file=$(get_jar_path "$service_name")
    local pid_file="$PIDS_DIR/${service_name}.pid"
    local log_file="$LOGS_DIR/${service_name}.log"
    
    # 检查 JAR 文件
    if [ ! -f "$jar_file" ]; then
        echo "❌ 错误: 找不到 $jar_file"
        echo "   请先执行编译: bash scripts/build/build-all.sh"
        return 1
    fi
    
    # 检查是否已运行
    if [ -f "$pid_file" ]; then
        local old_pid=$(cat "$pid_file")
        if kill -0 "$old_pid" 2>/dev/null; then
            echo "⚠️  $service_name 已在运行 (PID: $old_pid)"
            return 0
        fi
    fi
    
    echo "🚀 启动 $service_name..."
    nohup java $JVM_OPTS -jar "$jar_file" \
        --server.port=$port \
        > "$log_file" 2>&1 &
    
    local new_pid=$!
    echo $new_pid > "$pid_file"
    
    sleep 2
    
    # 检查启动是否成功
    if kill -0 "$new_pid" 2>/dev/null; then
        echo "✅ $service_name 启动成功 (PID: $new_pid, Port: $port)"
        return 0
    else
        echo "❌ $service_name 启动失败，请检查日志:"
        echo "   tail -100 $log_file"
        return 1
    fi
}

# 主逻辑
case "${1:-all}" in
    all)
        echo "================================"
        echo "🔄 启动所有服务"
        echo "================================"
        failed=0
        for service in "${!SERVICES[@]}"; do
            port=${SERVICES[$service]}
            start_service "$service" "$port" || ((failed++))
        done
        
        if [ $failed -eq 0 ]; then
            echo ""
            echo "✨ 所有服务启动成功！"
            echo ""
            echo "检查状态: bash scripts/deploy/status.sh"
        else
            echo ""
            echo "❌ 有 $failed 个服务启动失败"
            exit 1
        fi
        ;;
    *)
        if [[ ${SERVICES[$1]+_} ]]; then
            port=${SERVICES[$1]}
            start_service "$1" "$port"
        else
            echo "❌ 未知的服务: $1"
            echo ""
            echo "支持的服务: all ${!SERVICES[@]}"
            echo ""
            echo "用法: bash start-services.sh [all|service-name]"
            echo "示例: bash start-services.sh auth"
            echo "示例: bash start-services.sh all"
            exit 1
        fi
        ;;
esac
