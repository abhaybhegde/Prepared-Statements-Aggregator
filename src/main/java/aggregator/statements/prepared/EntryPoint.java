package aggregator.statements.prepared;

import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EntryPoint {
	
	private static final Logger log = LogManager.getLogger(EntryPoint.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();
		CommandLineArgumentsHandler cliHandler = new CommandLineArgumentsHandler();
		try {
			Map<String,String> commandLineArgumentsMap = cliHandler.parse(args);
			String inputFile = commandLineArgumentsMap.get(CommandLineOptions.INPUT_FILE);
			String outputFile = commandLineArgumentsMap.get(CommandLineOptions.OUTPUT_FILE);
			FileHandler file = new FileHandler();
			file.aggregatePreparedStatements(inputFile,outputFile);
		} catch (IllegalArgumentException e) {
			log.debug(e.getMessage(),e);
			System.err.println("Usage: java -jar <program_name> -input=<path_to_jdbc_log> -output=<output_file>");
		}

	}

}
