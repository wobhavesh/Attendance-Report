package com.wo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wo.domain.attendance.EmployeeMonthlySummary;

public interface EmployeeMonthlySummaryRepository extends JpaRepository<EmployeeMonthlySummary, Long> {

//	@Query("from EmployeeMonthlySummary where date_range=?1 and employee_master_id = ?2")
	EmployeeMonthlySummary findByMonthDateRangeAndEmployeeMaster_Id(String monthDateRange, long id);

}
