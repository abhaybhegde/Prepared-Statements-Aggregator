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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FileHandler {
		
	private static final Logger log = LogManager.getLogger(FileHandler.class);
	private static final String SET_STMTS_REGEX = "set";
	private static final String POSITION_REGEX = "[1-9]+";
	private static final String EMPTY_STRING = "EMPTY_STRING";
	
	public FileHandler() {
		//FIX_ME:Do we need anything here?
		
	}
	
	public boolean fileExists(String fileName) {
		File inputFile = new File(fileName);
		return inputFile.exists();
	}
	
	
	public void aggregatePreparedStatements(String inputFile, String outputFile,String packageName) {
		Path file = Paths.get(inputFile);
		BufferedReader reader = null;
		try {
			InputStream in = Files.newInputStream(file);
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			int count = 0;
			
			StringBuilder aggregatedPreparedStatements = new StringBuilder();
			Queue<String> linesContainingSubstitutions = new LinkedList<String>();
			while((line = reader.readLine()) != null) {
				if(line.contains(packageName) && line.contains("prepareStatement")) {
					aggregatedPreparedStatements.append(line);
					count = getTotalPlaceHolderCountInCurrentLine(line);
					while(count > 0) {
						String linesHavingSetterMethods = reader.readLine();
						getValuesForPlaceHolderInCurrentLine(linesHavingSetterMethods);
						--count;
					}
				}
			}
		} catch(IOException ex) {
			System.err.println(ex);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		}

	}

	protected Map<Integer,String> getValuesForPlaceHolderInCurrentLine(String line) {
		
		Map<Integer,String> placeHolderToValueMap = new HashMap<Integer, String>();
		Stack<String > setStatements = new Stack<String>();
		
		if ( line !=null ) {
			String [] tokens = line.split(" ");
			int length = tokens.length;
			int lastCharacterLocation = length -1;
			
			while( lastCharacterLocation > 0) {
				String token = tokens[lastCharacterLocation];
				if(token != null && token.startsWith("set")) {
					setStatements.push(token);
					break;
				}
				if(token.matches("\\)")) {
					setStatements.push(EMPTY_STRING);
				}else {
					setStatements.push(token);
				}
				--lastCharacterLocation;
			}
			Integer placeHolder = getPlaceHolder(setStatements.peek());
			String values = getValues(setStatements);
			placeHolderToValueMap.put(placeHolder, values);
		}
		
		
		return placeHolderToValueMap;
	}

	protected String getValues(Stack<String> setStatements) {

		if(setStatements.isEmpty()) return "";

		if(setStatements.lastElement().isEmpty()) {
			return " ";
		}
		
		Stack<String> arguments = new Stack<String>();
		
		while(!setStatements.isEmpty()) {
			String value = setStatements.pop();
			if(value.startsWith("set") && value.endsWith(",")) {
				continue;
			}
			if ( value.endsWith(")") && value.equalsIgnoreCase(")") ) {
				arguments.push(EMPTY_STRING);
				break;
			}
			arguments.push(value.replace(")", ""));
			
		}
		Iterator<String> iterator = arguments.iterator();
		StringBuilder aggregatedValues = new StringBuilder();
		while(iterator.hasNext()) {
			String value = iterator.next();
			aggregatedValues.append(value);
			aggregatedValues.append(" ");
		}
		
		return aggregatedValues.toString().trim();
	}

	protected Integer getPlaceHolder(String setStatements) {
		int openBraceLocation = setStatements.indexOf("(")+1;
		int commaPosition = setStatements.indexOf(",");
		return Integer.parseInt(setStatements.substring(openBraceLocation, commaPosition));
		
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
