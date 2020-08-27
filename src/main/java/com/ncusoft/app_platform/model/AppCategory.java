package com.ncusoft.app_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Arno Clare
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppCategory extends BaseModel {
    private String categoryCode;
    private String categoryName;
    private Long parentId;

    public AppCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public AppCategory(Long id, String categoryName) {
        super(id);
        this.categoryName = categoryName;
    }
}
