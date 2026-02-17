package com.idataai.app.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 实例消息生产者
 *
 * @author iDataAI
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InstanceProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    private static final String AI_TASK_EXCHANGE = "ai.task.exchange";
    private static final String AI_TASK_ROUTING_KEY = "ai.task";

    /**
     * 发送AI任务消息
     *
     * @param message 消息内容
     */
    public void sendAITask(Map<String, Object> message) {
        try {
            rabbitTemplate.convertAndSend(AI_TASK_EXCHANGE, AI_TASK_ROUTING_KEY, message);
            log.info("发送AI任务消息成功: {}", objectMapper.writeValueAsString(message));
        } catch (Exception e) {
            log.error("发送AI任务消息失败", e);
            throw new RuntimeException("发送AI任务消息失败", e);
        }
    }
}
