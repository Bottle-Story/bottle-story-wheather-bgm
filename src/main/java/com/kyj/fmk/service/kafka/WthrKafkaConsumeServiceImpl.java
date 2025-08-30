package com.kyj.fmk.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.model.kafka.consume.ConsumeWthUpdDTO;
import com.kyj.fmk.model.kafka.publish.KafkaWthrUpdDTO;
import com.kyj.fmk.model.req.ReqWthrLocDTO;
import com.kyj.fmk.model.res.ResWthrDTO;
import com.kyj.fmk.service.WheatherBgmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 카프카 컨슘 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WthrKafkaConsumeServiceImpl implements WthrKafkaConsumeService{

    private final ObjectMapper objectMapper;
    private final WheatherBgmService wheatherBgmService;
    private final WthrKafkaPublishService kafkaPublishService;

    @Override
    @KafkaListener(
            topics = KafkaTopic.BATCH_WHEATHER_UPDATE,
            groupId = "#{ 'weather-bgm.consume.' + T(com.kyj.fmk.core.model.KafkaTopic).BATCH_WHEATHER_UPDATE }"
    )
    public void consumeWthrUpdate(ConsumerRecord<String, String> record, Acknowledgment ack) {

       String json = record.value();

       ConsumeWthUpdDTO consumeWthUpdDTO = null;
        try {
             consumeWthUpdDTO = objectMapper.readValue(json,ConsumeWthUpdDTO.class);
        } catch (JsonProcessingException e) {

            throw new KyjSysException(CmErrCode.CM016);
        }

        if(consumeWthUpdDTO == null){
            throw new KyjSysException(CmErrCode.CM020);
        }

        ReqWthrLocDTO reqWthrLocDTO = new ReqWthrLocDTO();
        reqWthrLocDTO.setLat(Double.parseDouble(consumeWthUpdDTO.getLat()));
        reqWthrLocDTO.setLot(Double.parseDouble(consumeWthUpdDTO.getLot()));

        ResponseEntity<ResApiDTO<ResWthrDTO>> result = wheatherBgmService.selectWhtr(reqWthrLocDTO, consumeWthUpdDTO.getUsrSeqId());

        ResApiDTO<ResWthrDTO> body =  result.getBody();

        ResWthrDTO resWthr = null;

        if (body != null) {
            resWthr = (ResWthrDTO)body.getData();
        }

        KafkaWthrUpdDTO kafkaWthrUpdDTO = new KafkaWthrUpdDTO(Objects.requireNonNull(resWthr), consumeWthUpdDTO.getUsrSeqId());

        kafkaPublishService.publishWthrUpd(kafkaWthrUpdDTO);

        ack.acknowledge();

    }
}
