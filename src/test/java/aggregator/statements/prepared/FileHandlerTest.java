package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class FileHandlerTest {

	private static final String SQL_STMT_WITH_SEVEN_PLACEHOLDERS = "(insert into table (columun1,column2,column3,column4,column5,column6,column7) values (?,?,?,?,?,?,?))";
	private static final String SQL_STMT_WITH_ONE_PLACEHOLDERS = "(insert into table (columun1) values (?))";
	private static final String PLACE_HOLDER_REGEX = "(\\?)";

	private FileHandler file;
	
	@Before
	public void setUp() {
		file = new FileHandler();
	}
	
	@Test
	public void givenSQLStatmentWithSevenPlaceHolders_getTotalPlaceHolderCountInCurrentLine_shouldReturn_Seven() throws Exception {
		assertThat(file.getTotalPlaceHolderCountInCurrentLine(SQL_STMT_WITH_SEVEN_PLACEHOLDERS)).isEqualTo(7);
	}

	@Test
	public void forNullInput_getTotalPlaceHolderCountInCurrentLine_shouldReturn_zero() throws Exception {
		assertThat(file.getTotalPlaceHolderCountInCurrentLine(null)).isEqualTo(0);
	}
	
	@Test
	public void givenSQLStatmentWithOnePlaceHolders_getTotalPlaceHolderCountInCurrentLine_shouldReturn_One() throws Exception {
		assertThat(file.getTotalPlaceHolderCountInCurrentLine(SQL_STMT_WITH_ONE_PLACEHOLDERS)).isEqualTo(1);
	}
	
	@Test
	public void givenAStringContainingSetStatement_getValuesForParameterIndex_shouldReturn_positionToValueMap() throws Exception {
		String line = "2020-08-25 03:26:24,838 DEBUG [jboss.jdbc.spy] (default-threads - 40) java:/ABC.DS [PreparedStatement] setTimestamp(5, 2012-04-12 07:39:39.117)";
		Map<Integer,String> positionToValueMap = new HashMap<Integer, String>();
		
		positionToValueMap = file.getValuesForParameterIndex(line);
		
		assertThat(positionToValueMap).containsEntry(5,"2012-04-12 07:39:39.117");
		assertThat(positionToValueMap.get(5)).isEqualTo("2012-04-12 07:39:39.117");
		
		line = "2020-08-25 03:26:24,845 DEBUG [jboss.jdbc.spy] (default-threads - 40) java:/XYZ.DS [PreparedStatement] setString(27, )";
		positionToValueMap = file.getValuesForParameterIndex(line);
		assertThat(positionToValueMap).containsEntry(27,"EMPTY_STRING");
		assertThat(positionToValueMap.get(27)).isEqualTo("EMPTY_STRING");
		
		line = "2020-08-25 03:26:24,845 DEBUG [jboss.jdbc.spy] (default-threads - 40) java:/XYZ.DS [PreparedStatement] setString(27, )";
	}
	
	@Test
	public void givenSetStatementsWithPlaceHolders_getPlaceHolder_shouldReturn_placeHolder() throws Exception {
		String setStatements = "setString(345,";
		assertThat(file.getPlaceHolder(setStatements)).isEqualTo(345);
		
		setStatements = "setInt(1,";
		assertThat(file.getPlaceHolder(setStatements)).isEqualTo(1);
		
		setStatements = "setInt(22,";
		assertThat(file.getPlaceHolder(setStatements)).isEqualTo(22);
		
		
	}
	
	@Test
	public void givenSetStatementsWithValues_getValues_shouldReturn_values() throws Exception {
		//setTimestamp(5, 2012-04-12 07:39:39.117)
		Stack<String> setStatements = new Stack<String>();
		setStatements.push("07:39:39.117)");
		setStatements.push("2012-04-12");
		setStatements.push("setTimestamp(5,");
		String result  = file.getValues(setStatements);
		assertThat(result).isEqualTo("2012-04-12 07:39:39.117");
		
		
		setStatements = new Stack<String>();
		setStatements.push(")");
		setStatements.push("setTimestamp(5,");
		String result_2  = file.getValues(setStatements);
		assertThat(result_2).isEqualTo("EMPTY_STRING");
	}
	
	@Test
	public void givenSqlQueryWithPlaceHolders_getQueryWithValuesSubstitued_shouldReturnQueryWithValues() throws Exception {
		StringBuilder aggregatedPreparedStatements = new StringBuilder();
		aggregatedPreparedStatements.append("2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [Connection] prepareStatement(insert into TableOne (COL_1, COL_2, COL-3) values (?, ?, ?))");
		List<Map<Integer, String>> allValues = new ArrayList<Map<Integer,String>>();
		Map<Integer,String> value = new HashMap<Integer, String>();
		value.put(1, "20");
		value.put(2, "EMPTY_STRING");
		value.put(3,"First LastName");
		String result  = file.getQueryWithValuesSubstitued(aggregatedPreparedStatements, allValues );
		assertThat(result).isEqualTo("insert into TableOne (COL_1, COL_2, COL_3) values (20, , \"First LastName\")");
	}
	
	@Test
	public void testName() throws Exception {
		Pattern placeHolder = Pattern.compile(PLACE_HOLDER_REGEX);
		StringBuilder aggregatedPreparedStatements = new StringBuilder();
		aggregatedPreparedStatements.append("2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [Connection] prepareStatement(insert into TableOne (COL_1, COL_2, COL-3) values (?, ?, ?))");
		Matcher matcher = placeHolder.matcher(aggregatedPreparedStatements);
		file.getStartAndEndPosition(matcher, aggregatedPreparedStatements);
	}
	
	
	
}
