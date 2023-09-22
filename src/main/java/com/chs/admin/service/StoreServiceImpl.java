package com.chs.admin.service;

import com.chs.admin.dto.StoreDto;
import com.chs.admin.dto.StoreInput;
import com.chs.admin.entity.Store;
import com.chs.admin.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service

public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;

    @Override
    public StoreDto registerStore(StoreInput parameter) {
        boolean exist = storeRepository.existsByStoreNameAndAddrDetail(parameter.getStoreName(), parameter.getAddrDetail());
        if (exist) {
            throw new RuntimeException("이미 있는 가게 입니다.");
        }
        StoreDto storeDtoResult = StoreDto.fromInput(parameter);
        storeRepository.save(
                Store.toEntity(storeDtoResult)
        );
        return storeDtoResult;
    }
}
