package com.zambeyzz.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getCompanies()
    {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getOneCompany(@PathVariable Long id)
    {
        return new ResponseEntity<>(companyService.findById(id),HttpStatus.OK);
    }
    @PostMapping("/companies")
    public ResponseEntity<String> createCompany(@RequestBody Company company)
    {
        companyService.createCompany(company);
        return new ResponseEntity<>("This is created successfully",HttpStatus.CREATED);
    }
@DeleteMapping("/companies/{id}")
public ResponseEntity<String> deleteCompany(@PathVariable Long id)
{
    Boolean deletedCompany= companyService.deleteCompany(id);
    if(deletedCompany)
    {
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);

    }
    return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
}
@PutMapping("/companies/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company updateCompany)
{
    Boolean updatedCompany= companyService.updateCompany(id,updateCompany);
    if(updatedCompany)
    {
        return new ResponseEntity<>("Updated successfully",HttpStatus.OK);
    }
    return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
}
}
