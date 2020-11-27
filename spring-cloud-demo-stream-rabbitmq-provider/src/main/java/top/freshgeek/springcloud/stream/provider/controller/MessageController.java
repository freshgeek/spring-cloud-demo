package top.freshgeek.springcloud.stream.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.freshgeek.springcloud.stream.provider.service.MessageProvider;

import javax.annotation.Resource;

/**
 * @author chen.chao
 */
@RestController
public class MessageController {

	@Resource
	private MessageProvider messageProvider;


	@GetMapping("/send")
	public String send() {
		return messageProvider.send();
	}

}
