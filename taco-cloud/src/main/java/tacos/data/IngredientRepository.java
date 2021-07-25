package tacos.data;

import tacos.Ingredient;

import org.springframework.data.repository.CrudRepository; // 스프링데이터에서는 CrudRepository 인터페이스를 확장extends 할 수 있다.

//CrudRepositoy 인터페이스에는 데이터베이스의 CRUD 연산을 위한 많은 메서드가 선언되어있다.

public interface IngredientRepository extends CrudRepository<Ingredient, String>{ // 첫번째 매게변수는 리퍼지터리에 저장되는 개체 타입 두번째는 ID 속성의 타입이다.
	
}
