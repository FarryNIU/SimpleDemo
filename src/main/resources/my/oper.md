rabbitmqctl add_user bamboo buaagxz123
rabbitmqctl set_user_tags bamboo administrator
rabbitmqctl set_permissions -p / bamboo ".*" ".*" ".*"

优先级队列 死信队列 延时队列