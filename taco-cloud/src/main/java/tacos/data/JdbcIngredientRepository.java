package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import tacos.Ingredient;

@Repository			//Controller와 Component를 정의하는 스테레오타입 어노테이션
					//스프링 컴포넌트 검색에서 이 클래스를 자동으로 찾아서 스프링 애플리케이션컨텍스트의 빈으로 생성해준다.
public class JdbcIngredientRepository implements IngredientRepository {

	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcIngredientRepository(JdbcTemplate jdbc) {	//JdbcIngre...Repo... 빈이 생성되면 @Autowired 어노테이션을 통하여 스프링이 해당 빈을 JdbcTemplate에 연결한다. 
		this.jdbc = jdbc;
	}
	
	@Override
	public Iterable<Ingredient> findAll(){
		return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient);		//jdbc query 메서드
	}
	
	//query 메서드는 2개의 인자를 받는다. 첫번째는 수행할 쿼리 명령어 두번째는 스프링의 RowMapper 인터페이스를 구현한 mapRowToIngredient 메서드
	
	@Override
	public Ingredient findById(String id) {
		return jdbc.queryForObject("select id, name, type from Ingrdient where id=?", this::mapRowToIngredient,id);
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
		return new Ingredient(
				rs.getString("id"), 
				rs.getString("bane"), 
				Ingredient.Type.valueOf(rs.getString("type")));
	}
	
	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbc.update(			//save 메서드의 인자로 전달되는 식자재 객체의 id, name, type 속성의 값이 각 매개변수에 지정
				"insert into Ingredient (id, name, type) value (?, ?, ?)",
				ingredient.getId(),
				ingredient.getName(),
				ingredient.getType().toString());
		return ingredient;
	}
}
