package com.kyj.fmk.mapper;


import com.kyj.fmk.model.req.ReqInsertWthrDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 매퍼
 */
@Mapper
public interface WheatherBgmMapper {
    public void insertWthrM(ReqInsertWthrDTO reqWthrMDTO);
    public void insertWthrUiD(ReqInsertWthrDTO reqWthrUiDDTO);
    public List<ReqInsertWthrDTO> selectWthrViewDiff(List<ReqInsertWthrDTO> reqInsertWthrDTOS);

}
