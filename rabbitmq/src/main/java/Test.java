import org.apache.commons.lang3.StringEscapeUtils;

public class Test {
	public static void main(String[] args) {
		System.out.println(":" + new String("\\n") + ":");
		System.out.println(":" + new String("\\\n") + ":");
		
		System.out.println(StringEscapeUtils.escapeJava("\n"));
	}
}
