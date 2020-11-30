
## sleuth 请求链路

搭配zipkin 使用 , 使用docker 运行启动

```shell script
docker run -p 9411:9411 openzipkin/zipkin:2.17.2
```

然后在微服务(需要记录的都要引入,其实可以放入公共模块)中引入pom
```xml
   <!--包含了sleuth+zipkin-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>

```


然后配置微服务把请求数据发到zipkin
```yaml
spring:
    # zipkin+sleuth 请求链路监控
  zipkin:
    base-url: http://local:9411
  sleuth:
    sampler:
      #采样率值介于 0 到 1 之间，1 则表示全部采集
      probability: 1
```
