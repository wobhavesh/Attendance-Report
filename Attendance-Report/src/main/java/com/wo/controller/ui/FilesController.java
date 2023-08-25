package com.wo.controller.ui;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wo.controller.sheet.AttendanceController;
import com.wo.domain.attendance.ErrorCell;
import com.wo.service.file.FilesStorageService;
import com.wo.service.sheet.ReadExcelSheetService;

@Controller
public class FilesController {

	private final Logger logger = LoggerFactory.getLogger(FilesController.class);

	@Value("${file.storage.location:/opt/data/filestorage/excel}")
	private String path;
	
    @Autowired
    ReadExcelSheetService readExcelSheetService;
	
	@GetMapping("/")
	public ModelAndView importSheet(@RequestBody(required = false) List<ErrorCell> errorCells) {
		ModelAndView view = new ModelAndView("import");
		view.addObject("cellErrors", errorCells);
		return view;
	}

	@Autowired
	FilesStorageService storageService;
	
	@Autowired
	AttendanceController controller;

//	@PostMapping("/upload")
//	public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) {
//		logger.info("upload...");
//		ModelAndView view = new ModelAndView("import");
//		String message = "";
//		try {
//			storageService.save(file);
//			
//			message = "Uploaded the file successfully: " + file.getOriginalFilename();
//			logger.info("Uploaded the file successfully: >>>{}", message);
//			view.addObject("file", file.getOriginalFilename());
//			logger.info("Uploaded the file successfully path...: >>>{}", (path + File.separatorChar + file.getOriginalFilename()));
////			ResponseEntity<List<ErrorCell>> entity = controller.importExcel(path + File.separatorChar+ file.getOriginalFilename());
////			view.setViewName("redirect:/import");
////			view.setStatus(entity.getStatusCode());
////			logger.info("Response entity--{}", entity.getBody());
////			view.addObject("cellErrors", entity.getBody());
////			if (HttpStatus.OK.equals(entity.getStatusCode())) {
////				logger.info("Imported successfully...");
////				view.addObject("message", "Imported successfully");
////			} else {
////				logger.info("entity.getBody()>>{}", entity.getBody());
////				view.addObject("message", entity.getBody());
////			}
//			return view;
//		} catch (Exception e) {
//			logger.error("Could not upload file::{}", e);
//			message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
//			return view;
//		}
//	}



//	@GetMapping("/files")
//	public ResponseEntity<List<FileInfo>> getListFiles() {
//		List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
//			String filename = path.getFileName().toString();
//			String url = MvcUriComponentsBuilder
//					.fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
//
//			return new FileInfo(filename, url);
//		}).collect(Collectors.toList());
//
//		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
//	}
//
//	@GetMapping("/files/{filename:.+}")
//	@ResponseBody
//	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//		Resource file = storageService.load(filename);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//				.body(file);
//	}

}
