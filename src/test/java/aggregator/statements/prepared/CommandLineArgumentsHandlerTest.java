package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CommandLineArgumentsHandlerTest {

	@Test
	public void whenLessThanMinimumCommandLineArgumentsAreSent_parse_shouldThrow_IllegalArgumentException() throws Exception {
		String [] args = new String[] {"-input=jdbc.log"};
		CommandLineArgumentsHandler cli = new CommandLineArgumentsHandler();
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(()->cli.parse(args)).withMessageContaining("You have not provided me all the arguments i need..");
	}

	@Test
	public void whenAllCommandLineArgumentsAreSent_parse_shouldReturn_AllCommandLineArguments() throws Exception {
		String [] args = new String[] {"-input=server.log","-output=jdbc.log","-package=jboss.jdbc.spy"};
		CommandLineArgumentsHandler cli = new CommandLineArgumentsHandler();
		Map<String, String> commandLineArgumentsMap = new HashMap<String, String>();
		commandLineArgumentsMap = cli.parse(args);

		assertThat(commandLineArgumentsMap).containsEntry("input", "server.log");
		assertThat(commandLineArgumentsMap.get("input")).isEqualTo("server.log");
		
		assertThat(commandLineArgumentsMap).containsEntry("package", "jboss.jdbc.spy");
		assertThat(commandLineArgumentsMap.get("package")).isEqualTo("jboss.jdbc.spy");
	}
	
	
	
	
}
