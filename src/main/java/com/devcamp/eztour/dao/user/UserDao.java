package com.devcamp.eztour.dao.user;

import com.devcamp.eztour.domain.user.UserDto;

public interface UserDao {
    int insertUser(UserDto user) throws Exception;

    //int insertUserHistory(UserDto user) throws Exception;

}
