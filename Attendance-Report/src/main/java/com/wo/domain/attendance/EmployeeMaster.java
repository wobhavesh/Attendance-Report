package com.wo.domain.attendance;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wo.domain.Entity;

@javax.persistence.Entity
@Table(name = "employee_master")
public class EmployeeMaster implements Entity {

	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    @Column(name = "employee_number")
    private String employeeNumber;
    @Column(name = "employee_name")
    private String employeeName;
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "department")
    private String department;
    @Column(name = "location")
    private String location;
    @Column(name = "reporting_manager")
    private String reportingManager;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_date", insertable=false, updatable=false)
    private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modification_date", insertable=false, updatable=false)
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

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReportingManager() {
        return reportingManager;
    }

    public void setReportingManager(String reportingManager) {
        this.reportingManager = reportingManager;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmployeeMaster{");
        sb.append("id=").append(id);
        sb.append(", employeeNumber='").append(employeeNumber).append('\'');
        sb.append(", employeeName='").append(employeeName).append('\'');
        sb.append(", jobTitle='").append(jobTitle).append('\'');
        sb.append(", department='").append(department).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", reportingManager='").append(reportingManager).append('\'');
        sb.append(", creationDate=").append(creationDate);
        sb.append(", modificationDate=").append(modificationDate);
        sb.append('}');
        return sb.toString();
    }
}
