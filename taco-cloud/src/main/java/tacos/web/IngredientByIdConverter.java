package tacos.web;

//데이터베이스의 식자재 데이터를 Ingredient 객체로 변환하기

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.Optional;

@Component
public class IngredientByIdConverter
		implements Converter<String, Ingredient> {
	private IngredientRepository ingredientRepo;
	
	@Autowired
	public IngredientByIdConverter(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	@Override
	public Ingredient convert(String id) {
		Optional<Ingredient> optionalIngredient = ingredientRepo.findById(id);
		return optionalIngredient.isPresent() ? optionalIngredient.get() : null;
		//데이터베이스에서 식자재를 찾지 못했을 때 null이 반환
	}
}
