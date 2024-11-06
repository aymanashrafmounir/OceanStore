package com.demo.sqlite;

import com.demo.sqlite.Entites.companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<companies, Integer> {
    @Query("SELECT c FROM companies c WHERE c.isActive = :isActive")
    List<companies> getAllCompanies(@Param("isActive") boolean isActive);

    @Modifying
    @Query("UPDATE companies c SET c.isActive = CASE WHEN c.isActive = TRUE THEN FALSE ELSE TRUE END WHERE c.companyId = :companyId")
    void updateIsActive(@Param("companyId") int companyId);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END " +
            "FROM orders o WHERE o.companyId = :companyId AND o.status != 'completed'")
    boolean checkCompanyHasOrders(@Param("companyId") int companyId);
}
