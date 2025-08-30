package com.kyj.fmk.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 카프카 소모 서비스
 */
public interface WthrKafkaConsumeService {

    public void consumeWthrUpdate(ConsumerRecord<String, String> record, Acknowledgment ack);
}
