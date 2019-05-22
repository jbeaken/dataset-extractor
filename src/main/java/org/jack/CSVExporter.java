package org.jack;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class CSVExporter {

    private Repository repository;

    private Properties props;

    public CSVExporter() throws IOException, SQLException {

        log.info("Booting com.example.CSVExporter, loading property file and db repository.....");

        loadProperties();

        repository = new Repository( props );

        log.info("...success!!");
    }

    private void loadProperties() throws IOException {
        props = new Properties();
        FileInputStream fis = new FileInputStream("/home/git/datasetextractor/src/main/resources/db.properties");
        props.load( fis );
    }

    public void exportCSV() throws IOException, SQLException {

      ResultSet rs = repository.getResultSet();

      print( rs );

      rs.close();
    }


    private void print(ResultSet rs) throws IOException, SQLException {

        String filepath = props.getProperty("filepath");
        BufferedWriter writer = Files.newBufferedWriter(Paths.get( filepath ));

        log.info("Writing csv to file {}", filepath);

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader( rs ));

        csvPrinter.printRecords( rs );

        csvPrinter.close( true );

        log.info("Finished writing csv");

    }


}
