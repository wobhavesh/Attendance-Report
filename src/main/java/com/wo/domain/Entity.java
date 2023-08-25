package com.wo.domain;

import java.io.Serializable;
import java.util.Date;

public interface Entity extends Serializable {
    public long getId();
    public Date getCreationDate();
    public Date getModificationDate();
}
