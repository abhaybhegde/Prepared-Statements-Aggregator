package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class QueryParserTest {

	@Test
	public void givenDebugLogContainingQuery_parseShouldReturn_onlyQuery() {
		String query = "2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [Connection] prepareStatement(insert into TableOne (COL_1, COL_2, COL-3) values (?, ?, ?))";
		QueryParser queryParser = new QueryParser(query);
		assertThat(queryParser.getQuery()).isEqualTo("insert into TableOne (COL_1, COL_2, COL-3) values (?, ?, ?)");
	}

}
