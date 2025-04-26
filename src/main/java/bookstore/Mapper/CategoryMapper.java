package bookstore.Mapper;

import bookstore.DTO.Category.CategoryDTO;
import bookstore.Entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }
}
