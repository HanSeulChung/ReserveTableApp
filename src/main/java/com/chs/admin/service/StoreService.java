package com.chs.admin.service;

import com.chs.admin.dto.StoreDto;
import com.chs.admin.dto.StoreInput;

public interface StoreService {

    /**
     * 가게 등록
     */
    StoreDto registerStore(StoreInput parameter);
}
