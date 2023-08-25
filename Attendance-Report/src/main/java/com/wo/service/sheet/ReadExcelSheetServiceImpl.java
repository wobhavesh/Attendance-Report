package com.wo.service.sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wo.domain.CellMetaData;
import com.wo.domain.CellMetaData.CellColumnType;
import com.wo.domain.attendance.DailySummaryStatus;
import com.wo.domain.attendance.EmployeeDailySummary;
import com.wo.domain.attendance.EmployeeMaster;
import com.wo.domain.attendance.EmployeeMonthlySummary;
import com.wo.domain.attendance.ErrorCell;
import com.wo.repository.EmployeeDailySummaryRepository;
import com.wo.repository.EmployeeMasterRepository;
import com.wo.repository.EmployeeMonthlySummaryRepository;

@Service
public class ReadExcelSheetServiceImpl implements ReadExcelSheetService {

	private final Logger logger = LoggerFactory.getLogger(ReadExcelSheetServiceImpl.class);
	private static final String dateRangeRegex = "^\\d{1,2}-[a-zA-Z]{3}-\\d{4}\\s-\\s\\d{1,2}-[a-zA-Z]{3}-\\d{4}$";
	private static final String dateRegex = "^\\d{1,2}-[a-zA-Z]{3}-\\d{4}$";
	private final List<CellMetaData> cellMetadataList = new ArrayList<>();
//	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	private DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
	private XSSFCellStyle style = null;
	private CreationHelper factory = null;
	private List<ErrorCell> cellError = new ArrayList<>();
	
	@Autowired
	EmployeeMasterRepository masterRepository;
	@Autowired
	EmployeeDailySummaryRepository dailySummaryRepository;
	@Autowired
	EmployeeMonthlySummaryRepository monthlySummaryRepository;

