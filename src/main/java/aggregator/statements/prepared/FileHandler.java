package aggregator.statements.prepared;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FileHandler {
		
	private static final Logger log = LogManager.getLogger(FileHandler.class);;
	
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
