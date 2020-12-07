package top.freshgeek.springcloud.payment.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chen.chao
 */
@RestController
public class SentinelTestController {

	@GetMapping("/testHotKey")
	@SentinelResource(value = "other", blockHandler = "fallback")
	public String testHotKey(@RequestParam("pid") String pid, @RequestParam("uid") String uid) {
		return "hot key";
	}

	public String fallback(String pid, String uid, BlockException blockException) {
		return "hot key,fallback,pid:" + pid + ",uid:" + uid;
	}


}
