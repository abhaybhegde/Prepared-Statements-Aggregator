package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import aggregator.statements.prepared.FileHandler;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileHandler.class)
public class FileHandlerTest {

	@Ignore
	@Test
	public void givenAFileThatExists_fileExists_shouldReturnTrue() throws Exception {

		File mockedFile = Mockito.mock(File.class);
		Mockito.when(mockedFile.exists()).thenReturn(true);		
		PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments(Matchers.anyString()).thenReturn(mockedFile);
		FileHandler inputFile = new FileHandler();

//		assertThat(inputFile.fileExists()).isTrue();
	}

	@Ignore
	@Test
	public void givenAFileThatDoesNotExits_fileExists_shouldReturnFalse() throws Exception {

		File mockedFile = Mockito.mock(File.class);
		Mockito.when(mockedFile.exists()).thenReturn(false);		
		PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments(Matchers.anyString()).thenReturn(mockedFile);
//		FileHandler inputFile = new FileHandler("fileName.log");

//		assertThat(inputFile.fileExists()).isFalse();

	}


}
