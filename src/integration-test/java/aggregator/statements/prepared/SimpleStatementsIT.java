package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class SimpleStatementsIT {

	@Test
	public void whenIncompleteArgumentsAreSent_programShouldThrow_IllegalArgumentException() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("java","target/aggregator.statements.prepared-jar-with-dependencies.jar");
		System.out.println(pb.directory(new File("target")));
		Process p = pb.start();
		OutputStream os = p.getOutputStream();
		
	}

}

