package top.freshgeek.springcloud.storage.entity.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.freshgeek.springcloud.common.MiscroServiceNameConstants;
import top.freshgeek.springcloud.common.dto.CommonResult;
import top.freshgeek.springcloud.storage.entity.feign.impl.StorageFeignServiceImpl;

@FeignClient(value = MiscroServiceNameConstants.STORAGE_SERVICE, fallback = StorageFeignServiceImpl.class)
public interface StorageFeignService {

	/***
	 *  扣减库存
	 * @param productId 产品id
	 * @param count 数量
	 * @return 成功状态
	 */
	@PostMapping("/storage/decrease")
	CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
