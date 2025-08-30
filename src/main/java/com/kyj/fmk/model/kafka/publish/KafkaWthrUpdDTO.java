package com.kyj.fmk.model.kafka.publish;

import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.dto.BaseKafkaDTO;
import com.kyj.fmk.model.res.ResWthrDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class KafkaWthrUpdDTO extends BaseKafkaDTO {

    private LocalDate wthrDate;
    private LocalTime wthrTime;
    private LocalDate wthrBaseDate;
    private LocalTime wthrBaseTime;
    private LocalTime sunSetTime;
    private LocalTime sunRiseTime;
    private String oceanCode;
    private String skyCode;
    private String particleCode;
    private String t1h;
    private String usrSeqId;

    public KafkaWthrUpdDTO(ResWthrDTO resWthrDTO,String usrSeqId){
        this.wthrDate     = resWthrDTO.getWthrDate();
        this.wthrTime     = resWthrDTO.getWthrTime();
        this.wthrBaseDate = resWthrDTO.getWthrBaseDate();
        this.wthrBaseTime = resWthrDTO.getWthrBaseTime();
        this.sunSetTime   = resWthrDTO.getSunSetTime();
        this.sunRiseTime  = resWthrDTO.getSunRiseTime();
        this.oceanCode    = resWthrDTO.getOceanCode();
        this.skyCode      = resWthrDTO.getSkyCode();
        this.particleCode = resWthrDTO.getParticleCode();
        this.t1h          = resWthrDTO.getT1h();
        this.usrSeqId = usrSeqId;
        super.setFrom("WHEATHER-BGN");
        super.setTopic(KafkaTopic.WHEATHER_BGM_WHEATHER_UPDATE);
    }
}
