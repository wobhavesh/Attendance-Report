package com.wo.service.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wo.repository.EmployeeDailySummaryRepository;

@Service
public class EmployeeDailySummaryServiceImpl implements EmployeeDailySummaryService {

	@Autowired
	private EmployeeDailySummaryRepository repository;

}
