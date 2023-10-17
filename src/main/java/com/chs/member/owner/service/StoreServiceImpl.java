package com.chs.member.owner.service;

import com.chs.exception.Impl.*;
import com.chs.member.owner.dto.StoreDto;
import com.chs.member.owner.dto.StoreInput;
import com.chs.member.owner.entity.Owner;
import com.chs.member.owner.entity.Store;
import com.chs.member.owner.repository.OwnerRepository;
import com.chs.member.owner.repository.StoreRepository;
import com.chs.member.owner.dto.StoreEditInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;

    // 타임리프용
    public List<String> getAutoCompleteResultsByName(String query) {
        // 가게 이름에서 검색어(query)를 포함하는 가게들을 찾아서 반환
        List<Store> stores = storeRepository.findByStoreNameContaining(query);
        return stores.stream()
                .map(Store::getStoreName)
                .collect(Collectors.toList());
    }

    public List<String> getAutoCompleteResultsByAddress(String query) {
        // 가게 주소에서 검색어(query)를 포함하는 가게들을 찾아서 반환
        List<Store> stores = storeRepository.findByAddrContaining(query);
        return stores.stream()
                .map(Store::getAddr)
                .collect(Collectors.toList());
    }
    @Override
    public boolean add(StoreInput parameter, String ownerId) {

        boolean storeExist = storeRepository.existsByStoreNameAndAddrDetail(parameter.getStoreName(), parameter.getAddrDetail());
        if (storeExist) {
            throw new AlreadyExistStoreException();
        }

        Owner owner = ownerRepository.findByUserId(ownerId)
                .orElseThrow(() -> new NoUserIdException());

        StoreDto storeDto = StoreDto.fromInput(parameter);
        Store store = Store.toEntity(storeDto);
        store.setOwner(owner);
        storeRepository.save(store);

        return true;
    }

    @Override
    public boolean edit(StoreInput parameter) {
        Optional<Store> optionalStore = storeRepository.findById(parameter.getId());

        if (!optionalStore.isPresent()) {
            //수정할 데이터가 없음
            throw new NoStoreException();
        }
        Store store =optionalStore.get();
        storeRepository.save(
                Store.builder()
                        .id(store.getId())
                        .storeName(parameter.getStoreName())
                        .addr(parameter.getAddr())
                        .addrDetail(parameter.getAddrDetail())
                        .description(parameter.getDescription())
                        .phone(parameter.getPhone())
                        .regDt(store.getRegDt())
                        .udtDt(LocalDateTime.now())
                        .owner(store.getOwner())
                        .build()
        );

        return true;
    }

    @Override
    public boolean delete(String idList) {
        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                id = Long.parseLong(x);
                try {

                } catch (Exception e) {

                }
                if (id > 0) {
                    storeRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public StoreDto getById(long id) {
        return storeRepository.findById(id).map(StoreDto::of).orElseThrow(() -> new NoStoreException());
    }

    @Override
    public List<StoreDto> getAllStores(String ownerId) {
        return StoreDto.of(storeRepository.findAllByOwner_UserId(ownerId));
    }

    @Override
    public StoreDto registerStore(StoreInput parameter, String ownerId) {
        boolean storeExist = storeRepository.existsByStoreNameAndAddrDetail(parameter.getStoreName(), parameter.getAddrDetail());
        if (storeExist) {
            throw new AlreadyExistStoreException();
        }

        Owner owner = ownerRepository.findByUserId(ownerId)
                .orElseThrow(() -> new NoUserIdException());



        Store storeEntity = Store.toEntity(StoreDto.fromInput(parameter));
        storeEntity.setOwner(owner);
        storeRepository.save(storeEntity);
        return StoreDto.of(storeEntity);
    }

    @Override
    public List<StoreDto> readStore(String ownerId) {
        return StoreDto.of(storeRepository.findAllByOwner_UserId(ownerId));
    }

    @Override
    public Page<StoreDto> getAllStore(Pageable pageable) {
        return StoreDto.toDto(this.storeRepository.findAll(pageable));
    }

    @Override
    public List<StoreDto> getAllStore() {
        return StoreDto.of(this.storeRepository.findAll());
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

        if (!store.getOwner().getUserId().equals(ownerId)) {
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

        if (!store.getOwner().getUserId().equals(ownerId)) {
            throw new UnmatchOwnerStoreException();
        }
        Owner owner = ownerRepository.findByUserId(ownerId)
                        .orElseThrow(() -> new NoUserIdException());

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
                        .owner(owner)
                        .build()
        ));
    }
}
