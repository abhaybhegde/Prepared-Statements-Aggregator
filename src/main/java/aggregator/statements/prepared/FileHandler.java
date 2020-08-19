package aggregator.statements.prepared;

import java.io.File;

public class FileHandler {
	private String fileName;
	public FileHandler(String fileName) {
		this.fileName = fileName;
		
		
	}
	public boolean fileExists() {
		File inputFile = new File(fileName);
		return inputFile.exists();
	}

}