	@Override
	public void readExcel(String path) {
		try {
			File file = new File(path);
			FileInputStream fileInputStream = new FileInputStream(file);

			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			
			factory = workbook.getCreationHelper();
			style = workbook.createCellStyle();
			style.setFillBackgroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.DIAMONDS);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			cellMetadataList.clear();
			cellError.clear();
			computeColumnMetadata(sheet);

			importSheetData(sheet, workbook);
			
			String updatedSheetName = path.substring(0, path.lastIndexOf('.'));
			String sheetExtension = path.substring(path.lastIndexOf('.'));
			updatedSheetName = updatedSheetName + "-styled";
			updatedSheetName = updatedSheetName + sheetExtension;
			logger.info(sheetExtension);
			FileOutputStream fileOutputStream = new FileOutputStream(new File(updatedSheetName));
			workbook.write(fileOutputStream);
		} catch (Exception e) {
			logger.error("Error while reading file---{}", e);
		}
	}

	private void computeColumnMetadata(XSSFSheet sheet) {
		String metadataText = "";
//		logger.info("cell>>{}  -- row.getFirstCellNum()--{}", sheet.getRow(2).getLastCellNum(), sheet.getRow(2).getFirstCellNum());
		XSSFRow row = sheet.getRow(2);
		int lastCellNumber = row.getLastCellNum();
		Optional<CellRangeAddress> cellRange = isCellInMergedRegion(row, lastCellNumber);
		if (cellRange.isPresent()) {
//			logger.info("cellRange.get().getLastColumn()--{}", cellRange.get().getLastColumn());
			lastCellNumber = cellRange.get().getLastColumn();
		}
		for (int i = 0; i <= lastCellNumber; i++) {
			Cell cell = row.getCell(i);
			CellMetaData cellMetaData = new CellMetaData();
			if (cell == null && cellRange.isPresent()) {
//				logger.info("cell --- is merged region...");
			}
			
			cellMetaData.setColumnIndex(i);
			try {
				if (cell != null && cell.getRichStringCellValue() != null && !cell.getRichStringCellValue().toString().isEmpty()) {
					String cellValue = cell.getRichStringCellValue().toString();
					metadataText = cellValue;
					if (cellValue.matches(dateRangeRegex)) {
						cellMetaData.setMonthlySummary(true);
						cellMetaData.setMonthRange(cellValue);
					} else if (cellValue.matches(dateRegex)) {
						cellMetaData.setDailySummary(true);
						cellMetaData.setDate(cellValue);
					}
				} else {
					if (metadataText != null && !metadataText.isEmpty()) {
						if (metadataText.matches(dateRangeRegex)) {
							cellMetaData.setMonthlySummary(true);
							cellMetaData.setMonthRange(metadataText);
						} else if (metadataText.matches(dateRegex)) {
							cellMetaData.setDailySummary(true);
							cellMetaData.setDate(metadataText);
						}
					} else {
						cellMetaData.setEmployeeMaster(true);
					}
				}

				String columnValue = sheet.getRow(3).getCell(i).getRichStringCellValue().toString();
				logger.error("columnValue -> {}", columnValue);
				if (columnValue != null && !columnValue.isEmpty() && CellColumnType.valueOfLabel(columnValue) != null) {
					CellColumnType columnType = CellColumnType.valueOfLabel(columnValue);
					cellMetaData.setCellColumnType(columnType);
				}
				cellMetadataList.add(cellMetaData);
			} catch (Exception e) {
				logger.error("Exception while computing columns metadata -> {}", e);
			}
		}
		logger.info("cellMetadataList.size::::{}", cellMetadataList.size());
		cellMetadataList.forEach(System.out::println);
	}

	private Optional<CellRangeAddress> isCellInMergedRegion(XSSFRow row, int i) {
		Optional<CellRangeAddress> cellRangeAddress = row.getSheet().getMergedRegions().stream()
				.filter(obj -> (obj.containsColumn(i) && obj.containsRow(row.getRowNum()))).findFirst();
		return cellRangeAddress;
	}

	private void importSheetData(XSSFSheet sheet, XSSFWorkbook workbook) {
		boolean isRowBlank = true;
		for (int rowIterator = 0; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
			if (rowIterator <= 3) {
				continue;
			}
			XSSFRow row = sheet.getRow(rowIterator);
			if (row == null || !row.cellIterator().hasNext()) {
				break;
			}
			EmployeeMaster employeeMaster = new EmployeeMaster();

			Map<String, EmployeeDailySummary> dailySummaryMap = new HashedMap<>();
			EmployeeMonthlySummary employeeMonthlySummary = new EmployeeMonthlySummary();

			for (Iterator cellIterator = row.cellIterator(); cellIterator.hasNext();) {
				Cell cell = (Cell) cellIterator.next();
				if (!CellType.BLANK.equals(cell.getCellType())) {
					isRowBlank = false;
				}
				CellMetaData cellMetaData = cellMetadataList.stream()
						.filter(data -> (data.getColumnIndex() == cell.getColumnIndex())).findAny().get();

				EmployeeDailySummary dailySummary = null;
				if (cellMetaData.getDate() != null && !cellMetaData.getDate().isEmpty()) {
					if (dailySummaryMap.containsKey(cellMetaData.getDate())) {
						dailySummary = dailySummaryMap.get(cellMetaData.getDate());
					} else {
						dailySummary = new EmployeeDailySummary();
						dailySummaryMap.put(cellMetaData.getDate(), dailySummary);
					}
					dailySummary.setDate(cellMetaData.getDate());
				} else if (cellMetaData.getMonthRange() != null && !cellMetaData.getMonthRange().isEmpty()) {
					employeeMonthlySummary.setMonthDateRange(cellMetaData.getMonthRange());
				}
				mapDataInEntity(cell, cellMetaData, employeeMaster, employeeMonthlySummary, dailySummary, sheet,
						workbook, row.getRowNum());
			}
			if (isRowBlank) {
				logger.info("row is blank>>{}", row.getRowNum());
				break;
			}
			populateDataInDB(employeeMaster, dailySummaryMap.values(), employeeMonthlySummary);
		}

	}

	private void mapDataInEntity(Cell cell, CellMetaData cellMetaData, EmployeeMaster employeeMaster,
			EmployeeMonthlySummary employeeMonthlySummary, EmployeeDailySummary employeeDailySummary, XSSFSheet sheet, XSSFWorkbook workbook, int rowIdx) {
//		logger.info("cell.getCellType() >>{}", cell.getCellType());
		if (CellType.BLANK.equals(cell.getCellType()) || (CellType.STRING.equals(cell.getCellType())
				&& (cell.getRichStringCellValue() == null || cell.getRichStringCellValue().toString().isEmpty()
						|| "No Summary".equalsIgnoreCase(cell.getRichStringCellValue().toString())))) {
			return;
		}
		switch (cellMetaData.getCellColumnType()) {
		case EMPLOYEE_NUMBER:
			if (CellType.STRING.equals(cell.getCellType())) {
				employeeMaster.setEmployeeNumber(cell.getRichStringCellValue().toString());
			} else if (CellType.NUMERIC.equals(cell.getCellType())) {
				DecimalFormat df = new DecimalFormat("#");
				employeeMaster.setEmployeeNumber(df.format(cell.getNumericCellValue()));
			} else {
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in String or Numeric format", cell.toString()));
			}
			break;
		case EMPLOYEE_NAME:
			employeeMaster.setEmployeeName(cell.getRichStringCellValue().toString());
			break;
		case JOB_TITLE:
			employeeMaster.setJobTitle(cell.getRichStringCellValue().toString());
			break;
		case DEPARTMENT:
			employeeMaster.setDepartment(cell.getRichStringCellValue().toString());
			break;
		case LOCATION:
			employeeMaster.setLocation(cell.getRichStringCellValue().toString());
			break;
		case REPORTING_MANAGER:
			employeeMaster.setReportingManager(cell.getRichStringCellValue().toString());
			break;
		case TOTAL_GROSS_HOURS:
				employeeMonthlySummary.setTotalGrossHours(cell.getRichStringCellValue().toString());
			break;
		case AVG_GROSS_HOURS:
			try {
				Time time = new Time(timeFormatter.parse(cell.getRichStringCellValue().toString()).getTime());
				employeeMonthlySummary.setAvgGrossHours(time);
			} catch (Exception e) {
				addComment(cell, workbook, sheet, rowIdx, "Error parsing AVG_GROSS_HOURS time");
				logger.error("Error parsing AVG_GROSS_HOURS time >>{}", e);
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in duration format", cell.toString()));
			}
			break;
		case TOTAL_EFFECTIVE_HOURS:
			employeeMonthlySummary.setTotalEffectiveHours(cell.getRichStringCellValue().toString());
			break;
		case AVG_EFFECTIVE_HOURS:
			try {
				Time time = new Time(timeFormatter.parse(cell.getRichStringCellValue().toString()).getTime());
				employeeMonthlySummary.setAvgEffectiveHours(time);
			} catch (Exception e) {
				addComment(cell, workbook, sheet, rowIdx, "Error parsing AVG_EFFECTIVE_HOURS time");
				logger.error("Error parsing AVG_EFFECTIVE_HOURS time >>{}", e);
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in duration format", cell.toString()));
			}
			break;
		case LEAVE:
			try {
				employeeMonthlySummary.setLeave(cell.getNumericCellValue());
			} catch (Exception e) {
				logger.error("Error parsing LEAVE time >>{}", e);
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in String or Numeric format", cell.toString()));
			}
			break;
		case IN:
			try {
				Time time = new Time(timeFormatter.parse(cell.getRichStringCellValue().toString()).getTime());
				employeeDailySummary.setInTime(time);
			} catch (Exception e) {
				addComment(cell, workbook, sheet, rowIdx, "Error parsing IN time");
				logger.error("Error parsing IN time >>{}", e);
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in duration format", cell.toString()));
			}
			break;
		case OUT:
			try {
				Time time = new Time(timeFormatter.parse(cell.getRichStringCellValue().toString()).getTime());
				employeeDailySummary.setOutTime(time);
			} catch (Exception e) {
				addComment(cell, workbook, sheet, rowIdx, "Error parsing OUT time");
				logger.error("Error parsing OUT time >>{}", e);
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in duration format", cell.toString()));
			}
			break;
		case EFFECTIVE_HOURS:
			try {
				Time time = new Time(timeFormatter.parse(cell.getRichStringCellValue().toString()).getTime());
				employeeDailySummary.setEffectiveHours(time);
			} catch (Exception e) {
				addComment(cell, workbook, sheet, rowIdx, "Error parsing EFFECTIVE_HOURS time");
				logger.error("Error parsing EFFECTIVE_HOURS time >>{}", e);
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in duration format", cell.toString()));
			}
			break;
		case GROSS_HOURS:
			try {
				Time time = new Time(timeFormatter.parse(cell.getRichStringCellValue().toString()).getTime());
				employeeDailySummary.setGrossHours(time);
			} catch (Exception e) {
				addComment(cell, workbook, sheet, rowIdx, "Error parsing GROSS_HOURS time");
				logger.error("Error parsing GROSS_HOURS time >>{}", e);
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in duration format", cell.toString()));
			}
			break;
		case SHIFT_HOURS:
			try {
				Time time = new Time(timeFormatter.parse(cell.getRichStringCellValue().toString()).getTime());
				employeeDailySummary.setShiftHours(time);
			} catch (Exception e) {
				cell.setCellStyle(style);
				addComment(cell, workbook, sheet, rowIdx, "Error parsing SHIFT_HOURS time");
				logger.error("Error parsing SHIFT_HOURS time >>{}", e);
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in duration format", cell.toString()));
			}
			break;
		case STATUS:
			try {
				employeeDailySummary.setStatus(DailySummaryStatus.valueOfLabel(cell.getRichStringCellValue().toString()));
			} catch (Exception e) {
				cellError.add(new ErrorCell(cell.getAddress().formatAsString(), "Value is not in matching Status's!", cell.toString()));
			}
			break;
		default:
			break;
		}
	}
	
	private void populateDataInDB(EmployeeMaster employeeMaster, Collection<EmployeeDailySummary> collection,
			EmployeeMonthlySummary employeeMonthlySummary) {

		EmployeeMaster dbEmployeeMaster = masterRepository.findByEmployeeNumber(employeeMaster.getEmployeeNumber());
		if (dbEmployeeMaster == null) {
			dbEmployeeMaster = masterRepository.save(employeeMaster);
		}
		for (EmployeeDailySummary dailySummary : collection) {
			dailySummary.setEmployeeMaster(dbEmployeeMaster);
		}
//		employeeDailySummary.forEach(e -> {e.setEmployeeMaster(dbEmployeeMaster);});
//		employeeDailySummary.setEmployeeMaster(dbEmployeeMaster);
		employeeMonthlySummary.setEmployeeMaster(dbEmployeeMaster);
		
		
//		logger.info("employeeMonthlySummary.getDate()>>>{} employeeMaster.getId()>>{}", employeeMonthlySummary.getMonthDateRange(), employeeMaster.getId());
		EmployeeMonthlySummary dbEmployeeMonthlySummary = monthlySummaryRepository.findByMonthDateRangeAndEmployeeMaster_Id(employeeMonthlySummary.getMonthDateRange(), dbEmployeeMaster.getId());
//		logger.info("employee summary from db ---{}", dbEmployeeMonthlySummary);
		if (dbEmployeeMonthlySummary != null) {
			employeeMonthlySummary.setId(dbEmployeeMonthlySummary.getId());
		}
//		logger.info("mkonthly summaries---{}", employeeMonthlySummary);
		
		monthlySummaryRepository.save(employeeMonthlySummary);
		for (EmployeeDailySummary employeeDailySummary : collection) {
			EmployeeDailySummary dbEmployeeDailySummary = dailySummaryRepository.findByDateAndEmployeeMaster_Id(employeeDailySummary.getDate(), dbEmployeeMaster.getId());
			if (dbEmployeeDailySummary != null) {
				employeeDailySummary.setId(dbEmployeeDailySummary.getId());
			}
		}
		dailySummaryRepository.saveAll(collection);
	}
	
    public void addComment(Cell cell, XSSFWorkbook workbook, XSSFSheet sheet, int rowIdx, String commentText) {
        CreationHelper factory = workbook.getCreationHelper();

        ClientAnchor anchor = factory.createClientAnchor();
        //i found it useful to show the comment box at the bottom right corner
        anchor.setCol1(cell.getColumnIndex() + 1); //the box of the comment starts at this given column...
        anchor.setCol2(cell.getColumnIndex() + 3); //...and ends at that given column
        anchor.setRow1(rowIdx + 1); //one row below the cell...
        anchor.setRow2(rowIdx + 5); //...and 4 rows high

        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        Comment comment = drawing.createCellComment(anchor);
        //set the comment text and author
        comment.setString(factory.createRichTextString(commentText));
        comment.setAuthor("SheetExport");

        cell.setCellComment(comment);
        cell.setCellStyle(style);
    }
    
    @Override
    public List<ErrorCell> getCellError() {
		return cellError;
	}
}
