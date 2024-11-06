package com.demo.sqlite;

import com.demo.sqlite.Entites.companies;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<companies> createCompanies(List<companies> companiesList) {
        return companyRepository.saveAll(companiesList);
    }

    public ResponseEntity<String> updateCompany(int id, companies company) {
        Optional<companies> existingCompany = getCompanyById(id);

        if (existingCompany.isPresent()) {
            companies updatedCompany = existingCompany.get();

            Optional.ofNullable(company.getCommercialName()).ifPresent(updatedCompany::setCommercialName);
            Optional.ofNullable(company.getEmail()).ifPresent(updatedCompany::setEmail);
            Optional.ofNullable(company.getContactName()).ifPresent(updatedCompany::setContactName);
            Optional.ofNullable(company.getContactPhone()).ifPresent(updatedCompany::setContactPhone);
            Optional.ofNullable(company.getPriceTierId()).ifPresent(updatedCompany::setPriceTierId);
            Optional.ofNullable(company.getIsActive()).ifPresent(updatedCompany::setIsActive);

            saveCompany(updatedCompany);

            return ResponseEntity.ok("Company updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found.");
        }
    }

    public List<companies> getAllCompanies(boolean isActive) {
        return companyRepository.getAllCompanies(isActive);
    }

    public List<companies> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Transactional
    public ResponseEntity<String> deactivateCompany(int id) {
        Optional<companies> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            companies company = optionalCompany.get();
            companyRepository.updateIsActive(company.getCompanyId());
            return ResponseEntity.ok("Company deactivated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found.");
    }

    public ResponseEntity<String> deleteCompany(int id) {
        Optional<companies> existingCompany = getCompanyById(id);

        if (existingCompany.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found.");
        }

        companies company = existingCompany.get();
        boolean hasOrders = companyRepository.checkCompanyHasOrders(company.getCompanyId());

        if (hasOrders) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Company has orders and cannot be deleted.");
        }

        companyRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Company deleted successfully.");
    }

    public Optional<companies> getCompanyById(int id) {
        return companyRepository.findById(id);
    }

    public companies saveCompany(companies company) {
        return companyRepository.save(company);
    }

}

