package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ArgumentParserTest {

	@Test
	public void givenASetterMethod_ArgumentParser_shouldReturn_parsedStatement() {
		String setStatement = "2020-08-28 00:20:50,333 DEBUG [jboss.jdbc.spy] (default-threads - 17) java:/ABC.DS [PreparedStatement] setString(1, Narendra)";
		ArgumentParser argParser = new ArgumentParser(setStatement);
		String resutl = argParser.parse();
		assertThat(resutl).isEqualTo("setString 1 Narendra");
	
	}

}
