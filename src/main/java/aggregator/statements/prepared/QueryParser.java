package aggregator.statements.prepared;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
	
	private static final String RAW_QUERY_REGEX = "prepareStatement(.+)";
	private Pattern rawQueryPattern = Pattern.compile(RAW_QUERY_REGEX);
	
	private Queue<StringBuilder> queryQueue = new LinkedList<StringBuilder>();
	private String rawQuery;
	
	public QueryParser(String query) {
		Matcher matcher = rawQueryPattern.matcher(query);
		while(matcher.find()) {
			rawQuery = matcher.group();
		}
		System.out.println(rawQuery);
		
	}

	public String getQuery() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
