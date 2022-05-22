package com.yunhalee.withEmployee.company.controller;

import com.yunhalee.withEmployee.company.dto.CompanyListResponse;
import com.yunhalee.withEmployee.company.dto.CompanyListResponses;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import com.yunhalee.withEmployee.company.dto.CompanyResponse;
import com.yunhalee.withEmployee.company.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies/recommendation")
    public ResponseEntity<CompanyListResponses> companyRecommendation(){
        return ResponseEntity.ok(companyService.companyRecommendation());
    }

    @GetMapping(value = "/companies", params = "keyword")
    public  ResponseEntity<CompanyListResponses> searchCompany(@RequestParam("keyword")String keyword){
        return ResponseEntity.ok(companyService.searchCompany(keyword));
    }

    @GetMapping(value = "/companies", params = {"page", "sort"})
    public ResponseEntity<CompanyListResponses> allCompanies(@RequestParam("page")Integer page, @RequestParam("sort")String sort){
        return ResponseEntity.ok(companyService.getCompaniesByPage(page, sort));
    }

    @GetMapping("/users/{ceoId}/companies")
    public ResponseEntity<CompanyListResponses> getByCeoId(@PathVariable("ceoId") Integer ceoId){
        return ResponseEntity.ok(companyService.findByCeoId(ceoId));
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyResponse> getByName(@PathVariable("id") Integer id){
        return ResponseEntity.ok(companyService.companyResponse(id));
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyListResponse> create(@RequestBody CompanyRequest companyRequest){
        return ResponseEntity.ok(companyService.create(companyRequest));
    }

    @PostMapping("/companies/{id}")
    public ResponseEntity<CompanyListResponse> update(@PathVariable("id") Integer id, @RequestBody CompanyRequest companyRequest){
        return ResponseEntity.ok(companyService.update(id, companyRequest));
    }


    @DeleteMapping("/companies/{id}")
    public ResponseEntity deleteCompany(@PathVariable("id") Integer id){
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }


}
