package com.haui.ScentLyt.response.category;

import com.haui.ScentLyt.entity.Category;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private Integer id;
    private String categoryName;
    private Boolean active;

    public static CategoryResponse fromCategory(Category category) {
        CategoryResponse response = new CategoryResponse();
        BeanUtils.copyProperties(category, response);
        response.setId(category.getId());
        return response;
    }
}
