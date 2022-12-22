package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return CategoryMapper.INSTANCE.map(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(categoryRepository.findCategoryByName(name).orElse(null));
    }
}
