package com.yunhalee.withEmployee.company.service;

import com.yunhalee.withEmployee.company.domain.CompanyRepository;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CompanyServiceTests {

//    @Autowired
//    CompanyService companyService;
//
//    @Autowired
//    CompanyRepository companyRepository;
//
//    @Test
//    public void createCompany(){
//        //given
//        String companyName = "testCompany";
//        String companyDescription = "This is test.";
//        Integer ceoId = 17;
//        CompanyRequest companyRequest = new CompanyRequest(companyName,companyDescription,ceoId);
//
//        //when
//        CompanyDTO companyDTO = companyService.save(companyRequest);
//
//        //then
//        assertEquals(companyName, companyDTO.getName());
//        assertEquals(companyDescription,companyDTO.getDescription());
//    }
//
//    @Test
//    public void updateCompany(){
//        //given
//        String companyName = "testCompany";
//        String companyDescription = "This is test.";
//        Integer ceoId = 17;
//        CompanyRequest companyRequest = new CompanyRequest(companyName,companyDescription,ceoId);
//        CompanyDTO companyDTO = companyService.save(companyRequest);
//        CompanyRequest companyRequest1 = new CompanyRequest(companyDTO.getId(),"testUpdateCompany","testD",ceoId);
//
//        //when
//        CompanyDTO companyDTO1 = companyService.save(companyRequest1);
//
//        //then
//        assertNotEquals(companyName, companyDTO1.getName());
//        assertNotEquals(companyDescription, companyDTO1.getDescription());
//    }
//
//    @Test
//    public void deleteCompany(){
//        //given
//        Integer id = 1;
//
//        //when
//        companyService.deleteCompany(id);
//
//        //then
//        assertNull(companyRepository.findById(id));
//    }
//
//    @Test
//    public void isCompanyNameUnique(){
//        //given
//        String name = "testCompany";
//
//        //when
//        boolean check = companyService.isNameUnique(name);
//
//        //then
//        assertFalse(check);
//    }
//
//    @Test
//    public void searchCompany(){
//        //given
//        String keyword = "test";
//
//        //when
//        List<CompanyListDTO> companyListDTOS = companyService.searchCompany(keyword);
//
//        //then
//        assertNotEquals(0, companyListDTOS.size());
//        companyListDTOS.forEach(companyListDTO -> {
//            assertTrue(companyListDTO.getName().contains(keyword));
//        });
//    }
//
//    @Test
//    public void getCompaniesByPage(){
//        //given
//        Integer page = 1;
//        String sort = "nameAsc";
//
//        //when
//        HashMap<String, Object> hashMap = companyService.getCompaniesByPage(page, sort);
//        List<CompanyListDTO> companyListDTOS = (List<CompanyListDTO>) hashMap.get("companies");
//
//        //then
//        assertEquals(companyListDTOS.size(),9);
//        for (CompanyListDTO companyListDTO : companyListDTOS) {
//            System.out.println(companyListDTO.getName());
//        }
//
//    }
}
