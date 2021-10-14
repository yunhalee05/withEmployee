package com.yunhalee.withEmployee.service;

import com.yunhalee.withEmployee.Repository.CompanyRepository;
import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.dto.*;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CompanyService {

    public static final int COMPANY_PER_PAGE = 9;
    public static final int COMPANY_RECOMMENDATION_PER_PAGE = 3;

    @Autowired
    private CompanyRepository repo;

    @Autowired
    private UserRepository userRepo;

    public CompanyListByPageDTO listAll(Integer page){
        Pageable pageable = PageRequest.of(page-1, COMPANY_PER_PAGE, Sort.by("id"));
        Page<Company> pageCompany = repo.findAllCompanies(pageable);
        List<Company> companies = pageCompany.getContent();
        CompanyListByPageDTO companyListByPageDTO = new CompanyListByPageDTO(pageCompany.getTotalElements(), pageCompany.getTotalPages(), companies);
        return companyListByPageDTO;
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

    public List<CompanyListDTO> companyRecommendation(){
        Pageable pageable = PageRequest.of(0, COMPANY_RECOMMENDATION_PER_PAGE);
        Page<Company> page = repo.findByRandom(pageable);
        List<Company> companies = page.getContent();

        List<CompanyListDTO> companyListDTOS = new ArrayList<>();
        companies.forEach(company -> companyListDTOS.add(new CompanyListDTO(company)));

        return companyListDTOS;
    }

    public List<CompanyListDTO> searchCompany(String keyword){
        List<Company> companies = repo.findByKeyword(keyword);
        List<CompanyListDTO> companyListDTOS = new ArrayList<>();
        companies.forEach(company -> companyListDTOS.add(new CompanyListDTO(company)));

        return companyListDTOS;
    }

    public HashMap<String, Object> getCompaniesByPage(Integer page, String sort){
        Pageable pageable = PageRequest.of(page-1,COMPANY_PER_PAGE, Sort.by("createdAt"));

        if(sort.equals("createdAtDesc")){
            pageable = PageRequest.of(page-1,COMPANY_PER_PAGE, Sort.by(Sort.Direction.DESC,"createdAt"));
        }else if(sort.equals("nameAsc")){
            pageable = PageRequest.of(page-1,COMPANY_PER_PAGE, Sort.by("name"));
        }else if(sort.equals("nameDesc")){
            pageable = PageRequest.of(page-1,COMPANY_PER_PAGE, Sort.by(Sort.Direction.DESC,"name"));
        }
        Page<Company> companies = repo.findAllCompanies(pageable);
        List<Company> allCompanies = companies.getContent();

        List<CompanyListDTO> companyListDTOS = new ArrayList<>();
        allCompanies.forEach(company -> companyListDTOS.add(new CompanyListDTO(company)));

        HashMap<String, Object> response= new HashMap<>();
        response.put("totalElement", companies.getTotalElements());
        response.put("totalPage", companies.getTotalPages());
        response.put("companies", companyListDTOS);


        return response;
    }



    public boolean isNameUnique(String name){
        Company existingCompany = repo.findByName(name);
        if(existingCompany==null){
            return true;
        }
        return false;
    }
}
