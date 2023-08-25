package com.wo.domain.attendance;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.wo.domain.Entity;

@javax.persistence.Entity
@Table(name = "employee_monthly_summary")
@EntityListeners(AuditingEntityListener.class)
public class EmployeeMonthlySummary implements Entity {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_master_id")
	private EmployeeMaster employeeMaster;
    @Column(name = "total_gross_hours")
    private String totalGrossHours;
    @Column(name = "avg_gross_hours")
    private Time avgGrossHours;
    @Column(name = "total_effective_hours")
    private String totalEffectiveHours;
    @Column(name = "avg_effective_hours")
    private Time avgEffectiveHours;
	@Column(name = "date_range")
	private String monthDateRange;
	@Column(name = "month_leave")
	private double leave;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_date", insertable=false, updatable=false)
    @CreatedDate
    private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modification_date", insertable=false, updatable=false)
    @LastModifiedDate
    private Date modificationDate;

    @Override
    public long getId() {
        return id;
    }

    @Generated(GenerationTime.INSERT)
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

    public String getTotalGrossHours() {
        return totalGrossHours;
    }

    public void setTotalGrossHours(String totalGrossHours) {
        this.totalGrossHours = totalGrossHours;
    }
    
    public EmployeeMaster getEmployeeMaster() {
		return employeeMaster;
	}
    
    public void setEmployeeMaster(EmployeeMaster employeeMaster) {
		this.employeeMaster = employeeMaster;
	}

    public Time getAvgGrossHours() {
        return avgGrossHours;
    }

    public void setAvgGrossHours(Time avgGrossHours) {
        this.avgGrossHours = avgGrossHours;
    }

    public String getTotalEffectiveHours() {
        return totalEffectiveHours;
    }

    public void setTotalEffectiveHours(String totalEffectiveHours) {
        this.totalEffectiveHours = totalEffectiveHours;
    }

    public Time getAvgEffectiveHours() {
        return avgEffectiveHours;
    }

    public void setAvgEffectiveHours(Time avgEffectiveHours) {
        this.avgEffectiveHours = avgEffectiveHours;
    }
    
    public double getLeave() {
		return leave;
	}
    
    public void setLeave(double leave) {
		this.leave = leave;
	}
    
    public String getMonthDateRange() {
		return monthDateRange;
	}
    
    public void setMonthDateRange(String monthDateRange) {
		this.monthDateRange = monthDateRange;
	}

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EmployeeMonthlySummary{");
        sb.append("id=").append(id);
        sb.append(", totalGrossHours=").append(totalGrossHours);
        sb.append(", avgGrossHours=").append(avgGrossHours);
        sb.append(", totalEffectiveHours=").append(totalEffectiveHours);
        sb.append(", avgEffectiveHours=").append(avgEffectiveHours);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", modificationDate=").append(modificationDate);
        sb.append('}');
        return sb.toString();
    }
}
