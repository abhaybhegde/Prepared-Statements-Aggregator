package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class FileHandlerTest {

	private static final String SQL_STMT_WITH_SEVEN_PLACEHOLDERS = "(insert into table (columun1,column2,column3,column4,column5,column6,column7) values (?,?,?,?,?,?,?))";
	private static final String SQL_STMT_WITH_ONE_PLACEHOLDERS = "(insert into table (columun1) values (?))";

	@Test
	public void givenSQLStatmentWithSevenPlaceHolders_getTotalPlaceHolderCountInCurrentLine_shouldReturn_Seven() throws Exception {
		FileHandler file = new FileHandler();
		assertThat(file.getTotalPlaceHolderCountInCurrentLine(SQL_STMT_WITH_SEVEN_PLACEHOLDERS)).isEqualTo(7);
	}


	@Test
	public void forNullInput_getTotalPlaceHolderCountInCurrentLine_shouldReturn_zero() throws Exception {
		FileHandler file = new FileHandler();
		assertThat(file.getTotalPlaceHolderCountInCurrentLine(null)).isEqualTo(0);
	}
	
	@Test
	public void givenSQLStatmentWithOnePlaceHolders_getTotalPlaceHolderCountInCurrentLine_shouldReturn_One() throws Exception {
		FileHandler file = new FileHandler();
		assertThat(file.getTotalPlaceHolderCountInCurrentLine(SQL_STMT_WITH_ONE_PLACEHOLDERS)).isEqualTo(1);
	}

	
	@Test
	public void testName() throws Exception {
		String line = "setString(1,ValueOne)";
		Map<Integer,String> positionToValueMap = new HashMap<Integer, String>();
		FileHandler file = new FileHandler();
		positionToValueMap = file.getValuesForPlaceHolderInCurrentLine(line);
		assertThat(positionToValueMap).containsEntry(1,"ValueOne");
	}
	
	
}
