package com.yunhalee.withEmployee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyCreateDTO {

    private Integer id;

    private String name;

    private String description;

    private Integer ceoId;

    public CompanyCreateDTO() {
    }

    public CompanyCreateDTO(String name, String description, Integer ceoId) {
        this.name = name;
        this.description = description;
        this.ceoId = ceoId;
    }

    public CompanyCreateDTO( Integer id,String name, String description, Integer ceoId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ceoId = ceoId;
    }
}
