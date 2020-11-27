package top.freshgeek.springcloud.stream.consumer.listerner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author chen.chao
 */
@Component
@EnableBinding(Sink.class)
public class ReceiveMessageListener {

	@Value("${server.port}")
	private String severPort;

	@StreamListener(Sink.INPUT)
	public void input(Message<String> message) {
		System.out.println("port" + severPort + "，----------->接收到的消息：" + message.getPayload());
	}
}
