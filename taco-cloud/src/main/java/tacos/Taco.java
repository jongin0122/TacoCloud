package tacos;

import java.util.List;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

import javax.validation.constraints.NotNull; //유효성 검사 어노테이션
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
public class Taco {
	
	@Id			//id속성에는 데이터베이스가 자동으로 생성해 주는 ID값이 사용
	@GeneratedValue(strategy=GenerationType.AUTO)		//strategy 속성의 값이 GenerationType.AUTO로 설정
	private Long id;
	
	private Date createdAt;
	
	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;
	
	@ManyToMany(targetEntity=Ingredient.class)		//ingredients들 간의 관계를 선언하기 위해 사용
													//하나의 Taco 객체는 많은 Ingredient 객체를 가질 수 있는데, 하나의 Ingredient는 여러  Taco 객체에 포함될 수 있기 때문이다.
	@Size(min=1, message="you must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
	
	@PrePersist			//Taco 객체가 저장되기 전에 createdAt 속성을 현재 일자와 시간으로 설정하는 데 사용된다.
	void createdAt() {
		this.createdAt = new Date();
	}
}
