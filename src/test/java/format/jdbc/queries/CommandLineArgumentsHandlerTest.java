package format.jdbc.queries;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import aggregator.statements.prepared.CommandLineArgumentsHandler;

public class CommandLineArgumentsHandlerTest {



	@Test
	public void whenLessThan2CommandLineArgumentsAreSent_parse_shouldThrow_IllegalArgumentException() throws Exception {
		String [] args = new String[] {"-input=jdbc.log"};
		CommandLineArgumentsHandler cli = new CommandLineArgumentsHandler();
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(()->cli.parse(args)).withMessageContaining("You have not provided me all the arguments i need..");
	}
	
	@Test
	public void whenAllCommandLineArgumentsAreSent_parse_shouldReturn_AllCommandLineArguments() throws Exception {
		String [] args = new String[] {"-input=server.log","-output=jdbc.log"};
		CommandLineArgumentsHandler cli = new CommandLineArgumentsHandler();
		Map<String, String> commandLineArgumentsMap = new HashMap<String, String>();
		commandLineArgumentsMap = cli.parse(args);
		assertThat(commandLineArgumentsMap).containsEntry("input", "server.log");
		assertThat(commandLineArgumentsMap.get("input")).isEqualTo("server.log");
	}

	@Test
	public void whenAbsolutePathsAreSent_parseShouldReturn_absolutePath() throws Exception {
		String [] args = new String[] {"-input=/tmp/someDirectory/my_jdbc.log","-output=jdbc.log"};
		CommandLineArgumentsHandler cli = new CommandLineArgumentsHandler();
		Map<String,String> commandLineArgumentsMap =  new HashMap<String, String>();
		commandLineArgumentsMap = cli.parse(args);
		assertThat(commandLineArgumentsMap).containsEntry("input","/tmp/someDirectory/my_jdbc.log");
		assertThat(commandLineArgumentsMap).containsEntry("output","jdbc.log");
	}


}
