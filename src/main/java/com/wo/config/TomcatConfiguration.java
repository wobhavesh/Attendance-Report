package com.wo.config;
import org.apache.catalina.Context;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "tomcat.staticResourceCustomizer.enabled", matchIfMissing = true)
@EnableConfigurationProperties(ServerProperties.class)
public class TomcatConfiguration {
	@Bean
	public ServletWebServerFactoryCustomizer staticResourceCustomizer() {

		ServerProperties serverProperties = null;
		return new ServletWebServerFactoryCustomizer(serverProperties) {
			@Override
			public void customize(ConfigurableServletWebServerFactory container) {
				System.out.println("container" + container.toString());
				if (container instanceof TomcatServletWebServerFactory) {
					System.out.println("TomcatServletWebServerFactory");
					((TomcatServletWebServerFactory) container).addContextCustomizers(new TomcatContextCustomizer() {
						@Override
						public void customize(Context context) {
							System.out.println("Customizing Tomcat");
							context.addLifecycleListener(new StaticResourceConfigurer(context));
						}
					});
				}
			}
		};
	}
}