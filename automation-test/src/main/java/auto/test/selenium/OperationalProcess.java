package auto.test.selenium;

import java.util.List;

public class OperationalProcess {
	private String processId;
	private String processName;
	
	private List<OperationStep> steps;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public List<OperationStep> getSteps() {
		return steps;
	}

	public void setSteps(List<OperationStep> steps) {
		this.steps = steps;
	}
}
