package aggregator.statements.prepared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLineArgumentsHandler {
	
	private List<String> commandLineArguments;
	private final static int MIN_CLI_OPTIONS=3;

	
	

	public Map<String,String> parse(String [] args) throws IllegalArgumentException{
		
		int totalCommandLineArguments = args.length;
		
		if(args == null  || totalCommandLineArguments <MIN_CLI_OPTIONS) {
			throw new IllegalArgumentException("You have not provided me all the arguments i need..");
		}
		
		Map<String,String> commandLineArgumentsMap = new HashMap<String,String>();
		

		for(int i=0;i<totalCommandLineArguments;++i) {
			String argument = args[i];
			if(argument != null  && argument.contains(CommandLineOptions.INPUT_FILE)) {
				String inputFilePath = argument.replaceFirst("-", "").split("=")[1];
				commandLineArgumentsMap.put(CommandLineOptions.INPUT_FILE, inputFilePath);
			}
			if(argument != null  && argument.contains(CommandLineOptions.OUTPUT_FILE)) {
				String outPutFilePath = argument.replaceFirst("-", "").split("=")[1];
				commandLineArgumentsMap.put(CommandLineOptions.OUTPUT_FILE, outPutFilePath);
			}
			if(argument != null && argument.contains(CommandLineOptions.PACKAGE_NAME)) {
				String packageName = argument.replaceFirst("-","").split("=")[1];
				commandLineArgumentsMap.put(CommandLineOptions.PACKAGE_NAME, packageName);
			}
		}
		return commandLineArgumentsMap;
	}
	

}
