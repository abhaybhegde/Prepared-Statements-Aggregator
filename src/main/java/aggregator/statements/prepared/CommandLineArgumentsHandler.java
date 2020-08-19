package aggregator.statements.prepared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLineArgumentsHandler {
	
	private List<String> commandLineArguments;
	
	private static final String INPUT_FILE= "input";
	private static final String OUTPUT_FILE= "output";
	
	

	public Map<String,String> parse(String [] args) throws IllegalArgumentException{
		
		if(args == null  || args.length <2) {
			throw new IllegalArgumentException("You have not provided me all the arguments i need..");
		}
		
		Map<String,String> commandLineArgumentsMap = new HashMap<String,String>();
		

		for(int i=0;i<args.length;++i) {
			String argument = args[i];
			if(argument != null  && argument.contains(INPUT_FILE)) {
				String inputFilePath = argument.replaceFirst("-", "").split("=")[1];
				commandLineArgumentsMap.put(INPUT_FILE, inputFilePath);
			}
			if(argument != null  && argument.contains(OUTPUT_FILE)) {
				String outPutFilePath = argument.replaceFirst("-", "").split("=")[1];
				commandLineArgumentsMap.put(OUTPUT_FILE, outPutFilePath);
			}
		}
		return commandLineArgumentsMap;
	}
	

}
