package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.CompanyCreateDTO;
import com.yunhalee.withEmployee.dto.CompanyDTO;
import com.yunhalee.withEmployee.dto.CompanyListDTO;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/company/save")
    public CompanyDTO save(@RequestBody CompanyCreateDTO companyCreateDTO){
        return service.save(companyCreateDTO);
    }


    @GetMapping("/companies/{id}")
    public List<CompanyListDTO> getByCeoId(@PathVariable("id") Integer id){
        List<Company> companies = service.findByCeoId(id);
        List<CompanyListDTO> companyListDTOS = new ArrayList<>();
        companies.forEach(company -> companyListDTOS.add(new CompanyListDTO(company)));

        return companyListDTOS;
    }

    @PostMapping("/company/check_name")
    public String checkNameUnique(@Param("name")String name){
        return service.isNameUnique(name) ? "OK" : "Duplicated";
    }
}
