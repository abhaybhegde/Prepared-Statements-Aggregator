package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void givenDebugLogContainingQuery_parseShouldReturn_onlyQuery() {
		String query = "2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [Connection] prepareStatement(insert into TableOne (COL_1, COL_2, COL-3) values (?, ?, ?))";
		String query_2 = "2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [Connection] prepareStatement(select COL_1 from Table_1 where COL_1 IN ( ?, ?, ?))"; 
		QueryParser queryParser = new QueryParser(query);
		queryParser = new QueryParser(query_2);
		
		assertThat(queryParser.getCurrentQuery().toString()).isEqualTo("select COL_1 from Table_1 where COL_1 IN ( ?, ?, ?)");
	}

}
