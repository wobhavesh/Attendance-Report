package com.wo.service.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wo.repository.EmployeeMonthlySummaryRepository;

@Service
public class EmployeeMonthlySummaryServiceImpl implements EmployeeMonthlySummaryService {

	@Autowired
	private EmployeeMonthlySummaryRepository repository;

}
