package com.kyj.fmk.model.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보조회를 위한 위치정보 dto
 */
@Getter
@Setter
public class ReqWthrLocDTO {
    private double lat;
    private double lot;
}
