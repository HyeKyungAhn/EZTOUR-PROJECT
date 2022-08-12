package com.devcamp.eztour.service;

import com.devcamp.eztour.dao.reserv.ReservDao;
import com.devcamp.eztour.domain.reserv.ReservInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservServiceImpl implements ReservService {
    @Autowired
    ReservDao reservDao;

    @Override
    public ReservInfoDto readPrdInfo(String prdDtlCd) throws Exception{
        return reservDao.selectReservPrdInfo(prdDtlCd);
    }
}
