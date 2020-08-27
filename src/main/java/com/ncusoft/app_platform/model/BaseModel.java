package com.ncusoft.app_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author Arno Clare
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {
    @Id
    private Long id;
    private Long createBy;
    private Date creationDate;
    private Long modifyBy;
    private Date modifyDate;

    public BaseModel(long id) {
        this.id = id;
    }

    public BaseModel(Long id) {
        this.id = id;
    }
}
