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
@Deprecated
public class DataDictionary extends BaseModel {
    private String typeCode;
    private String typeName;
    private Long valueId;
    private String valueName;

    public DataDictionary(Long id) {
        super(id);
    }

    public DataDictionary(Long id, String typeCode, String typeName, Long valueId, String valueName) {
        super(id);
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.valueId = valueId;
        this.valueName = valueName;
    }
}
