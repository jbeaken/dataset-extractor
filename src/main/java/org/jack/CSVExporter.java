package org.jack;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class CSVExporter implements AutoCloseable {

    private Repository repository;

    private Properties props;

    private BufferedWriter writer;

    private CSVPrinter csvPrinter;

    public CSVExporter() throws IOException, SQLException {

        log.info("**** Booting com.example.CSVExporter, loading property file and db repository.....");

        loadProperties();

        repository = new Repository( props );

        String filepath = props.getProperty("filepath");

        log.info("Opening file {} for writing.....", filepath);

        writer = Files.newBufferedWriter(Paths.get( filepath ));

        log.info("**** CSVExporter successfully booted!!");
    }

    private void loadProperties() throws IOException {
        props = new Properties();

        InputStream inputStream = getClass().getResourceAsStream("/db.properties");

        props.load( inputStream );
    }

    public void exportCSV() throws IOException, SQLException {

        int offset = 0;

        int pageSize = 10;

        ResultSet rs = null;

        while(true) {
            rs = repository.getResultSet(offset, pageSize);

            if (offset != 0 && offset % 1000 == 0) {
                log.debug("No of rows processed {}", offset);
            }

            if (rs.isBeforeFirst()) {
                print(rs);
            } else break;

            offset += pageSize;
        }

        rs.close();
        log.info("Finished writing csv");
    }


    private void print(ResultSet rs) throws IOException, SQLException {

        if(csvPrinter == null) {
            log.info("Building CSVPrinter with headers");
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(rs));
        }

        csvPrinter.printRecords( rs );
    }


    @Override
    public void close() throws Exception {
        csvPrinter.close( true );

        writer.close();

        repository.close();
    }
}
