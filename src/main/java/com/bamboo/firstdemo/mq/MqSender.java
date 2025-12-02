package com.bamboo.firstdemo.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.springframework.stereotype.Component;

@Component
public class MqSender {
    private ConnectionFactory connectionFactory;
    public void initMq(){
        // 1: 创建连接工厂，设置连接属性
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("113.44.133.43");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("bamboo");
        connectionFactory.setPassword("buaagxz123");
    }

    public void sendMessage(String message){
        try{
            // 2: 从连接工厂中获取
            Connection connection = connectionFactory.newConnection("生产者");
            // 3: 从连接中打开通道channel
            Channel channel = connection.createChannel();

            // 4: 通过创建交换机，声明队列，绑定关系，路由key,发送消息，和接收消息
            /*
             *  申明队列：如果队列不存在会自动创建。
             *  注意：
             *  1.Rabbitmq不允许创建两个相同的队列名称，否则会报错。
             *  2.队列声明可以放在生产者、消费者或web页面上创建。但是在消费者启动监听之前队列一定要创建好。
             *    如果要先启动消费者，建议把声明队列放在消费者端。否在消费者监听队列不存在会报异常。
             *
             *    如果要先启动生产者，建议在生产者端声明队列。虽然发送消息时队列不存不会报错，但第一次发送时队列不存在相当于白发送了。
             *  @params1： queue 队列的名称
             *  @params2： durable 队列是否持久化（即存盘），false = 非持久化 true = 持久化，非持久化会存盘吗？会存盘，但是会随从重启服务会丢失
             *  @params3： exclusive 是否排他，即是否私有的，如果为true,会对当前队列加锁，其他的通道不能访问，并且连接自动关闭
             *  @params4： autoDelete 是否自动删除，当此队列的连接数为0时，此队列会销毁（无论队列中是否还有数据）
             *  @params5： arguments 可以设置队列附加参数，设置队列的有效期，消息的最大长度，队列的消息生命周期等等
             * */
            channel.queueDeclare(
                    "nzf-queue3",
                    true,         // durable
                    false, false, null// not exclusive, not auto-delete
                    // Collections.singletonMap("x-queue-type", "stream")
            );

            // 5: 发送消息给队列queue1
            /*
             * @params1: 交换机exchange
             * @params2: 队列名称、路由key(routing)
             * @params3: 属性配置
             * @params4: 发送消息的内容
             *
             **/

            channel.basicPublish("", "nzf-queue3", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("消息发送成功!");

            channel.basicPublish("", "nzf-queue2", null, message.getBytes());
            System.out.println("消息发送成功!");

            // 最后关闭通关和连接
            channel.close();
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

}
