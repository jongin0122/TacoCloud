package tacos;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Data; //Getters와 Setters 런타임시 자동 생성 라이브러리
import lombok.RequiredArgsConstructor;

@Data //lombok 어노테이션 사용
@RequiredArgsConstructor		//@NoArg... 어노테이션이 인자있는 생성자를 제거하지만 ,
								//@Requi... 어노테이션 때문에 private의 인자 없는 생성자와 더불어 인자가 있는 생성자를 여전히 가질 수 있다. 
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)	//PRIVATE --> 클래스 외부에서 사용하지 못하게 했다.
@Entity			//Ingredient를 JAP개체로 선언
public class Ingredient {
	
	@Id			//데이터베이스의 개체를 고유하게 식별한다는 것을 나타냄
	private final String id;
	private final String name;
	private final Type type;
	
	public static enum Type{
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}

}
