package com.yunhalee.withEmployee.company.service;

import com.yunhalee.withEmployee.company.domain.CompanyRepository;
import com.yunhalee.withEmployee.company.dto.CompanyResponses;
import com.yunhalee.withEmployee.user.dto.CeoResponse;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import com.yunhalee.withEmployee.company.dto.CompanyDTO;
import com.yunhalee.withEmployee.company.dto.CompanyListByPageDTO;
import com.yunhalee.withEmployee.company.dto.CompanyListDTO;
import com.yunhalee.withEmployee.company.dto.CompanyResponse;
import com.yunhalee.withEmployee.company.exception.CompanyNameAlreadyInUserException;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.exception.CompanyNotFoundException;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.service.UserService;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CompanyService {

    public static final int COMPANY_PER_PAGE = 9;
    public static final int COMPANY_RECOMMENDATION_PER_PAGE = 3;

    private CompanyRepository companyRepository;
    private UserService userService;

    public CompanyService(CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.userService = userService;
    }

    public CompanyResponses listAll(Integer page){
        Pageable pageable = PageRequest.of(page-1, COMPANY_PER_PAGE, Sort.by("id"));
        Page<Company> pageCompany = companyRepository.findAllCompanies(pageable);
        return CompanyResponses.of(pageCompany.getTotalElements(),
            pageCompany.getTotalPages(),
            pageCompany.getContent().stream()
                .map(company -> CompanyResponse.of(company, CeoResponse.of(company.getCeo())))
                .collect(Collectors.toList()));
    }

    public Company findById(Integer id){
        return companyRepository.findById(id).get();
    }

    public CompanyResponse create(CompanyRequest request) {
        checkCompanyName(request.getName());
        User ceo = userService.findUserById(request.getCeoId());
        Company company = companyRepository.save(request.toCompany(ceo));
        return CompanyResponse.of(company, CeoResponse.of(ceo));
    }

    private void checkCompanyName(String name){
        if (companyRepository.existsByName(name)){
            throw new CompanyNameAlreadyInUserException("This company name is already in use. name : " + name);
        }
    }

    public CompanyDTO save(CompanyRequest companyRequest){

        User ceo = userService.findUserById(companyRequest.getCeoId());

        if(companyRequest.getId() !=null){
            Company existingCompany = companyRepository.findById(companyRequest.getId()).get();

            existingCompany.setName(companyRequest.getName());
            existingCompany.setDescription(companyRequest.getDescription());
            existingCompany.setCeo(ceo);

            companyRepository.save(existingCompany);

            return new CompanyDTO(existingCompany);

        }else{

            Company newCompany = new Company(companyRequest.getName(), companyRequest.getDescription());
            newCompany.setCeo(ceo);
            companyRepository.save(newCompany);
            return new CompanyDTO(newCompany);
        }

    }

    public List<Company> findByCeoId(Integer id){
        return companyRepository.findByUserId(id);
    }

    public void deleteCompany(Integer id) {
         companyRepository.deleteById(id);
    }

    public List<CompanyListDTO> companyRecommendation(){
        Pageable pageable = PageRequest.of(0, COMPANY_RECOMMENDATION_PER_PAGE);
        Page<Company> page = companyRepository.findByRandom(pageable);
        List<Company> companies = page.getContent();

        List<CompanyListDTO> companyListDTOS = new ArrayList<>();
        companies.forEach(company -> companyListDTOS.add(new CompanyListDTO(company)));

        return companyListDTOS;
    }

    public List<CompanyListDTO> searchCompany(String keyword){
        List<Company> companies = companyRepository.findByKeyword(keyword);
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
        Page<Company> companies = companyRepository.findAllCompanies(pageable);
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
        Company existingCompany = companyRepository.findByName(name);
        if(existingCompany==null){
            return true;
        }
        return false;
    }

    public Company findCompanyById(Integer id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new CompanyNotFoundException("Company does not exist with id : " + id));
    }
}
