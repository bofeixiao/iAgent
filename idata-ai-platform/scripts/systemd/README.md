# Systemd 服务配置说明

本目录包含用于系统启动、停止和自启的 Systemd 服务文件。

## 📋 服务文件

### idata-backend.service - 后端服务

启动所有6个后端微服务的Systemd服务。

**安装方式**:
```bash
# 复制服务文件到系统目录
sudo cp idata-backend.service /etc/systemd/system/

# 重新加载Systemd配置
sudo systemctl daemon-reload

# 启用自启(开机时自动启动)
sudo systemctl enable idata-backend.service

# 立即启动服务
sudo systemctl start idata-backend.service

# 查看状态
sudo systemctl status idata-backend.service

# 查看日志
sudo journalctl -u idata-backend.service -f
```

### idata-frontend.service - 前端服务(Nginx)

管理Nginx服务，用于前端静态文件和API代理。

**安装方式**:
```bash
# 注意: 这个服务管理Nginx本身，通常Nginx已有自己的服务
# 如果需要特别配置，才使用这个文件

sudo cp idata-frontend.service /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable idata-frontend.service
```

## 🚀 命令速查

```bash
# 启动后端服务
sudo systemctl start idata-backend.service

# 停止后端服务
sudo systemctl stop idata-backend.service

# 重启后端服务
sudo systemctl restart idata-backend.service

# 查看状态
sudo systemctl status idata-backend.service

# 查看日志(最后100行)
sudo journalctl -u idata-backend -n 100

# 实时查看日志
sudo journalctl -u idata-backend -f

# 禁用自启
sudo systemctl disable idata-backend.service

# 查看自启状态
sudo systemctl is-enabled idata-backend.service
```
