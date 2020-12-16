package top.freshgeek.springcloud.storage.entity.feign.impl;

import top.freshgeek.springcloud.common.dto.CommonResult;
import top.freshgeek.springcloud.storage.entity.feign.StorageFeignService;

public class StorageFeignServiceImpl implements StorageFeignService {

    @Override
    public CommonResult decrease(Long productId, Integer count) {
        return null;
    }
}
