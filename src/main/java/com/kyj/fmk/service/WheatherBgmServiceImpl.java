package com.kyj.fmk.service;

import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.core.model.wheather.*;
import com.kyj.fmk.core.service.eai.WheatherApiService;
import com.kyj.fmk.core.util.DetermineUiCode;
import com.kyj.fmk.core.util.DetermineWhtr;
import com.kyj.fmk.model.req.ReqInsertWthrDTO;
import com.kyj.fmk.model.req.ReqWthrLocDTO;
import com.kyj.fmk.model.res.ResWthrDTO;
import com.kyj.fmk.repository.WheatherBgmRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 서비스 구현체
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WheatherBgmServiceImpl implements WheatherBgmService{

    private final WheatherBgmRepository wheatherBgmRepository;
    private final WheatherApiService wheatherApiService;

    /**
     * 날씨정보를 인서트하고 조회 하는 서비스
     * @param
     */
    @Override
    @Transactional
    public ResponseEntity<ResApiDTO<ResWthrDTO>> selectWhtr(ReqWthrLocDTO reqWthrLocDTO, String usrSeqId) {

        ReqWheatherApiDTO reqWheatherApiDTO = new ReqWheatherApiDTO();
        reqWheatherApiDTO.setLot(reqWthrLocDTO.getLot());
        reqWheatherApiDTO.setLat(reqWthrLocDTO.getLat());



        //기상청 정보 조회
        ResSunRiseSetApiDTO resSunRiseSetApiDTO = wheatherApiService.loadSunRiseSet(reqWheatherApiDTO);
        //일몰 일출 조회
        List<ResWheatherApiDTO> resWheatherApiDTOList =  wheatherApiService.loadWheather(reqWheatherApiDTO);

        //insertList 조회를 위한 값 세팅
        List<ReqInsertWthrDTO> selectTargetList = new ArrayList<>();


        // ui상태코드 등 값 세팅
        for (ResWheatherApiDTO iter : resWheatherApiDTOList){

            ReqInsertWthrDTO reqInsertWthrDTO = new ReqInsertWthrDTO();

            setResWheatherApiDTOList(reqInsertWthrDTO,iter,resSunRiseSetApiDTO,usrSeqId);

            selectTargetList.add(reqInsertWthrDTO);
        }
        log.info("size={}",selectTargetList.size());

        //테이블에 없는 데이터만 조회 하여 세팅
        List<ReqInsertWthrDTO> insertList = wheatherBgmRepository.selectWthrViewDiff(selectTargetList);

        //테이블 2개에 대한 인서트
        for (ReqInsertWthrDTO dto : insertList){

            wheatherBgmRepository.insertWthrM(dto);
            wheatherBgmRepository.insertWthrUiD(dto);
        }


        ResWthrDTO resWthrDTO = setResWthrDTO(selectTargetList);

        ResApiDTO<ResWthrDTO> resApiDTO = new ResApiDTO<>(resWthrDTO);

        return ResponseEntity
                .ok(resApiDTO);
    }


    /**
     * ResWheatherApiDTOList 세팅
     * @param reqInsertWthrDTO
     * @param iter
     * @param resSunRiseSetApiDTO
     */
    private void setResWheatherApiDTOList(ReqInsertWthrDTO reqInsertWthrDTO ,ResWheatherApiDTO iter
            ,ResSunRiseSetApiDTO resSunRiseSetApiDTO,String usrSeqId){
        reqInsertWthrDTO.setWthrBaseTime(iter.getWthrBaseTime());
        reqInsertWthrDTO.setWthrBaseDate(iter.getWthrBaseDate());
        reqInsertWthrDTO.setWthrDate(iter.getWthrDate());
        reqInsertWthrDTO.setWthrTime(iter.getWthrTime());
        reqInsertWthrDTO.setLgt(iter.getWthData().getLgt());
        reqInsertWthrDTO.setPty(iter.getWthData().getPty());
        reqInsertWthrDTO.setSky(iter.getWthData().getSky());
        reqInsertWthrDTO.setWsd(iter.getWthData().getWsd());
        reqInsertWthrDTO.setT1h(Integer.parseInt(iter.getWthData().getT1h()));
        reqInsertWthrDTO.setUsrSeqId(BigInteger.valueOf(Integer.parseInt(usrSeqId)));

        reqInsertWthrDTO.setLgtNm(DetermineWhtr.determineLgtNm(String.valueOf(iter.getWthData().getLgt())));
        reqInsertWthrDTO.setSkyNm(DetermineWhtr.determineSkyNm(String.valueOf(iter.getWthData().getSky())));
        reqInsertWthrDTO.setPtyNm(DetermineWhtr.determinePtyNm(String.valueOf(iter.getWthData().getPty())));
        reqInsertWthrDTO.setWsdNm(DetermineWhtr.determineWsdNm(String.valueOf(iter.getWthData().getWsd())));
        reqInsertWthrDTO.setSunRiseTime(resSunRiseSetApiDTO.getSunRiseTime());
        reqInsertWthrDTO.setSunSetTime(resSunRiseSetApiDTO.getSunSetTime());

        ReqDetermineUiCode reqDetermineUiCode = new ReqDetermineUiCode();
        reqDetermineUiCode.setWhtrData(iter.getWthData());
        reqDetermineUiCode.setWthrTime(iter.getWthrTime());
        reqDetermineUiCode.setSunRiseTime(resSunRiseSetApiDTO.getSunRiseTime());
        reqDetermineUiCode.setSunSetTime(resSunRiseSetApiDTO.getSunSetTime());

        WhtrUiCode whtrUiCode =  DetermineUiCode.determineUiCode(reqDetermineUiCode);

        reqInsertWthrDTO.setOceanCode(whtrUiCode.getOceanCode());
        reqInsertWthrDTO.setParticleCode(whtrUiCode.getParticleCode());
        reqInsertWthrDTO.setSkyCode(whtrUiCode.getSkyCode());
    }


    /**
     * ResWthrDTO 세팅
     * @param selectTargetList
     * @return
     */
    private ResWthrDTO setResWthrDTO(List<ReqInsertWthrDTO> selectTargetList ){

        ResWthrDTO resWthrDTO = new ResWthrDTO();

        // 오늘 날짜 + 시간
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        // 무조건 올림 정각 계산
        int hour = now.getHour();

        if (now.getMinute() > 0 || now.getSecond() > 0 || now.getNano() > 0) {
            hour += 1; // 현재 시각에 1시간 더함
        }

        // 24시 처리
        if (hour >= 24) {
            hour = 0;
        }

        LocalTime wthrTime = LocalTime.of(hour, 0);


        for (ReqInsertWthrDTO dto : selectTargetList){


            if(dto.getWthrTime().equals(wthrTime)){
                resWthrDTO.setOceanCode(dto.getOceanCode());
                resWthrDTO.setSkyCode(dto.getSkyCode());
                resWthrDTO.setParticleCode(dto.getParticleCode());
                resWthrDTO.setSunSetTime(dto.getSunSetTime());
                resWthrDTO.setSunRiseTime(dto.getSunRiseTime());
                resWthrDTO.setWthrDate(dto.getWthrDate());
                resWthrDTO.setWthrTime(dto.getWthrTime());
                resWthrDTO.setWthrBaseDate(dto.getWthrBaseDate());
                resWthrDTO.setWthrBaseTime(dto.getWthrBaseTime());
                resWthrDTO.setT1h(String.valueOf(dto.getT1h()));
            }
        }

        return resWthrDTO;
    }

}
