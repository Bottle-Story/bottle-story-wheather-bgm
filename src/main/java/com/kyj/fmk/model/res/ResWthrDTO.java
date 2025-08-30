package com.kyj.fmk.model.res;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보를 클라이언트에 전달하는 DTO
 */
@Getter
@Setter
public class ResWthrDTO {

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
}
