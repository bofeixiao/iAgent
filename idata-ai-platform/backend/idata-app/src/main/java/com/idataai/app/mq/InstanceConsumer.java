package com.idataai.app.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idataai.app.service.IInstanceService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 实例消息消费者
 *
 * @author iDataAI
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InstanceConsumer {

    private final IInstanceService instanceService;
    private final ObjectMapper objectMapper;

    /**
     * 监听AI任务队列
     */
    @RabbitListener(queues = "ai.task.queue")
    public void handleAITask(Message message, Channel channel) {
        try {
            // 解析消息
            String messageBody = new String(message.getBody());
            log.info("接收到AI任务消息: {}", messageBody);

            Map<String, Object> taskData = objectMapper.readValue(messageBody, Map.class);
            Long instanceId = Long.valueOf(taskData.get("instanceId").toString());

            // 处理任务
            instanceService.processInstance(instanceId);

            // 手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("AI任务处理成功，实例ID: {}", instanceId);

        } catch (Exception e) {
            log.error("AI任务处理失败", e);
            try {
                // 拒绝消息并重新入队
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (Exception ex) {
                log.error("消息确认失败", ex);
            }
        }
    }
}
