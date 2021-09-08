package com.yunhalee.withEmployee.controller;

import com.yunhalee.withEmployee.dto.*;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService service;

    @GetMapping("/company/companylist")
    public CompanyListByPageDTO listAll(@Param("page") String page){
        Integer pageCompany = Integer.parseInt(page);
        return service.listAll(pageCompany);
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

    @DeleteMapping("/company/delete/{id}")
    public Integer deleteCompany(@PathVariable("id") Integer id){
        service.deleteCompany(id);
        return id;
    }

    @GetMapping("/company/recommendation")
    public List<CompanyListDTO> companyRecommendation(){
        return service.companyRecommendation();
    }

    @GetMapping("/company/search")
    public List<CompanyListDTO> searchCompany(@Param("keyword")String keyword){
        return service.searchCompany(keyword);
    }

    @GetMapping("/companies")
    public ResponseEntity<?> allCompanies(@Param("page")String page, @Param("sort")String sort){
        Integer pageCompany = Integer.parseInt(page);
        return service.getCompaniesByPage(pageCompany, sort);
    }

    @PostMapping("/company/check_name")
    public String checkNameUnique(@Param("name")String name){
        return service.isNameUnique(name) ? "OK" : "Duplicated";
    }

}
