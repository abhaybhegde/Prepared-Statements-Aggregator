package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class QueryParserTest {

	private static final String QUERY_1 = "2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [Connection] prepareStatement(insert into TableOne (COL_1, COL_2, COL-3) values (?, ?, ?))";
	private static final String SQL_STMT_WITH_SEVEN_PLACEHOLDERS = "2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [Connection] prepareStatement(insert into table (columun1,column2,column3,column4,column5,column6,column7) values (?,?,?,?,?,?,?))";
	private static final String QUERY_3 = "2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [Connection] prepareStatement(select * from Table_1 where COL_1 IN ( ?, ?, ?))";
	private static final String QUERY_WITH_NO_PLACEHOLDERS = "2020-08-25 00:56:05,110 DEBUG [jboss.jdbc.spy] (default-threads - 34) java:/ABC.DS [Connection] prepareStatement(select COL.nextval from dual)";
	private static final String SQL_STMT_WITH_ONE_PLACEHOLDERS = "2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [Connection] prepareStatement(select * from Table_1 where COL_1 = ?)";
	
	@Test
	public void givenSetOfQueries_parseShouldReturn_onlyLastQuery() {
		QueryParser queryParser = new QueryParser(QUERY_1);
		queryParser = new QueryParser(SQL_STMT_WITH_SEVEN_PLACEHOLDERS);
		queryParser = new QueryParser(QUERY_3);
		
		assertThat(queryParser.getCurrentQuery().toString()).isEqualTo("select * from Table_1 where COL_1 IN ( ?, ?, ?)");
	}
	
	@Test
	public void givenSQLStatmentWithSevenPlaceHolders_getTotalPlaceHolderCountInCurrentLine_shouldReturn_Seven() throws Exception {
		QueryParser queryParser = new QueryParser(SQL_STMT_WITH_SEVEN_PLACEHOLDERS);
		assertThat(queryParser.getTotalPlaceHoldersInCurrentQuery()).isEqualTo(7);
	}

	@Test
	public void forQueriesWithNoPlaceHolders_getTotalPlaceHoldersInCurrentQuery_shouldReturn_zero() throws Exception {
		QueryParser queryParser = new QueryParser(QUERY_WITH_NO_PLACEHOLDERS);
		assertThat(queryParser.getTotalPlaceHoldersInCurrentQuery()).isEqualTo(0);
	}
	
	@Test
	public void givenSQLStatmentWithOnePlaceHolders_getTotalPlaceHolderCountInCurrentLine_shouldReturn_One() throws Exception {
		QueryParser queryParser = new QueryParser(SQL_STMT_WITH_ONE_PLACEHOLDERS);
		assertThat(queryParser.getTotalPlaceHoldersInCurrentQuery()).isEqualTo(1);
	}
	
	

}
