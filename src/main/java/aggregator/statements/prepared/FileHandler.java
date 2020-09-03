package aggregator.statements.prepared;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FileHandler {
		
	private static final Logger log = LogManager.getLogger(FileHandler.class);
	private static final String SET_STMTS_REGEX = "set";
	private static final String POSITION_REGEX = "[1-9]+";
	
	public FileHandler() {
		//FIX_ME:Do we need anything here?
		
	}
	
	public boolean fileExists(String fileName) {
		File inputFile = new File(fileName);
		return inputFile.exists();
	}
	
	
	public void aggregatePreparedStatements(String inputFile, String outputFile,String packageName) {
		Path file = Paths.get(inputFile);
		try {
			InputStream in = Files.newInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			int count = 0;
			
			StringBuilder aggregatedPreparedStatements = new StringBuilder();
			Queue<String> linesContainingSubstitutions = new LinkedList<String>();
			while((line = reader.readLine()) != null) {
				if(line.contains(packageName) && line.contains("prepareStatement")) {
					aggregatedPreparedStatements.append(line);
					count = getTotalPlaceHolderCountInCurrentLine(line);
					while(count > 0) {
						getValuesForPlaceHolderInCurrentLine(line);
						linesContainingSubstitutions.add(reader.readLine());
						
						--count;
					}
				}
			}
		} catch(IOException ex) {
			System.err.println(ex);
		}

	}

	protected Map<Integer,String> getValuesForPlaceHolderInCurrentLine(String line) {
		
		Map<Integer,String> placeHolderToValueMap = new HashMap<Integer, String>();
		
		if ( line !=null ) {
			String [] tokens = line.split(" ");
			String statement = tokens[tokens.length-1];
			String setterStatement  = statement.split(",")[0];
			String valueToSet = statement.split(",")[1];
			int lastIdxOfClosingBracket = valueToSet.lastIndexOf(')');
			int firstIdxOfOpeningBracket = setterStatement.lastIndexOf('(');
			String value = valueToSet.substring(0,lastIdxOfClosingBracket);
			Integer placeHolder = Integer.parseInt(setterStatement.substring(0, firstIdxOfOpeningBracket));
			placeHolderToValueMap.put(placeHolder, value);
		}
		
		
		return placeHolderToValueMap;
	}

	protected int getTotalPlaceHolderCountInCurrentLine(String line) {
		int count =0;
		try {
			count =  (line.length() - line.replace("?", "").length());
		} catch (NullPointerException ex) {
			log.error(ex.getMessage());
		}
		return count;
	}

}
