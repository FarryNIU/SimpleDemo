#RabbitMQ
rabbitmq创建用户，并赋权限
rabbitmqctl add_user bamboo buaagxz123
rabbitmqctl set_user_tags bamboo administrator
rabbitmqctl set_permissions -p / bamboo ".*" ".*" ".*"

# Redis
## 操作
### linux登录Redis
redis-cli -h 127.0.0.1 -p 6379 -a buaagxz123
### 查看所有key值

优先级队列 死信队列 延时队列