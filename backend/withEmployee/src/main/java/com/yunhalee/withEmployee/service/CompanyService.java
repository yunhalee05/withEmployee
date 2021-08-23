package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.CompanyRepository;
import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.dto.CompanyCreateDTO;
import com.yunhalee.withEmployee.dto.CompanyDTO;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repo;

    @Autowired
    private UserRepository userRepo;

    public List<Company> listAll(){
        return repo.findAllCompanies();
    }

    public Company findById(Integer id){
        return repo.findById(id).get();
    }

    public CompanyDTO save(CompanyCreateDTO companyCreateDTO){

        User ceo = userRepo.findById(companyCreateDTO.getCeoId()).get();

        if(companyCreateDTO.getId() !=null){
            Company existingCompany = repo.findById(companyCreateDTO.getId()).get();

            existingCompany.setName(companyCreateDTO.getName());
            existingCompany.setDescription(companyCreateDTO.getDescription());
            existingCompany.setCeo(ceo);

            repo.save(existingCompany);

            return new CompanyDTO(existingCompany);

        }else{

            Company newCompany = new Company(companyCreateDTO.getName(),companyCreateDTO.getDescription());
            newCompany.setCeo(ceo);
            repo.save(newCompany);
            return new CompanyDTO(newCompany);
        }

    }

    public List<Company> findByCeoId(Integer id){
        return repo.findByUserId(id);
    }

    public void deleteCompany(Integer id) {
         repo.deleteById(id);
    }

    public boolean isNameUnique(String name){
        Company existingCompany = repo.findByName(name);
        if(existingCompany==null){
            return true;
        }
        return false;
    }
}
