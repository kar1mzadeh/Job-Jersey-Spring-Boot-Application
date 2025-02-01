package com.zambeyzz.companyms.company.imp;


import com.zambeyzz.companyms.company.Company;
import com.zambeyzz.companyms.company.CompanyRepository;
import com.zambeyzz.companyms.company.CompanyService;
import com.zambeyzz.companyms.company.clients.ReviewClient;
import com.zambeyzz.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImp implements CompanyService {
    private CompanyRepository companyRepository;
    ReviewClient reviewClient;
//    private Long nextId=1L;

    public CompanyServiceImp(CompanyRepository companyRepository,ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient=reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void createCompany(Company company) {
//        company.setId(nextId++);
      companyRepository.save(company);
    }

    @Override
    public Boolean deleteCompany(Long id) {
        try {
            companyRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateCompany(Long id, Company updatedCompany) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent())
        {
            Company company = companyOptional.get();
            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());
            companyRepository.save(updatedCompany);
            return true;
        }
        return false;
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company is not found: " + reviewMessage.getCompanyId()));

        double rating = reviewClient.getAvarageRatings(reviewMessage.getCompanyId());

        company.setRating(rating);

        companyRepository.save(company);
    }

}
