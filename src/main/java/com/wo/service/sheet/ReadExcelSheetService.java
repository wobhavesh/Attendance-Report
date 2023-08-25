package com.wo.service.sheet;

import java.util.List;

import com.wo.domain.attendance.ErrorCell;

public interface ReadExcelSheetService {
	public void readExcel(String path);

	public List<ErrorCell> getCellError();
}
