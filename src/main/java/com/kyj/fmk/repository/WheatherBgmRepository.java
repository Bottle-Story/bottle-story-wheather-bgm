package com.kyj.fmk.repository;


import com.kyj.fmk.model.req.ReqInsertWthrDTO;

import java.util.List;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 리포지토리 인터페이스
 */
public interface WheatherBgmRepository {
    public void insertWthrM(ReqInsertWthrDTO reqInsertWthrDTO);
    public void insertWthrUiD(ReqInsertWthrDTO reqInsertWthrDTO);
    public List<ReqInsertWthrDTO> selectWthrViewDiff(List<ReqInsertWthrDTO> reqInsertWthrDTOS);

}
