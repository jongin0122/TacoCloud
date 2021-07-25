package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//WebConfig는 뷰 컨트롤러의 역할을 수행하는 구성 클래스이며 WebMvcConfigurer 인터페이스를 구성한다.
//WebMvcConfigurer 인터페이스는 스프링 MVC를 구성하는 메서드를 정의하고 있다.

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		//하나 이상의 뷰 컨트롤러를 등록하기 위해 사용할 수 있는 ViewControllerRegistry를 인자로 바든ㄴ다.
		//여기서 우리의 뷰 컨트롤러가 GET요청을 처리하는 경로인 "/"를 인자로 전달하여 addViewController()를 호출한다.
	}

}
