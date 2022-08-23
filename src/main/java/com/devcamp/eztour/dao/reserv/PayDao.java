package com.devcamp.eztour.dao.reserv;

import com.devcamp.eztour.domain.reserv.PayDto;

import java.util.Map;

public interface PayDao {
    PayDto selectPay(String rsvt_no) throws Exception;

    String selectPayStatus(Map<String, String> map)throws Exception;

    int insertPay(PayDto payDto) throws Exception;

    int deletePayAdmin() throws Exception;
}
