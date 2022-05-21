package com.yunhalee.withEmployee.company.controller;

import com.yunhalee.withEmployee.company.dto.CompanyListResponses;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import com.yunhalee.withEmployee.company.dto.CompanyDTO;
import com.yunhalee.withEmployee.company.dto.CompanyListByPageDTO;
import com.yunhalee.withEmployee.company.dto.CompanyListDTO;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.dto.CompanyResponse;
import com.yunhalee.withEmployee.company.dto.CompanyResponses;
import com.yunhalee.withEmployee.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService service;

//    @GetMapping("/companies")
//    public ResponseEntity<CompanyResponses> listAll(@RequestParam("page") Integer page){
//        return ResponseEntity.ok(service.listAll(page));
//    }

    @GetMapping("/company/{id}")
    public CompanyDTO getByName(@PathVariable("id") Integer id){
        Company company = service.findById(id);
//        System.out.println(company);
        return new CompanyDTO(company);
    }

    @PostMapping("/company/save")
    public CompanyDTO save(@RequestBody CompanyRequest companyRequest){
        return service.save(companyRequest);
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyResponse> create(@RequestBody CompanyRequest companyRequest){
        return ResponseEntity.ok(service.create(companyRequest));
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
    public ResponseEntity<CompanyListResponses> allCompanies(@RequestParam("page")Integer page, @RequestParam("sort")String sort){
        return ResponseEntity.ok(service.getCompaniesByPage(page, sort));
    }

    @PostMapping("/company/check_name")
    public String checkNameUnique(@Param("name")String name){
        return service.isNameUnique(name) ? "OK" : "Duplicated";
    }

}
