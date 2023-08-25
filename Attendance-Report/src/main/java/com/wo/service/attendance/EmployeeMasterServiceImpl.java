package com.wo.service.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wo.repository.EmployeeMasterRepository;

@Service
public class EmployeeMasterServiceImpl implements EmployeeMasterService {

	@Autowired
	private EmployeeMasterRepository repository;

}
