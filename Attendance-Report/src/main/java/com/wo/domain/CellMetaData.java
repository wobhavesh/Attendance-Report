package com.wo.domain;

import java.util.HashMap;
import java.util.Map;

public class CellMetaData {

	public enum CellColumnType {

		EMPLOYEE_NUMBER("Employee Number"),
		EMPLOYEE_NAME("Employee Name"),
		JOB_TITLE("Job Title"),
		DEPARTMENT("Department"),
		LOCATION("Location"),
		REPORTING_MANAGER("Reporting Manager"),
		TOTAL_GROSS_HOURS("Total Gross Hours"),
		AVG_GROSS_HOURS("Avg Gross Hours"),
		TOTAL_EFFECTIVE_HOURS("Total Effective Hours"),
		AVG_EFFECTIVE_HOURS("Avg Effective Hours"),
		LEAVE("Leave"),
		IN("IN"),
		OUT("OUT"),
		EFFECTIVE_HOURS("Effective Hours"),
		GROSS_HOURS("Gross Hours"),
		SHIFT_HOURS("Shift Hours"),
		STATUS("Status");

		public final String label;
		private static final Map<String, CellColumnType> BY_LABEL = new HashMap<>();

		private CellColumnType(String label) {
			this.label = label;
		}

		static {
			for (CellColumnType e : values()) {
				BY_LABEL.put(e.label, e);
			}
		}

		public static CellColumnType valueOfLabel(String label) {
			return BY_LABEL.get(label);
		}
	}

	private int columnIndex;
	private CellColumnType cellColumnType;
	private boolean monthlySummary;
	private boolean dailySummary;
	private boolean employeeMaster;
	private String monthRange;
	private String date;

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public CellColumnType getCellColumnType() {
		return cellColumnType;
	}

	public void setCellColumnType(CellColumnType cellColumnType) {
		this.cellColumnType = cellColumnType;
	}

	public boolean isMonthlySummary() {
		return monthlySummary;
	}

	public void setMonthlySummary(boolean monthlySummary) {
		this.monthlySummary = monthlySummary;
	}

	public boolean isDailySummary() {
		return dailySummary;
	}

	public void setDailySummary(boolean dailySummary) {
		this.dailySummary = dailySummary;
	}

	public boolean isEmployeeMaster() {
		return employeeMaster;
	}

	public void setEmployeeMaster(boolean employeeMaster) {
		this.employeeMaster = employeeMaster;
	}

	public String getMonthRange() {
		return monthRange;
	}

	public void setMonthRange(String monthRange) {
		this.monthRange = monthRange;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CellMetaData [columnIndex=");
		builder.append(columnIndex);
		builder.append(", cellColumnType=");
		builder.append(cellColumnType);
		builder.append(", monthlySummary=");
		builder.append(monthlySummary);
		builder.append(", dailySummary=");
		builder.append(dailySummary);
		builder.append(", employeeMaster=");
		builder.append(employeeMaster);
		builder.append(", monthRange=");
		builder.append(monthRange);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}

}
