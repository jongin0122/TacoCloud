package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

import tacos.data.UserRepository;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.Order;
import tacos.Taco;
import tacos.User;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import javax.validation.Valid;
import org.springframework.validation.Errors;

@Slf4j			// Logger를 생성한다 (Simple Logging Facade
@Controller
@RequestMapping("/design")			//해당 컨트롤러가 처리하는 요청의 종류를 나타낸다. 즉 DesignTacoController에서 /design으로 시작하는 경로의 요청을 처리한다. 
@SessionAttributes("order")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo;
	
	private TacoRepository tacoRepo;
	private UserRepository userRepo;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo, UserRepository userRepo) {
		this.ingredientRepo = ingredientRepo;
		this.tacoRepo = tacoRepo;
		this.userRepo = userRepo;
		//생성자에서 위의 두개의 객체 모두를 인자로 받는다. 그리고 showDesignForm()과 processDesign() 메서드에서 사용할 수 있도록 두 인자 모두 인스턴스 변수에 지정한다.
	}
	
						//예전에는 @ReqeustMapping(method=RequestMethod.GET) 으로 표기하였다. 
	@GetMapping			// /design의 HTTP GET 요청이 수신될 때 그 요청을 처리하기 위해 showDesignForm() 메서드가 호출됨을 나타낸다. 
	public String showDesignForm(Model model, Principal principal) {
			
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i->ingredients.add(i));	//이 findAll()메소드를 showDesignForm() 메서드에서 호출
		
		Type[] types = Ingredient.Type.values();
		for (Type type:types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients,type));
		}
		
		/*
		 * model.addAttribute("taco", new Taco()); //Model은 컨트롤러와 데이터를 보여주는 뷰 사이에서 데이터를
		 * 운반하는 객체이다. //궁극적으로 Model 객체의 속성에 있는 데이터는 뷰가 알 수 있는 서블릿요청 속성들로 복사된다.
		 */		
		
		String username= principal.getName();
		User user = userRepo.findByUsername(username);
		model.addAttribute("user", user);
		
		return "design";		//design 뷰 반환
	}
	
	@ModelAttribute(name = "order")		//Order객체가 모델에 생성되도록 해준다.
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	@PostMapping		//브라우저에서 폼이 제출되면 브라우저가 폼의 모든 데이터를 모아서 폼에 나타난 GET요청과 같은 경로(/design)로 서버에 HTTP POST 요청을 전송한다.
	public String processDisign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {			//폼에서 전달하는 Taco 객체의 속성과 바인딩
		if(errors.hasErrors()) {		//@Valid 어노테이션은 Taco 객체의 유효성 검사를 수행하라고 MVC에 알려준다.
			return "design";		//에러가 있으면 다시 design 뷰를 반환
		}
		
		Taco saved=tacoRepo.save(design);
		order.addDesign(saved);
		
		return "redirect:/orders/current";		//리디렉션은 변경된 경로로 재접속을 나타낸다 즉, 
												//processDesign()의 실행이 끝난 후 사용자의 브라우저가 /order/current 상대경로로 재접속 되어야 한다는 것을 나타낸다.
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type){
		return ingredients.parallelStream().filter(x-> x.getType().equals(type)).collect(Collectors.toList());
	}

}
