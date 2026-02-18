#!/bin/bash

# iData AI 平台 - 数据库备份脚本
# 自动备份策略: 保留最近7天的备份

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
BACKUP_DIR="$SCRIPT_DIR/backup"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)
BACKUP_FILE="$BACKUP_DIR/idata_ai_$TIMESTAMP.sql.gz"

# 数据库连接信息
DB_HOST="localhost"
DB_USER="idata"
DB_PASS="Idata@123456"
DB_NAME="idata_ai"

# 创建备份目录
mkdir -p "$BACKUP_DIR"

echo "========================================="
echo "🔄 开始数据库备份..."
echo "========================================="
echo "备份时间: $(date '+%Y-%m-%d %H:%M:%S')"
echo "备份文件: $BACKUP_FILE"
echo ""

# 执行备份
if mysqldump -h "$DB_HOST" -u "$DB_USER" -p"$DB_PASS" "$DB_NAME" | gzip > "$BACKUP_FILE"; then
    FILE_SIZE=$(du -h "$BACKUP_FILE" | cut -f1)
    echo "✅ 备份成功"
    echo "   文件大小: $FILE_SIZE"
else
    echo "❌ 备份失败"
    exit 1
fi

# 清理旧备份(保留最近7天)
echo ""
echo "🧹 清理旧备份(保留最近7天)..."
DELETED_COUNT=0
while IFS= read -r old_file; do
    rm -f "$old_file"
    echo "   删除: $(basename "$old_file")"
    ((DELETED_COUNT++))
done < <(find "$BACKUP_DIR" -name "idata_ai_*.sql.gz" -mtime +7)

if [ $DELETED_COUNT -gt 0 ]; then
    echo "   已删除 $DELETED_COUNT 个过期备份"
else
    echo "   无过期备份"
fi

echo ""
echo "✨ 备份完成！"
echo "========================================="

# 显示最近的备份列表
echo ""
echo "📋 最近的备份文件:"
ls -lh "$BACKUP_DIR"/idata_ai_*.sql.gz 2>/dev/null | tail -5 || echo "   无备份文件"
