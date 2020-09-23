package aggregator.statements.prepared;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class QueryParser {
	
	private static final String RAW_QUERY_REGEX = "prepareStatement(.+)";
	private Pattern rawQueryPattern = Pattern.compile(RAW_QUERY_REGEX);
	private static final Logger log = LogManager.getLogger(QueryParser.class);
	
	private Queue<StringBuilder> queryQueue;
	private String rawQuery;
	private StringBuilder currentQuery;
	
	public QueryParser(String query) {
		setCurrentQuery(query);
		
	}

	private void setCurrentQuery(String query) {
		
		currentQuery = new StringBuilder();
		if (query != null) {
			Matcher matcher = rawQueryPattern.matcher(query);
			while (matcher.find()) {
				rawQuery = matcher.group(1);
			}
			log.debug("Parsed Query:" + rawQuery);
			
			if (rawQuery != null) {
				currentQuery = new StringBuilder(rawQuery.trim().subSequence(1, rawQuery.length() - 1));
			} 
		}
		queryQueue = new LinkedList<StringBuilder>();
		queryQueue.add(currentQuery);
	}

	public StringBuilder getCurrentQuery() {
		StringBuilder currentQuery = new StringBuilder();
		
		try {
			currentQuery = queryQueue.remove();
		} catch (NoSuchElementException  e) {
			log.error("Current query could not be passed.");
			System.err.println(e);
		}
		return currentQuery;
		
		
	}
	
	protected int getTotalPlaceHoldersInCurrentQuery(StringBuilder currentQuery) {
		int count =0;
		try {
			count =  (currentQuery.length() - currentQuery.toString().replace("?", "").length());
		} catch (NullPointerException ex) {
			log.error(ex.getMessage());
			count =  -1;
		}
		return count;
	}
	
	
}
