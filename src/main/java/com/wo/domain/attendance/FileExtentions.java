package com.wo.domain.attendance;

import java.util.HashMap;
import java.util.Map;

public enum FileExtentions {

	XLSX(".xlsx"),
	XLS(".xls");

	public final String label;
	private static final Map<String, FileExtentions> BY_LABEL = new HashMap<>();

	private FileExtentions(String label) {
		this.label = label;
	}

	static {
		for (FileExtentions e : values()) {
			BY_LABEL.put(e.label, e);
		}
	}

	public static FileExtentions valueOfLabel(String label) {
		return BY_LABEL.get(label);
	}
}
