package com.wo.controller.sheet;

import java.io.File;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wo.domain.attendance.ErrorCell;
import com.wo.domain.attendance.FileExtentions;
import com.wo.service.file.FilesStorageService;
import com.wo.service.sheet.ReadExcelSheetService;
import com.wo.service.sheet.ReadExcelSheetServiceImpl;

@Controller
@CrossOrigin("http://localhost:8080")
public class AttendanceController {
	
	private final Logger logger = LoggerFactory.getLogger(ReadExcelSheetServiceImpl.class);
	
	@Value("${file.storage.location:/opt/data/filestorage/excel}")
	private String path;
	
    @Autowired
    ReadExcelSheetService readExcelSheetService;
    
	@Autowired
	FilesStorageService storageService;

	@PostMapping(value = "/upload")
    public ResponseEntity<List<ErrorCell>> importExcel(@RequestParam("file") MultipartFile file) {

    	ResponseEntity<List<ErrorCell>> response = new ResponseEntity<>(HttpStatus.OK);
    	logger.info("read excel...{}", file);
    	try {
    		storageService.save(file);
//			File file = new File("/home/bhavesh/Downloads/attendance-sheets/");
    		String filePath = path + File.separatorChar + file.getOriginalFilename();
			File fileInput = new File(filePath);
			if (fileInput.exists()) {
				if (fileInput.isDirectory()) {
					logger.info("file.listFiles()>>{}", fileInput.listFiles().length);
					for (File directoryFile : fileInput.listFiles()) {
						String path = directoryFile.getPath();
						String sheetExtension = path.substring(path.lastIndexOf('.'));
						if (FileExtentions.valueOfLabel(sheetExtension) != null) {
							readExcelSheetService.readExcel(path);
							response = new ResponseEntity<>(readExcelSheetService.getCellError(), HttpStatus.OK);
						}
					}
				} else {
					logger.info("file.listFiles()>>{}", fileInput);
					String path = filePath;
					String sheetExtension = path.substring(path.lastIndexOf('.'));
					if (FileExtentions.valueOfLabel(sheetExtension) != null) {
						readExcelSheetService.readExcel(path);
						response = new ResponseEntity<>(readExcelSheetService.getCellError(), HttpStatus.OK);
					} else {
						response = new ResponseEntity<>(/*"File is not in excel format."*/null, HttpStatus.BAD_REQUEST);
					}
				}
			} else {
				response = new ResponseEntity<>(/*"File does not exist."*/null, HttpStatus.BAD_REQUEST);
			}
    	} catch (RuntimeException e) {
    		if (e.getMessage().equals("A file of that name already exists.")) {
    			response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    		} else {
    			response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    		}
			logger.error("file open error");
    	} catch (Exception e) {
    		response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			logger.error("file open error");
		}
    	
        return response;
    }
}
