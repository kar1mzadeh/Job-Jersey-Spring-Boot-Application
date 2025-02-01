package com.zambeyzz.Jobms.clients;

import com.zambeyzz.Jobms.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="COMPANYMS",url = "${companyms.url}")
public interface CompanyClient {
@GetMapping("/companies/{id}")
    Company getClient(@PathVariable("id") Long id);
}
