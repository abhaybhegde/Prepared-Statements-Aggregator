package aggregator.statements.prepared;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {
	
	private String rawStatement;
	private StringBuilder currentPreparedStatement;
	private static final String PREPARED_STMT_REGEX = "set(\\w+)\\((\\d+)(,[^,]+)+\\)";
	private Pattern rawPreparedStatement = Pattern.compile(PREPARED_STMT_REGEX);
	
	
	public ArgumentParser(String setStatement) {
		setCurrentPreparedStatement(setStatement);
	}

	private void setCurrentPreparedStatement(String setStatement) {
		currentPreparedStatement = new StringBuilder();
		
		if (setStatement != null) {
			Matcher matcher = rawPreparedStatement.matcher(setStatement);
			
			while(matcher.find()) {
				currentPreparedStatement.append(matcher.group(1)).append(matcher.group(2)).append(matcher.group(3));
			}
			
		}
		System.out.println(currentPreparedStatement.toString());
		
	}

	public String parse() {
		
		return currentPreparedStatement.toString();
	}

}
