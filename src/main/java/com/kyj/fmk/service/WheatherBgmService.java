package com.kyj.fmk.service;

import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.model.req.ReqInsertWthrDTO;
import com.kyj.fmk.model.req.ReqWthrLocDTO;

import com.kyj.fmk.model.res.ResWthrDTO;
import org.springframework.http.ResponseEntity;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 서비스
 */
public interface WheatherBgmService {

    public ResponseEntity<ResApiDTO<ResWthrDTO>> selectWhtr(ReqWthrLocDTO reqWthrLocDTO, String usrSeqId);
}
