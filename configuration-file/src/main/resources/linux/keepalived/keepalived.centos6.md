### 1 installation
```shell
yum install epel-release
yum install keepalived -y
```
### 2 query configuration file location
```
rpm -qc keepalived
```

### 3 configuration
192.168.56.21 与 192.168.56.22 组成双活keepalived，互相BACKUP，DNS可以配置两个IP分别是192.168.56.200和192.168.56.201
#### 3.1 192.168.56.21

rename a.txt to /etc/keepalived/keepalived.conf
```shell
cat >> a.txt <<EOF
! Configuration File for keepalived
# 全局配置，可以理解为没有用
global_defs {
   notification_email {
     acassen@firewall.loc
     failover@firewall.loc
     sysadmin@firewall.loc
   }
   notification_email_from Alexandre.Cassen@firewall.loc
   smtp_server 192.168.56.21
   smtp_connect_timeout 30
   router_id LVS_DEVEL
}

vrrp_script chk_nginx {
#   可以是自己写的检测脚本。也可以是一行命令如killall -0 nginx
    script "/etc/keepalived/check_nginx.sh"
#   每2s检测一次
    interval 2
#   检测失败（脚本返回非0）则优先级 -5
    weight -5
#   检测连续 2 次失败才算确定是真失败。会用weight减少优先级（1-255之间）
    fall 2
#   检测 1 次成功就算成功。但不修改优先级
    rise 1
}


#虚拟路由1的配置
vrrp_instance VI_1 {
    state MASTER
    interface eth0
    # 虚拟路由1的ID，这个需要跟BACKUP保持一致
    virtual_router_id 51
    #MASTER 优先级比BACKUP要高，51这个路由为例MASTER优先级是100，而3.2 中51这个路由id的BACKUP是99
    priority 100
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.56.200
    }
    track_script {
        chk_nginx
    }    
}

vrrp_instance VI_2 {
    state BACKUP
    interface eth0
    # 虚拟路由2的ID，这个需要跟MASTER保持一致
    virtual_router_id 52
    priority 99
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.56.201
    }
    track_script {
        chk_nginx
    }        
}
EOF
```
#### 3.2 192.168.56.22
```shell
cat >> a.txt <<EOF
! Configuration File for keepalived

global_defs {
   notification_email {
     acassen@firewall.loc
     failover@firewall.loc
     sysadmin@firewall.loc
   }
   notification_email_from Alexandre.Cassen@firewall.loc
   smtp_server 192.168.56.22
   smtp_connect_timeout 30
   router_id LVS_DEVEL
}


vrrp_script chk_nginx {
#   可以是自己写的检测脚本。也可以是一行命令如killall -0 nginx
    script "/etc/keepalived/check_nginx.sh"
#   每2s检测一次
    interval 2
#   检测失败（脚本返回非0）则优先级 -5
    weight -5
#   检测连续 2 次失败才算确定是真失败。会用weight减少优先级（1-255之间）
    fall 2
#   检测 1 次成功就算成功。但不修改优先级
    rise 1
}

vrrp_instance VI_1 {
    state BACKUP
    interface eth0
    virtual_router_id 51
    priority 99
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.56.200
    }
    track_script {
        chk_nginx
    }        
}


vrrp_instance VI_2 {
    state MASTER
    interface eth0
    virtual_router_id 52
    priority 100
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.56.201
    }
    track_script {
        chk_nginx
    }        
}
EOF
```

### 4 check_nginx
/etc/keepalived/check_nginx.sh ：
```
#!/bin/bash
# ps -C 统计命令nginx，--no-heading 去掉表格头，wc -l，统计行数
counter=$(ps -C nginx --no-heading|wc -l)
if [ "${counter}" = "0" ]; then
    #如果nginx挂了，那么重新启动
    /etc/init.d/nginx start
    sleep 2
    counter=$(ps -C nginx --no-heading|wc -l)
    if [ "${counter}" = "0" ]; then
        #如果重新启动nginx失败，那么自杀
        /etc/init.d/keepalived stop
    fi
fi
```
切记
```
chmod +x check_nginx.sh
```

### 5 start
```shell
service keepalived start
service keepalived status
service keepalived stop
```


### 6 reference
````
                   +-------------+
                   |    uplink   |
                   +-------------+
                          |
                          +
    MASTER            keep|alived         BACKUP
172.29.88.224      172.29.88.222      172.29.88.225
+-------------+    +-------------+    +-------------+
|   nginx01   |----|  virtualIP  |----|   nginx02   |
+-------------+    +-------------+    +-------------+
                          |
       +------------------+------------------+
       |                  |                  |
+-------------+    +-------------+    +-------------+
|    web01    |    |    web02    |    |    web03    |
+-------------+    +-------------+    +-------------+

````
[nginx-keepalived-ha](http://seanlook.com/2015/05/18/nginx-keepalived-ha/)