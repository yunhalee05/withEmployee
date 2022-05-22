package com.yunhalee.withEmployee.company.controller;

import com.yunhalee.withEmployee.company.dto.CompanyListResponse;
import com.yunhalee.withEmployee.company.dto.CompanyListResponses;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import com.yunhalee.withEmployee.company.dto.CompanyResponse;
import com.yunhalee.withEmployee.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService service;

//    @GetMapping("/companies")
//    public ResponseEntity<CompanyResponses> listAll(@RequestParam("page") Integer page){
//        return ResponseEntity.ok(service.listAll(page));
//    }

    @GetMapping("/companies/recommendation")
    public ResponseEntity<CompanyListResponses> companyRecommendation(){
        return ResponseEntity.ok(service.companyRecommendation());
    }

    @GetMapping(value = "/companies", params = "keyword")
    public  ResponseEntity<CompanyListResponses> searchCompany(@RequestParam("keyword")String keyword){
        return ResponseEntity.ok(service.searchCompany(keyword));
    }

    @GetMapping(value = "/companies", params = {"page", "sort"})
    public ResponseEntity<CompanyListResponses> allCompanies(@RequestParam("page")Integer page, @RequestParam("sort")String sort){
        return ResponseEntity.ok(service.getCompaniesByPage(page, sort));
    }

    @GetMapping("/users/{ceoId}/companies")
    public ResponseEntity<CompanyListResponses> getByCeoId(@PathVariable("ceoId") Integer ceoId){
        return ResponseEntity.ok(service.findByCeoId(ceoId));
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyResponse> getByName(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.companyResponse(id));
    }

//    @PostMapping("/company/save")
//    public CompanyDTO save(@RequestBody CompanyRequest companyRequest){
//        return service.save(companyRequest);
//    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyListResponse> create(@RequestBody CompanyRequest companyRequest){
        return ResponseEntity.ok(service.create(companyRequest));
    }

    @PostMapping("/companies/{id}")
    public ResponseEntity<CompanyListResponse> update(@PathVariable("id") Integer id, @RequestBody CompanyRequest companyRequest){
        return ResponseEntity.ok(service.update(id, companyRequest));
    }


    @DeleteMapping("/companies/{id}")
    public ResponseEntity deleteCompany(@PathVariable("id") Integer id){
        service.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }


}
