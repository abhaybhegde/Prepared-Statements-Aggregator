package aggregator.statements.prepared;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class FileHandlerTest {

	private static final String SQL_STMT_WITH_SEVEN_PLACEHOLDERS = "(insert into table (columun1,column2,column3,column4,column5,column6,column7) values (?,?,?,?,?,?,?))";
	private static final String SQL_STMT_WITH_ONE_PLACEHOLDERS = "(insert into table (columun1) values (?))";

	FileHandler file;
	
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
	public void givenAStringContainingSetStatement_getValuesForPlaceHolderInCurrentLine_shouldReturn_positionToValueMap() throws Exception {
		String line = "java:/XYZ.DS [PreparedStatement] setString(1,ValueOne)";
		Map<Integer,String> positionToValueMap = new HashMap<Integer, String>();
		
		positionToValueMap = file.getValuesForPlaceHolderInCurrentLine(line);
		
		assertThat(positionToValueMap).containsEntry(1,"ValueOne");
		assertThat(positionToValueMap.get(1)).isEqualTo("ValueOne");
		
		line = "2020-08-25 03:26:24,845 DEBUG [jboss.jdbc.spy] (default-threads - 40) java:/XYZ.DS [PreparedStatement] setString(27,ValueTwo)";
		positionToValueMap = file.getValuesForPlaceHolderInCurrentLine(line);
		assertThat(positionToValueMap).containsEntry(27,"ValueTwo");
//		assertThat(positionToValueMap.get(27)).isEqualTo("ValueTwo");
	}
	
	
}
