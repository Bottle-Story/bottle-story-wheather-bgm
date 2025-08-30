package com.kyj.fmk.repository;

import com.kyj.fmk.mapper.WheatherBgmMapper;

import com.kyj.fmk.model.req.ReqInsertWthrDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 리포지토리 구현체(마이바티스)
 */
@Repository
@RequiredArgsConstructor
public class MybatisWheatherBgmRepository implements WheatherBgmRepository{

    private final WheatherBgmMapper wheatherBgmMapper;

    /**
     * 날씨정보 마스터 인서트
     * @param
     */
    @Override
    public void insertWthrM(ReqInsertWthrDTO reqInsertWthrDTO) {

        wheatherBgmMapper.insertWthrM(reqInsertWthrDTO);
    }


    /**
     * 날씨정보ui테이블 인서트
     * @param
     */
    @Override
    public void insertWthrUiD(ReqInsertWthrDTO reqInsertWthrDTO) {

        wheatherBgmMapper.insertWthrUiD(reqInsertWthrDTO);
    }
    /**
     * 날씨정보 인서트를 위한 타겟 조회
     * @param
     */
    @Override
    public List<ReqInsertWthrDTO> selectWthrViewDiff(List<ReqInsertWthrDTO> reqInsertWthrDTOS) {

        return wheatherBgmMapper.selectWthrViewDiff(reqInsertWthrDTOS);
    }

}
