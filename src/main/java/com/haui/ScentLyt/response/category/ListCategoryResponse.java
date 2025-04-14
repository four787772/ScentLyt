package com.haui.ScentLyt.response.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ListCategoryResponse {
    private List<CategoryResponse> categories;
    private int totalPages;
}
