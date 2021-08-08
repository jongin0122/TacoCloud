package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.User;
import javax.validation.Valid;
import org.springframework.validation.Errors;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/orders")			// /orders로 시작되는 경로의 요청 처리 메서드가 처리한다는 것을 알려주는 클래스 수준의 어노테이션이다.
public class OrderController {
	
	private OrderRepository orderRepo;
	
	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}
	
									//예전에는 @ReqeustMapping(method=RequestMethod.GET) 으로 표기하였다.
	@GetMapping("/current")			//@GetMapping을 함께 지정하여 /orders/current 경로의 HTTP GET 요청을 orderForm()메서드가 처리한다
	public String orderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order) {
		if (order.getDeliveryName() == null) {
			order.setDeliveryName(user.getFullname());
		}
		if (order.getDeliveryStreet() == null) {
			order.setDeliveryStreet(user.getStreet());
		}
		if (order.getDeliveryCity() == null) {
			order.setDeliveryCity(user.getCity());
		}
		if (order.getDeliveryState() == null) {
			order.setDeliveryState(user.getState());
		}
		if (order.getDeliveryZip() == null) {
			order.setDeliveryZip(user.getZip());
		}
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
		
		order.setUser(user);
		
		orderRepo.save(order);
		sessionStatus.setComplete();
		
		return "redirect:/";
	}
}
