package com.kyj.fmk.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.model.kafka.publish.KafkaWthrUpdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 카프카 이벤트 발행 서비스
 *
 */
@Service
@RequiredArgsConstructor
public class WthrKafkaPublishServiceImpl implements WthrKafkaPublishService{

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 날씨정보 업데이트 큐 전송
     * @param kafkaWthrUpdDTO
     */
    @Override
    public void publishWthrUpd(KafkaWthrUpdDTO kafkaWthrUpdDTO) {

        String data = null;
        try {
            data = objectMapper.writeValueAsString(kafkaWthrUpdDTO);
        } catch (JsonProcessingException e) {
            throw new KyjSysException(CmErrCode.CM016);
        }
        if (data == null){
            throw new KyjSysException(CmErrCode.CM021);

        }
        // Kafka 전송
        kafkaTemplate.send(kafkaWthrUpdDTO.getTopic(),data);
    }
}
