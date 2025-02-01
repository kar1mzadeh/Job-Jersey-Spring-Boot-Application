package com.zambeyzz.companyms.company;

import com.zambeyzz.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
List<Company> getAllCompanies();
Company findById(Long id);
void createCompany(Company company);
Boolean deleteCompany(Long id);
Boolean updateCompany(Long id, Company updatedCompany);
public void updateCompanyRating(ReviewMessage reviewMessage);
}
