package creatip.restaurant.service;

import static org.assertj.core.api.Assertions.*;

import creatip.restaurant.IntegrationTest;
import creatip.restaurant.domain.Category;
import creatip.restaurant.service.dto.CategoryDTO;
import creatip.restaurant.service.mapper.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class CategoryServiceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private Category category;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    CategoryMapper categoryMapper;

    public static Category createEntity() {
        Category category = new Category().code(DEFAULT_CODE).name(DEFAULT_NAME).status(DEFAULT_STATUS);
        return category;
    }

    @BeforeEach
    public void initTest() {
        category = createEntity();
    }

    @Test
    public void saveCategory() {
        CategoryDTO dto = categoryMapper.toDto(category);
        dto = this.categoryService.save(dto);

        assertThat(dto.getId() > 0);
    }
}
