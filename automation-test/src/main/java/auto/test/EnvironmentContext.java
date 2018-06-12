package auto.test;

import com.ericsson.ct.cloud.nfvi.selenium.Procedure;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.ct.cloud.nfvi.util.TypeBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 *
 */
@ConfigurationProperties(prefix = "nfvi")
@Component
public class EnvironmentContext {

	private String driver;
	private List<Procedure> procedures;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public List<Procedure> getProcedures() {
		return procedures;
	}

	public void setProcedures(List<Procedure> procedures) {
		this.procedures = procedures;
	}

}
