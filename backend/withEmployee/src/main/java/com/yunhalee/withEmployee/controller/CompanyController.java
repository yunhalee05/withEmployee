package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.CompanyDTO;
import com.yunhalee.withEmployee.dto.CompanyListDTO;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService service;

    @GetMapping("/company/companylist")
    public List<CompanyListDTO> listAll(){
        List<Company> companies = service.listAll();

        List<CompanyListDTO> companyDTOS = new ArrayList<CompanyListDTO>();

        companies.forEach(company -> {
            companyDTOS.add(new CompanyListDTO(company));
        });
        return companyDTOS;
    }

    @GetMapping("/company/{id}")
    public CompanyDTO getByName(@PathVariable("id") Integer id){
        Company company = service.findById(id);
//        System.out.println(company);
        return new CompanyDTO(company);
    }
}
