package tacos.web;		//OrderController의 pageSize 속성의 경우는 이 속성을 별개의 홀더 클래스로 추출

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix="taco.orders")
@Data
@Validated
public class OrderProps {
	
	@Min(value=5, message="must be between 5 and 25")
	@Max(value=25, message="must be between 5 and 25")
	private int pageSize = 20;
}