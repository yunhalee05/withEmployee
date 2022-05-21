package com.yunhalee.withEmployee.company.service;

import com.yunhalee.withEmployee.company.domain.CompanyRepository;
import com.yunhalee.withEmployee.company.dto.CompanyListResponse;
import com.yunhalee.withEmployee.company.dto.CompanyListResponses;
import com.yunhalee.withEmployee.company.dto.CompanyResponses;
import com.yunhalee.withEmployee.company.exception.CompanyNameEmptyException;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponse;
import com.yunhalee.withEmployee.user.dto.CeoResponse;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import com.yunhalee.withEmployee.company.dto.CompanyResponse;
import com.yunhalee.withEmployee.company.exception.CompanyNameAlreadyInUseException;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.exception.CompanyNotFoundException;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.dto.MemberResponse;
import com.yunhalee.withEmployee.user.service.UserService;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
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
        Page<Company> pageCompany = companyRepository.findCompanies(pageable);
        return CompanyResponses.of(pageCompany.getTotalElements(),
            pageCompany.getTotalPages(),
            pageCompany.getContent().stream()
                .map(company -> CompanyResponse.of(company, CeoResponse.of(company.getCeo())))
                .collect(Collectors.toList()));
    }

    public CompanyListResponses getCompaniesByPage(Integer page, String sort){
        Pageable pageable = getPageable(page, sort);
        Page<Company> pageCompanies = companyRepository.findCompanies(pageable);
        return CompanyListResponses.of(pageCompanies.getTotalElements(),
            pageCompanies.getTotalPages(),
            pageCompanies.getContent().stream()
                .map(company -> CompanyListResponse.of(company, CeoResponse.of(company.getCeo())))
                .collect(Collectors.toList()));
    }

    private Pageable getPageable(Integer page, String sort) {
        if(sort.equals("createdAtDesc")){
            return PageRequest.of(page-1,COMPANY_PER_PAGE, Sort.by(Sort.Direction.DESC,"createdAt"));
        }else if(sort.equals("nameAsc")){
            return PageRequest.of(page-1,COMPANY_PER_PAGE, Sort.by("name"));
        }else if(sort.equals("nameDesc")){
            return PageRequest.of(page-1,COMPANY_PER_PAGE, Sort.by(Sort.Direction.DESC,"name"));
        }
        return PageRequest.of(page-1,COMPANY_PER_PAGE, Sort.by("createdAt"));
    }

    public CompanyListResponses companyRecommendation(){
        Pageable pageable = PageRequest.of(0, COMPANY_RECOMMENDATION_PER_PAGE);
        Page<Company> pageCompanies = companyRepository.findByRandom(pageable);
        return CompanyListResponses.of(pageCompanies.getTotalElements(),
            pageCompanies.getTotalPages(),
            pageCompanies.getContent().stream()
                .map(company -> CompanyListResponse.of(company, CeoResponse.of(company.getCeo())))
                .collect(Collectors.toList()));
    }

    public CompanyListResponses searchCompany(String keyword){
        List<Company> companies = companyRepository.findByKeyword(keyword);
        return CompanyListResponses.of((long)companies.size(), 1,
            companies.stream().map(company -> CompanyListResponse.of(company, CeoResponse.of(company.getCeo()))).collect(Collectors.toList()));
    }

    public CompanyListResponses findByCeoId(Integer ceoId){
        List<Company> companies = companyRepository.findByUserId(ceoId);
        return CompanyListResponses.of((long)companies.size(), 1,
            companies.stream().map(company -> CompanyListResponse.of(company, CeoResponse.of(company.getCeo()))).collect(Collectors.toList()));
    }

    public CompanyResponse companyResponse(Integer id){
        Company company = findById(id);
        return CompanyResponse.of(company,
            company.getTeams().stream()
                .map(team -> SimpleTeamResponse.of(team))
                .collect(Collectors.toList()),
            CeoResponse.of(company.getCeo()),
            company.getMembers().stream()
                .map(member -> MemberResponse.of(member))
                .collect(Collectors.toList()));
    }

    @Transactional
    public CompanyResponse create(CompanyRequest request) {
        checkCompanyName(request.getName());
        User ceo = userService.findUserById(request.getCeoId());
        Company company = companyRepository.save(request.toCompany(ceo));
        return CompanyResponse.of(company, CeoResponse.of(ceo));
    }

    private void checkCompanyName(String name){
        checkNameIsEmpty(name);
        if (companyRepository.existsByName(name)){
            throw new CompanyNameAlreadyInUseException("This company name is already in use. name : " + name);
        }
    }


    private void checkNameIsEmpty(String name) {
        if (name.isBlank() || name.isEmpty()) {
            throw new CompanyNameEmptyException("Name could not be empty.");
        }
    }

    @Transactional
    public CompanyResponse update(Integer id, CompanyRequest request) {
        checkCompanyName(id, request);
        Company company = findById(id);
        company.update(getToUpdateUser(company.getCeo().getId(), request, company));
        return CompanyResponse.of(company, CeoResponse.of(company.getCeo()));
    }

    private void checkCompanyName(Integer id, CompanyRequest request) {
        checkNameIsEmpty(request.getName());
        if (companyRepository.existsByName(request.getName()) && !findCompanyByName(request.getName()).isCompany(id)){
            throw new CompanyNameAlreadyInUseException("This company name is already in use. name : " + request.getName());
        }
    }

    private Company getToUpdateUser(Integer ceoId, CompanyRequest request, Company company){
        if (!request.getCeoId().equals(ceoId)){
            User ceo = userService.findUserById(request.getCeoId());
            ceo.addCompany(company);
            return request.toCompany(ceo);
        }
        return request.toCompany();
    }

    @Transactional
    public void deleteCompany(Integer id) {
         companyRepository.deleteById(id);
    }


    private Company findById(Integer id) {
        return companyRepository.findByCompanyId(id)
            .orElseThrow(() -> new CompanyNotFoundException("Company does not exist with id : " + id));
    }

    public Company findCompanyById(Integer id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new CompanyNotFoundException("Company does not exist with id : " + id));
    }

    public Company findCompanyByName(String name) {
        return companyRepository.findByName(name)
            .orElseThrow(() -> new CompanyNotFoundException("Company does not exist with name : " + name));
    }
}
