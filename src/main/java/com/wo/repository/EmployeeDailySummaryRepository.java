package com.wo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wo.domain.attendance.EmployeeDailySummary;

public interface EmployeeDailySummaryRepository extends JpaRepository<EmployeeDailySummary, Long> {

	EmployeeDailySummary findByDateAndEmployeeMaster_Id(String date, long employeeMaster);

}
