package bookstore.Service;

import bookstore.Entity.Category;
import bookstore.Mapper.CategoryMapper;
import bookstore.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    public Category getByName(String name){
        return categoryRepository.findByName(name);
    }
    public Object getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toCategoryDTO).collect(Collectors.toList());
    }
}
