package com.wo.domain.attendance;

import java.util.HashMap;
import java.util.Map;

public enum DailySummaryStatus {
	Present("P","Present"),
	MissingSwipe("MS","MissingSwipe"),
	Holiday("H","Holiday"),
	WeeklyOff("WO","WeeklyOff"),
	Absent("A","Absent"),
	PendingApproval("PA","PendingApproval"),
	leave("L","leave"),
	WorkFromHome("WFH","WorkFromHome"),
	OnDuty("OD","OnDuty"),
	WorkedOnHoiday("WOH","WorkedOnHoiday"),
	NotAvalable("NA","NotAvalable"),
	Sickleave("SL","Sickleave"),
	Paidleave("PL","Paidleave"),
	UnpaidLeave("UL","Unpaid Leave"),
	FloaterLeave("FL","FloaterLeave"),
	Specialleave("SPL","Specialleave"),
	Matemityleave("ML","Matemityleave"),
	Patemityleave("PT","Patemityleave"),
	Bereavementleave("BL","Bereavementleave"),
	CasualSickleave("C/SL","CasualSickleave"),
	CompOffs("CO","CompOffs"),
	Mamiageleave("ML1","Mamiageleave"),
	Shortleave("SL1","Shortleave"),
	Officialleave("OL","Officialleave"),
	Holidayleave("HL","Holidayleave"),
	Holiday2023("HL23","Holiday 2023-24");

	public final String label;
	public final String desc;
	private static final Map<String, DailySummaryStatus> BY_LABEL = new HashMap<>();

	private DailySummaryStatus(String label, String desc) {
		this.label = label;
		this.desc = desc;
	}

	static {
		for (DailySummaryStatus e : values()) {
			BY_LABEL.put(e.label, e);
		}
	}

	public static DailySummaryStatus valueOfLabel(String label) {
		return BY_LABEL.get(label);
	}
}
