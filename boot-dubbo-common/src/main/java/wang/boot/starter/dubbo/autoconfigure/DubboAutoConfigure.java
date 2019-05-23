package wang.boot.starter.dubbo.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Administrator
 */
@Configuration
@ImportResource({"classpath:dubbo.xml","classpath:dubbo-provider.xml","classpath:dubbo-consumer.xml"})
public class DubboAutoConfigure {
}
