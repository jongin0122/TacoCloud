package tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {
	private JdbcTemplate jdbc;
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);	//식자제를 저장하는 saveTacoInfo() 메서드 호출하여 tacoId에 반환
		taco.setId(tacoId);
		for (Ingredient ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);		//반환된 타코ID를 사용해서 타코와 식자재의 연관 정보를 저장하는 saveIngredientToTaco()를 호출
		}
		return taco;
	}
	
	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreator psc =
				new PreparedStatementCreatorFactory(
						"insert into Taco (name, createdAt) values (?, ?)",		//실행할 SQL 명령
						Types.VARCHAR, Types.TIMESTAMP		//각 쿼리 매게변수의 타입을 인자로 전달
						).newPreparedStatementCreator(
								Arrays.asList(
										taco.getName(),
										new Timestamp(taco.getCreatedAt().getTime())));
		
		KeyHolder keyHolder = new GeneratedKeyHolder();		//생성된 타코 ID를 제공
		jdbc.update(psc, keyHolder);		// PreparedStatementCreator 객체와 KeyHolder 객체를 인자로 받는다.
		return keyHolder.getKey().longValue();		//update() 실행이 끝나면 keyHolder.getKey().longValue() 연속 호출로 타코 ID반환 
	}
	
	private void saveIngredientToTaco(
			Ingredient ingredient, long tacoId) {
		jdbc.update(
				"insert into Taco_Ingredients (taco, ingredient) " + "values (?, ?)",
				tacoId, ingredient.getId());
	}
}