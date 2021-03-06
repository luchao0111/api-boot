package org.minbox.framework.api.boot.sample.message.pipe.server;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.minbox.framework.message.pipe.server.processing.push.PushMessageEvent;
import org.minbox.framework.message.pipe.spring.annotation.ServerServiceType;
import org.minbox.framework.message.pipe.spring.annotation.server.EnableMessagePipeServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Properties;

/**
 * 消息管道Server相关配置
 *
 * @author 恒宇少年
 */
@Configuration
@EnableMessagePipeServer(serverType = ServerServiceType.NACOS)
public class MessagePipeServerConfiguration {
    /**
     * 配置Redis监听容器
     * <p>
     * 新消息写入消息管道时，
     * 会触发{@link PushMessageEvent}事件，而该事件的监听方式则是采用的Redis的KeyEvent的形式
     *
     * @param connectionFactory Redis连接工厂对象
     * @return The {@link RedisMessageListenerContainer} instance
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    /**
     * 配置Nacos {@link NamingService}实例
     *
     * @return
     * @throws NacosException
     */
    @Bean
    public NamingService namingService() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.USERNAME, "nacos");
        properties.put(PropertyKeyConst.PASSWORD, "nacos");
        properties.put(PropertyKeyConst.SERVER_ADDR, "open.nacos.yuqiyu.com:80");
        return NacosFactory.createNamingService(properties);
    }
}
