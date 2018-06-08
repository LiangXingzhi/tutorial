import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.compress.CompressionPluginType;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.plugins.PluginTypeInterface;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 * Hello world! if need custom plugins, plugins is at the project root folder,
 * /plugins
 */
public class RuleEngine {

	public static final String INPUT_PARAM1 = "p1";
	public static final String INPUT_PARAM2 = "p2";

	/**
	 * 
	 * @param ktrPath
	 * @param p1
	 *            if param is used in kettle, e.g. input file path or output file
	 *            path, etc.
	 * @param p2
	 * @throws KettleException
	 */
	public static void transform(String ktrPath, String p1, String p2) throws KettleException {
		List<PluginTypeInterface> pluginTypes = new ArrayList<PluginTypeInterface>();
		pluginTypes.add(StepPluginType.getInstance());
		pluginTypes.add(CompressionPluginType.getInstance());
		KettleEnvironment.init(pluginTypes, false);
		TransMeta metaData = new TransMeta(ktrPath, false);
		metaData.setParameterValue(INPUT_PARAM1, p1);
		metaData.setParameterValue(INPUT_PARAM2, p2);
		Trans trans = new Trans(metaData);
		trans.setLogLevel(LogLevel.BASIC);
		trans.execute(null);
		trans.waitUntilFinished();
		KettleEnvironment.shutdown();
	}

	public static void main(String[] args) throws KettleException {
		long e1 = LocalDateTime.now().getLong(ChronoField.MILLI_OF_DAY);
		long e2 = LocalDateTime.now().getLong(ChronoField.MILLI_OF_DAY);
		transform("src/main/resources/first_transformation.ktr", "", "");
	}
}