package com.wo;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wo.repository.EmployeeDailySummaryRepository;
import com.wo.repository.EmployeeMonthlySummaryRepository;

@SpringBootTest
class AttendanceReportApplicationTests {
	
	@Autowired
	EmployeeDailySummaryRepository EmployeeDailySummaryRepository;
	@Autowired
	com.wo.repository.EmployeeMasterRepository EmployeeMasterRepository;
	@Autowired
	EmployeeMonthlySummaryRepository monthlySummaryRepository;

//	@Test
	void contextLoads() throws ParseException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
//		System.out.println("date>>>" + dateFormat.parse("01-May-2023"));
//		LocalDateTime dateTime = java.time.LocalDateTime.parse("10:19",DateTimeFormatter.ofPattern( "HH:mm" ));
//		LocalTime.of(216, 19);
//		System.out.println("date>>>" + Time.parse(778740000));
//		DateFormat formatter = new SimpleDateFormat("HH:mm");
//		Time time = new Time(formatter.parse("10:19").getTime());
		
//		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		
//		System.out.println("date>>>" + dateTime.toLocalTime());
//		System.out.println("date>>>" + time);
		
//		String path = "/home/bhavesh/Downloads/Monthly Performance Report 01 May 2023 - 31 May 2023 - WebOsmotic Pvt. Ltd..xlsx";
//		String updatedSheetName = path.substring(0, path.lastIndexOf('.'));
//		String sheetExtension = path.substring(path.lastIndexOf('.'));
//		System.out.println("updatedSheetName >>" + updatedSheetName );
//		System.out.println("sheetExtension >>" + sheetExtension );
		
		
//		Time time = new Time(778740000);
//		
//		EmployeeDailySummary dailySummary = new EmployeeDailySummary();
//		dailySummary.setDate("test data");
//		dailySummary.setEffectiveHours(date);
//		dailySummary.setGrossHours(date);
//		
//		dailySummary.setInTime(time);
//		dailySummary.setOutTime(date);
//		dailySummary.setShiftHours(date);
//		
//		EmployeeDailySummaryRepository.save(dailySummary);
		
//		EmployeeMaster employeeMaster = EmployeeMasterRepository.findByEmployeeNumber("WOYAS0117");
//		System.out.println("emplo;;;;" + EmployeeDailySummaryRepository.findByDateAndEmployeeMaster_Id("09-Feb-20231", 78));
//		EmployeeDailySummaryRepository.findByDateAndEmployeeMaster_Id("19-Feb-2023", 78l);
		System.out.println("emplo;;;;" + monthlySummaryRepository.findByMonthDateRangeAndEmployeeMaster_Id("01-Feb-2023 - 28-Feb-20231", 1));
		
		
	}

}
