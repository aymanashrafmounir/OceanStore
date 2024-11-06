package com.demo.sqlite;

import com.demo.sqlite.Entites.companies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyController(CompanyService companyService, CompanyRepository companyRepository) {
        this.companyService = companyService;
        this.companyRepository = companyRepository;
    }

    @PostMapping
    public List<companies> createCompanies(@RequestBody List<companies> companiesList) {
        return companyService.createCompanies(companiesList);
    }

    @GetMapping("/getAll")
    public List<companies> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/getAll/{status}")
    public List<companies> getAllActiveCompanies(@PathVariable boolean status) {
        return companyService.getAllCompanies(status);
    }

    @PutMapping("/toggleActivation/{id}")
    public ResponseEntity<String> deactivateCompany(@PathVariable int id) {
        return companyService.deactivateCompany(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editCompany(@PathVariable int id, @RequestBody companies company) {
        return companyService.updateCompany(id, company);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable int id) {
        return companyService.deleteCompany(id);
    }
}
