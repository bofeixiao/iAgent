#!/bin/bash

# iData AI 平台 - 服务状态检查脚本

PIDS_DIR="/tmp/idata-pids"
SERVICES=("auth" "system" "app" "content" "payment" "marketing")
PORTS=("8081" "8082" "8083" "8084" "8085" "8087")

echo "========================================="
echo "iData AI 平台 - 服务状态检查"
echo "========================================="
echo ""

# 检查后端服务
echo "📱 后端服务状态:"
echo "--------"

running_count=0
total_count=${#SERVICES[@]}

for i in "${!SERVICES[@]}"; do
    service_name=${SERVICES[$i]}
    port=${PORTS[$i]}
    pid_file="$PIDS_DIR/${service_name}.pid"
    
    if [ -f "$pid_file" ]; then
        pid=$(cat "$pid_file")
        if kill -0 "$pid" 2>/dev/null; then
            status="✅ 运行中"
            ((running_count++))
        else
            status="❌ 已停止"
            rm -f "$pid_file"
        fi
    else
        status="❌ 未启动"
    fi
    
    printf "%-20s (Port %-5d) %s" "$service_name" "$port" "$status"
    
    # 检查端口
    if nc -z localhost $port 2>/dev/null; then
        echo " ✓"
    else
        echo " ✗"
    fi
done

echo ""
echo "后端服务运行: $running_count/$total_count"
echo ""

# 检查中间件
echo "🔧 中间件状态:"
echo "--------"

# MySQL
if mysql -u idata -pIdata@123456 -e "SELECT 1;" &>/dev/null; then
    echo "✅ MySQL 正常"
else
    echo "❌ MySQL 异常"
fi

# Redis
if redis-cli ping &>/dev/null; then
    echo "✅ Redis 正常"
else
    echo "❌ Redis 异常"
fi

# RabbitMQ
if curl -s -u idata:Idata@123456 http://localhost:15672/api/aliveness-test 2>/dev/null | grep -q "ok"; then
    echo "✅ RabbitMQ 正常"
else
    echo "❌ RabbitMQ 异常"
fi

echo ""
echo "========================================="

# 返回状态
if [ $running_count -eq $total_count ]; then
    exit 0
else
    exit 1
fi
