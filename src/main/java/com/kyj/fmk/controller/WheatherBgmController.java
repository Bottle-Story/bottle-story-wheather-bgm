package com.kyj.fmk.controller;

import com.kyj.fmk.core.model.dto.ResApiDTO;
import com.kyj.fmk.model.req.ReqWthrLocDTO;
import com.kyj.fmk.model.res.ResWthrDTO;
import com.kyj.fmk.sec.dto.oauth2.CustomOAuth2User;
import com.kyj.fmk.service.WheatherBgmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 2025-08-30
 * @author 김용준
 * 날씨정보 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wheatherbgm")
public class WheatherBgmController {

    private final WheatherBgmService wheatherBgmService;

    /**
     * 날씨정보를 조회하고 인서트하는 컨트롤러
     * @param reqWthrLocDTO
     * @param customOAuth2User
     * @return
     */
    @PostMapping("/wthr")
    public ResponseEntity<ResApiDTO<ResWthrDTO>> selectWthr(@RequestBody ReqWthrLocDTO reqWthrLocDTO
                                                 , @AuthenticationPrincipal CustomOAuth2User customOAuth2User){
        String usrSeqId= String.valueOf(customOAuth2User.getUsrSeqId());

        return wheatherBgmService.selectWhtr(reqWthrLocDTO,usrSeqId);
    }
}
