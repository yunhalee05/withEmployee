package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.CompanyRepository;
import com.yunhalee.withEmployee.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repo;

    public List<Company> listAll(){
        return repo.findAllCompanies();
    }

    public Company findById(Integer id){
        return repo.findById(id).get();
    }
}
