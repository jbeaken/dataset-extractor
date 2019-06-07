import org.jack.CSVExporter;
import org.jack.Repository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CSVExporterTest {

    private Repository repository;


    @Before
    public void init() throws SQLException {

        repository = mock(Repository.class);

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next()).thenReturn(true).thenReturn(false);

//        when( repository.getResultSet(0, 1000) ).thenReturn( resultSet);
    }


    @Test
    public void getRecords() throws IOException, SQLException {

//        CSVExporter csvExporter = new CSVExporter(repository);
//
//        csvExporter.exportCSV();
    }
}