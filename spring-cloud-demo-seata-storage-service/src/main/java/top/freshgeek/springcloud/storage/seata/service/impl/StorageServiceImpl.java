package top.freshgeek.springcloud.storage.seata.service.impl;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.freshgeek.springcloud.storage.entity.Storage;
import top.freshgeek.springcloud.storage.seata.dao.StorageRepository;
import top.freshgeek.springcloud.storage.seata.service.StorageService;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {


    @Resource
    private StorageRepository storageRepository;

    /**
     * 扣减库存
     */
    @Override
    @Transactional
    public void decrease(Long productId, Integer count) {
        log.info("------->storage-service中扣减库存开始");
        Optional<Storage> byId =
                storageRepository.findById(productId);
        Assert.isTrue(byId.isPresent());
        Storage storage = byId.get();
        storage.setTotal(storage.getTotal() - count);
        storageRepository.save(storage);
        log.info("------->storage-service中扣减库存结束");
    }
}