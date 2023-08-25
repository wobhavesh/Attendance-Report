package com.wo.domain.attendance;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wo.domain.Entity;

@javax.persistence.Entity
@Table(name = "employee_daily_summary")
public class EmployeeDailySummary implements Entity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_master_id")
	private EmployeeMaster employeeMaster;
	@Column(name = "in_time")
	private Time inTime;
	@Column(name = "out_time")
	private Time outTime;
	@Column(name = "effective_hours")
	private Time effectiveHours;
	@Column(name = "gross_hours")
	private Time grossHours;
	@Column(name = "shift_hours")
	private Time shiftHours;
	@Column(name = "date")
	private String date;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private DailySummaryStatus status;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", insertable = false, updatable = false)
	private Date creationDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_date", insertable = false, updatable = false)
	private Date modificationDate;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public Date getModificationDate() {
		return modificationDate;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public EmployeeMaster getEmployeeMaster() {
		return employeeMaster;
	}
	
	public void setEmployeeMaster(EmployeeMaster employeeMaster) {
		this.employeeMaster = employeeMaster;
	}

	public Time getInTime() {
		return inTime;
	}

	public void setInTime(Time inTime) {
		this.inTime = inTime;
	}

	public Time getOutTime() {
		return outTime;
	}

	public void setOutTime(Time outTime) {
		this.outTime = outTime;
	}

	public Time getEffectiveHours() {
		return effectiveHours;
	}

	public void setEffectiveHours(Time effectiveHours) {
		this.effectiveHours = effectiveHours;
	}

	public Time getGrossHours() {
		return grossHours;
	}

	public void setGrossHours(Time grossHours) {
		this.grossHours = grossHours;
	}

	public Time getShiftHours() {
		return shiftHours;
	}

	public void setShiftHours(Time shiftHours) {
		this.shiftHours = shiftHours;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public DailySummaryStatus getStatus() {
		return status;
	}
	
	public void setStatus(DailySummaryStatus status) {
		this.status = status;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EmployeeDailySummary{");
		sb.append("id=").append(id);
		sb.append(", inTime=").append(inTime);
		sb.append(", outTime=").append(outTime);
		sb.append(", effectiveHours=").append(effectiveHours);
		sb.append(", grossHours=").append(grossHours);
		sb.append(", shiftHours=").append(shiftHours);
		sb.append(", creationDate=").append(creationDate);
		sb.append(", modificationDate=").append(modificationDate);
		sb.append('}');
		return sb.toString();
	}
}
