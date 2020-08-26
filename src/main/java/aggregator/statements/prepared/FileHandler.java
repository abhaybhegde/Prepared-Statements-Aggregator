package aggregator.statements.prepared;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
		
	private String fileName;
	
	public FileHandler() {
		
		
		
	}
	public boolean fileExists(String fileName) {
		File inputFile = new File(fileName);
		return inputFile.exists();
	}
	
	
	public void aggregatePreparedStatements(String inputFile, String outputFile) {
		Path file = Paths.get(inputFile);
		try {
			InputStream in = Files.newInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = reader.readLine()) != null) {
				if(line.contains(CommandLineOptions.PACKAGE_NAME)) {
					System.out.println(line);
				}
			}
		} catch(IOException ex) {
			System.err.println(ex);
		}

	}

}
