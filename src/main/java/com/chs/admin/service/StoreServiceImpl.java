package com.chs.admin.service;

import com.chs.admin.dto.StoreDto;
import com.chs.admin.dto.StoreEditInput;
import com.chs.admin.dto.StoreInput;
import com.chs.admin.entity.Store;
import com.chs.admin.repository.StoreRepository;
import com.chs.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;

    @Override
    public StoreDto registerStore(StoreInput parameter, String ownerId) {
        boolean exist = storeRepository.existsByStoreNameAndAddrDetail(parameter.getStoreName(), parameter.getAddrDetail());
        if (exist) {
            throw new RuntimeException("이미 있는 가게 입니다.");
        }

        Store storeEntity = Store.toEntity(StoreDto.fromInput(parameter, ownerId));
        storeRepository.save(storeEntity);
        return StoreDto.of(storeEntity);
    }

    @Override
    public List<StoreDto> readStore(String ownerId) {
        return StoreDto.of(storeRepository.findAllByOwnerId(ownerId));
    }

    @Override
    public Page<Store> getAllStore(Pageable pageable) {
        return this.storeRepository.findAll(pageable);
    }

    @Override
    public List<StoreDto> getStoreByStoreName(String storeName) {
        return StoreDto.of(storeRepository.findAllByStoreName(storeName));
    }

    @Override
    public List<StoreDto> getStoreByStoreAddr(String storeAddr) {
        return StoreDto.of(storeRepository.findAllByAddr(storeAddr));
    }

    @Override
    public void deleteStore(Long storeId, String storeName) {
        storeRepository.deleteAllByIdAndStoreName(storeId, storeName);
    }

    @Override
    public StoreDto updateStore(StoreEditInput parameter) {
        Optional<Store> store = storeRepository.findById(parameter.getStoreId());
        Store storeEntity = store.get();

        StoreDto storeDto = StoreDto.of(storeEntity);
        storeDto.setStoreName(parameter.getStoreName());
        storeDto.setPhone(parameter.getPhone());
        storeDto.setAddr(parameter.getAddr());
        storeDto.setAddrDetail(parameter.getAddrDetail());
        storeDto.setUdtDt(LocalDateTime.now());
        storeDto.setDescription(parameter.getDescription());

        storeRepository.save(Store.toEntity(storeDto));
        return storeDto;
    }
}
