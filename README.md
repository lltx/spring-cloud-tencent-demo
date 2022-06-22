## Spring Cloud Tencent 是什么

Spring Cloud Tencent 是腾讯开源的一站式微服务解决方案。Spring Cloud Tencent 实现了 Spring Cloud 标准微服务 SPI，开发者可以基于 Spring Cloud Tencent 快速开发 Spring Cloud 微服务架构应用。Spring Cloud Tencent 的核心依托腾讯开源的一站式服务发现与治理平台 Polarismesh ，实现各种分布式微服务场景。

Spring Cloud Tencent 提供的能力包括但不限于：

![架构图](https://minio.pigx.vip/oss/1655809074.jpg)

## 一、安装北极星

北极星是腾讯开源的服务发现和治理中心，致力于解决分布式或者微服务架构中的服务可见、故障容错、流量控制和安全问题。虽然，业界已经有些组件可以解决其中一部分问题，但是缺少一个标准的、多语言的、框架无关的实现。

腾讯具有海量的分布式服务，加上业务线和技术栈的多样性，沉淀了大大小小数十个相关组件。从 2019 年开始，我们通过北极星对这些组件进行抽象和整合，打造公司统一的服务发现和治理方案，帮助业务提升研发效率和运营质量。

> > [北极星安装非常的简单下载响应平台的 zip 直接运行即可](https://github.com/polarismesh/polaris/releases/tag/v1.9.0 "北极星下载")

![北极星界面展示](https://minio.pigx.vip/oss/1655809558.png)

## 二、服务注册与发现

- 服务增加 polaris-discovery 依赖

```xml
<dependency>
    <groupId>com.tencent.cloud</groupId>
    <artifactId>spring-cloud-starter-tencent-polaris-discovery</artifactId>
</dependency>
```

- application.yaml 接入 polaris server

```yaml
spring:
  cloud:
    polaris:
      address: grpc://127.0.0.1:8091
```

- 启动服务观察 polaris console

![](https://minio.pigx.vip/oss/1655809927.png)

- 服务调用示例

```java
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
  return new RestTemplate();
}

@Autowired
private RestTemplate restTemplate;

@GetMapping("/consumer")
public String consumer() {
  return restTemplate.getForObject("http://lengleng-tencent-discovery-provider/provider/lengleng", String.class);
}
```

## 三、 配置管理

> > 在应用启动 Bootstrap 阶段，Spring Cloud 会调用 PolarisConfigFileLocator 从 Polaris 服务端获取配置文件并加载到 Spring 上下文里。通过 Spring Boot 标准的 @Value，@ConfigurationProperties 注解即可获取配置内容。动态配置刷新能力，则通过 Spring Cloud 标准的 @RefreshScope 机制实现。

- 服务增加 polaris-config 依赖

```xml
<dependency>
    <groupId>com.tencent.cloud</groupId>
    <artifactId>spring-cloud-starter-tencent-polaris-config</artifactId>
</dependency>
```

- bootstrap.yaml 接入 polaris-config

```yaml
spring:
  cloud:
    polaris:
      address: grpc://127.0.0.1:8081
      config:
        groups:
          - name: ${spring.application.name}
            files: "application"

```

> 特别注意: 这里需要配置在 bootstrap， spring-cloud-tencent 未适配 spring boot 最新的文件加载机制

- 北极星控制台增加配置

![](https://minio.pigx.vip/oss/1655810261.png)

![](https://minio.pigx.vip/oss/1655810312.png)

- 代码使用配置

```
@Value("${name:}")
private String name;
```

## 四、服务限流

> > 服务限流是最常见的一种服务自我保护措施之一，防止流量洪峰打垮服务。Spring Cloud Tencent Rate Limit 模块内置了针对 Spring Web 和 Spring WebFlux 场景的限流 Filter，结合 Polaris 的限流功能帮忙业务快速接入限流能力。

- 服务增加 polaris-ratelimit 依赖

> 使用限流组件时添加 discovery ,方便在服务列表编辑

```xml
<dependency>
    <groupId>com.tencent.cloud</groupId>
    <artifactId>spring-cloud-starter-tencent-polaris-discovery</artifactId>
</dependency>
<dependency>
    <groupId>com.tencent.cloud</groupId>
    <artifactId>spring-cloud-starter-tencent-polaris-ratelimit</artifactId>
</dependency>
```

- 服务接入 polaris-ratelimit

```yaml
spring:
  cloud:
    polaris:
      address: grpc://127.0.0.1:8091
      namespace: default
      ratelimit:
        reject-http-code: 403
        reject-request-tips: "lengleng test rate limit"
```

- 北极星控制台增加限流规则

![](https://minio.pigx.vip/oss/1655810598.png)

## 五、服务路由

> > polaris 能够实现的路由形式较多元数据路由、就近路由、规则路由、自定义路由等形式，本文以元数据路由演示，如下图只会路由至相同元数据信息的服务

![](https://minio.pigx.vip/oss/1655811097.jpg)

- 服务增加 polaris-router 依赖

```
<dependency>
    <groupId>com.tencent.cloud</groupId>
    <artifactId>spring-cloud-starter-tencent-polaris-router</artifactId>
</dependency>
```

- 服务标记元数据

```yaml
spring:
  cloud:
    polaris:
      address: grpc://127.0.0.1:8091
    tencent:
      metadata:
        content:
          version: local
```

## 六、限流熔断

> > 故障实例熔断是常见的一种容错保护机制。故障实例熔断能实现主调方迅速自动屏蔽错误率高或故障的服务实例，并启动定时任务对熔断实例进行探活。在达到恢复条件后对其进行半开恢复。在半开恢复后，释放少量请求去进行真实业务探测。并根据真实业务探测结果去判断是否完全恢复正常。

![](https://minio.pigx.vip/oss/1655811440.jpg)

- 添加限流熔断相关的依赖 polaris-circuitbreaker

```xml
<dependency>
    <groupId>com.tencent.cloud</groupId>
    <artifactId>spring-cloud-starter-tencent-polaris-circuitbreaker</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>

<dependency>
    <groupId>com.tencent.cloud</groupId>
    <artifactId>spring-cloud-starter-tencent-polaris-discovery</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-circuitbreaker-spring-retry</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

- 提供 Feign 服务调用实现

> spring-cloud-tencent 当前版本仅支持 feign 熔断

```java
@FeignClient(contextId = "demoFeign", value = "lengleng-circuitbreaker-tencent-circuitbreaker-provider",
		fallback = DemoFeignFallback.class)
public interface DemoFeign {
	@GetMapping("/provider")
	String get(@RequestParam String name);

}
```

- 服务接入 polaris-circuitbreaker

```yaml
spring:
  cloud:
    polaris:
      address: grpc://127.0.0.1:8091

#开启断路器
feign:
  circuitbreaker:
    enabled: true

```

- 编写熔断规则 polaris.yml

```yaml
consumer:
  circuitBreaker:
    checkPeriod: 100ms
    chain:
      - errorCount
      - errorRate
    plugin:
      errorCount:
        continuousErrorThreshold: 1
        metricNumBuckets: 1
      errorRate:
        errorRateThreshold: 100
        metricStatTimeWindow: 1s
        requestVolumeThreshold: 1
```

> > 全文配套源码: [https://github.com/lltx/spring-cloud-tencent-demo](https://github.com/lltx/spring-cloud-tencent-demo "spring-cloud-tencent-demo")
