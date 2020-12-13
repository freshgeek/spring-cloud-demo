package top.freshgeek.springcloud.storage.seata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.freshgeek.springcloud.common.payment.CommonResult;
import top.freshgeek.springcloud.storage.entity.feign.StorageFeignService;
import top.freshgeek.springcloud.storage.seata.service.StorageService;

@RestController
public class StorageController implements StorageFeignService {

    @Autowired
    private StorageService storageService;

    /**
     * 扣减库存
     *
     * @return
     */
    @PostMapping("/storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count) {
        storageService.decrease(productId, count);
        return CommonResult.of(200, "扣减库存成功！");
    }
}