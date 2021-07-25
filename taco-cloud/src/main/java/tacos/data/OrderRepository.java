package tacos.data;

import tacos.Order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
	
}



//애플리케이션이 시작될 때 스프링 데이터 JPA가 각 인터페이스 구현체(클래스 등)를 자동으로 생성해 준다.
//이것은 리퍼지터리들이 애당초 사용할 준비가 되어있다는 것을 의미한다. 
//JDBC 기반의 구현에서 했던 것처럼 그것들을 컨트롤러에 주입만 하면 된다.