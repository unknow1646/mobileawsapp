package com.example.mobilewebws;

import com.example.mobilewebws.configuration.MyBean;
import com.example.mobilewebws.security.AppProperties;
import com.example.mobilewebws.servicebean.BeanAImpl;
import com.example.mobilewebws.servicebean.BeanBImpl;
import com.example.mobilewebws.servicebean.BeanService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MobileWebWsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MobileWebWsApplication.class, args);
		BeanService beanService = context.getBean(BeanAImpl.class);
		beanService.startBean();
		beanService.stopBean();

		// PRIMERA FORMA
		/*BeanService beanService = (BeanService) SpringApplicationContext.getBean("beanAImpl");
		beanService.startBean();
		beanService.stopBean();

		 */
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}

	@Bean(name = "AppProperties")
	public AppProperties getAppProperties(){
		return new AppProperties();
	}

	@Bean
	@Qualifier("A")
	public BeanService beanAServiceBean(){
		return new BeanAImpl();
	}

	@Bean
	@Qualifier("B")
	public BeanService beanBServiceBean(){
		return new BeanBImpl();
	}
}
