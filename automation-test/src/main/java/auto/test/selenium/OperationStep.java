package auto.test.selenium;

public class OperationStep {
	private String stepId;
	private String stepName;
	private String stepValue;
	private String processId;
	private String serial;
	private String operationType;
	private String byType;
	private String byCondition;
	private String failPolicy;

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getStepValue() {
		return stepValue;
	}

	public void setStepValue(String stepValue) {
		this.stepValue = stepValue;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getByType() {
		return byType;
	}

	public void setByType(String byType) {
		this.byType = byType;
	}

	public String getByCondition() {
		return byCondition;
	}

	public void setByCondition(String byCondition) {
		this.byCondition = byCondition;
	}

	public String getFailPolicy() {
		return failPolicy;
	}

	public void setFailPolicy(String failPolicy) {
		this.failPolicy = failPolicy;
	}
}
