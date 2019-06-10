import org.jack.CSVExporter;
import org.jack.Extractor;
import org.jack.Repository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CSVExporterTest {

    private Repository repository;
    private Properties properties;


    @Before
    public void init() throws SQLException, IOException {

        repository = mock(Repository.class);

        properties = loadProperties();

        int noOfRowsInEachOutputFile = Integer.valueOf( properties.getProperty("noOfRowsInEachOutputFile") );

        int noOfRowsInEachDatabaseFetch =  Integer.valueOf( properties.getProperty("noOfRowsInEachDatabaseFetch") );

        int pageSize = noOfRowsInEachOutputFile < noOfRowsInEachDatabaseFetch ? noOfRowsInEachOutputFile : noOfRowsInEachDatabaseFetch;

        List<List<String>> result = new ArrayList<>();

        String[] headers = {"1", "2", "3", "4"};

        List<String> row = Arrays.asList( "a", "b", "c", "d");

        result.add( Collections.unmodifiableList( row ));
        result.add( Collections.unmodifiableList( row ));
        result.add( Collections.unmodifiableList( row ));
        result.add( Collections.unmodifiableList( row ));
        result.add( Collections.unmodifiableList( row ));
        result.add( Collections.unmodifiableList( row ));

        when( repository.getRows(0, pageSize) ).thenReturn(result);

        when( repository.getHeaders() ).thenReturn( headers );
    }


    @Test
    public void getRecords() throws Exception {

        CSVExporter csvExporter = new CSVExporter( properties, repository );

        csvExporter.exportCSV();
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();

        InputStream inputStream = Extractor.class.getClassLoader().getResourceAsStream("data.extractor.properties");

        properties.load( inputStream );

        return properties;
    }
}
