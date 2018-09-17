### 1 install
```shell
yum install epel-release
yum install haproxy
```
### 2 config
```
rpm -qc haproxy
vi 



```
```
# 监听端口
frontend  main *:8088
    # 指定后端程序app
    default_backend             app
# 后端程序app配置
backend app
    balance     roundrobin
    #指定后端服务地址
    server  app1 192.168.56.21:8080 check
    server  app2 192.168.56.22:8080 check
```
### 3 start
```
/etc/init.d/haproxy start
```

