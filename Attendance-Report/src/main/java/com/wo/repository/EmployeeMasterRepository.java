package com.wo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wo.domain.attendance.EmployeeMaster;

public interface EmployeeMasterRepository extends JpaRepository<EmployeeMaster, Long> {

	EmployeeMaster findByEmployeeNumber(String employeeNumber);
	
}
