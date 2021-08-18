package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.CompanyDTO;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService service;

    @GetMapping("/company/companylist")
    public List<CompanyDTO> listAll(){
        List<Company> companies = service.listAll();

        List<CompanyDTO> companyDTOS = new ArrayList<CompanyDTO>();

        companies.forEach(company -> {
            companyDTOS.add(new CompanyDTO(company));
        });
        return companyDTOS;
    }
}
