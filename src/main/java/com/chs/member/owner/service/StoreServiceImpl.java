package com.chs.member.owner.service;

import com.chs.exception.Impl.NoStoreException;
import com.chs.exception.Impl.UnmatchOwnerStoreException;
import com.chs.exception.Impl.UnmatchStoreIdAndNameException;
import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.dto.StoreInput;
import com.chs.member.owner.entity.Store;
import com.chs.member.owner.repository.StoreRepository;
import com.chs.member.owner.dto.StoreEditInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        return StoreDto.of(storeRepository.findAllByOwner_UserId(ownerId));
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
    public List<StoreDto> getStoreByOwnerId(String ownerId) {
        return StoreDto.of(storeRepository.findAllByOwner_UserId(ownerId));
    }

    @Override
    public StoreDto getStoreByOwnerIdAndStoreId(String ownerId, Long storeId) {
        return null;
    }

    @Override
    public void deleteStore(Long storeId, String storeName, String ownerId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NoStoreException());

        if (!store.getOwnerId().equals(ownerId)) {
            throw new UnmatchOwnerStoreException();
        }
        if (!store.getStoreName().equals(storeName)) {
            throw new UnmatchStoreIdAndNameException();
        }
        storeRepository.deleteAllByIdAndStoreName(storeId, storeName);
    }

    @Override
    public StoreDto updateStore(StoreEditInput parameter, String ownerId) {
        Store store = storeRepository.findById(parameter.getStoreId())
                .orElseThrow(() -> new NoStoreException());

        if (!store.getOwnerId().equals(ownerId)) {
            throw new UnmatchOwnerStoreException();
        }

        return StoreDto.of(storeRepository.save(
                Store.builder()
                        .id(store.getId())
                        .storeName(parameter.getStoreName())
                        .addr(parameter.getAddr())
                        .addrDetail(parameter.getAddrDetail())
                        .description(parameter.getDescription())
                        .phone(parameter.getPhone())
                        .regDt(store.getRegDt())
                        .udtDt(LocalDateTime.now())
                        .ownerId(store.getOwnerId())
                        .build()
        ));
    }
}
