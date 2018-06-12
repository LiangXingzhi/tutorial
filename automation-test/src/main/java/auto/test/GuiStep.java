package auto.test;

public class GuiStep {
	private String elementTag;
	private String elementType;
	private String elementText;
	private String elementClass;
	private Action action;

	public String getElementTag() {
		return elementTag;
	}

	public void setElementTag(String elementTag) {
		this.elementTag = elementTag;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getElementText() {
		return elementText;
	}

	public void setElementText(String elementText) {
		this.elementText = elementText;
	}

	public String getElementClass() {
		return elementClass;
	}

	public void setElementClass(String elementClass) {
		this.elementClass = elementClass;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;

	public static enum Action {
		click, dblclick, value
	}
}
