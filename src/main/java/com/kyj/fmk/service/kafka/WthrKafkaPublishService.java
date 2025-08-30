package com.kyj.fmk.service.kafka;

import com.kyj.fmk.model.kafka.publish.KafkaWthrUpdDTO;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 카프카 이벤트 발행 인터페이스
 */
public interface WthrKafkaPublishService {

    public void publishWthrUpd(KafkaWthrUpdDTO kafkaWthrUpdDTO);
}
