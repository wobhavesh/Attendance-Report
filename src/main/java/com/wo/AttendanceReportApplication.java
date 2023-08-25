package com.wo;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.wo.service.file.FilesStorageService;

@SpringBootApplication
public class AttendanceReportApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Resource
	FilesStorageService filesStorageService;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AttendanceReportApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(AttendanceReportApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		filesStorageService.init();
	}

}
