package aggregator.statements.prepared;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EntryPoint {
	
	private static final Logger log = LogManager.getLogger(EntryPoint.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();
		CommandLineArgumentsHandler cliHandler = new CommandLineArgumentsHandler();
		try {
			cliHandler.parse(args);
		} catch (IllegalArgumentException e) {
			log.debug(e.getMessage(),e);
			System.err.println("Usage: java -jar <program_name> -input=<path_to_jdbc_log> -output=<output_file>");
		}

	}

}
