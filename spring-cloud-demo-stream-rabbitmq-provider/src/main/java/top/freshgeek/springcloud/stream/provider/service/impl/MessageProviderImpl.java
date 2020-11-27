package top.freshgeek.springcloud.stream.provider.service.impl;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import top.freshgeek.springcloud.stream.provider.service.MessageProvider;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author chen.chao
 */
@EnableBinding(Source.class)
public class MessageProviderImpl implements MessageProvider {

	@Resource
	private MessageChannel output;

	@Override
	public String send() {
		String s = UUID.randomUUID().toString();
		output.send(MessageBuilder.withPayload(s).build());
		System.out.println("发送:" + s);
		return s;
	}
}
