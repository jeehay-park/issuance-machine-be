package com.ictk.issuance.service.workinfo;

import com.ictk.issuance.common.exception.IctkException;
import com.ictk.issuance.data.dto.machine.MachineIdListDTO;
import com.ictk.issuance.data.dto.workinfo.*;

public interface WorkInfoService {

    // Work Info 테이블 생성하기
    String createWorkInfoTable();

    // Work Info 단일 정보 조회 서비스
    WorkInfoSearchDTO.WorkInfoSearchRSB searchWorkInfo(String trId, WorkInfoSearchDTO.WorkInfoSearchRQB workInfoSearchRQB) throws IctkException;

    // Work Info 목록 조회 서비스
    WorkInfoListDTO.WorkInfoListRSB fetchWorkInfoList(String trId, WorkInfoListDTO.WorkInfoListRQB workInfoListRQB) throws IctkException;

    // Work Info 추가/변경 서비스
    WorkInfoSaveDTO.WorkInfoSaveRSB saveWorkInfo(String trId, WorkInfoSaveDTO.WorkInfoSaveRQB workInfoSaveRQB) throws IctkException;

    // Work Info 삭제 서비스
    WorkInfoDeleteDTO.WorkInfoDeleteRSB deleteWorkInfo(String trId, WorkInfoDeleteDTO.WorkInfoDeleteRQB workInfoDeleteRQB) throws IctkException;

    // Work Info Id 목록 조회
    WorkIdListDTO.WorkIdListRSB workIdsList(String trId) throws IctkException;

    // 작업제어 명령
    WorkControlDTO.WorkControlRSB controlWork(String trId, WorkControlDTO.WorkControlRQB workControlRQB) throws IctkException;

}