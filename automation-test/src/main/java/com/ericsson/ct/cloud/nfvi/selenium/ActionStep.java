package com.ericsson.ct.cloud.nfvi.selenium;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionStep {

    Logger logger = LoggerFactory.getLogger(ActionStep.class);

    private String action;

    private String by;

    private String target;

    private String value;

    private String name;
    
    private String failPolicy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	public String getFailPolicy() {
		return failPolicy;
	}

	public void setFailPolicy(String failPolicy) {
		this.failPolicy = failPolicy;
	}
}
