package org.jack;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class Extractor {

    public static void main(String... args) throws IOException, SQLException {

        Properties properties = loadProperties();

        Repository repository = new Repository( properties );

        try (  CSVExporter csvExporter = new CSVExporter( properties, repository ) ) {

            log.info("Starting csv exporter...");

            csvExporter.exportCSV();

            log.info("...all done!");

        } catch (Exception e) {
            log.error("Exception during export", e);
        }

    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();

        InputStream inputStream = Extractor.class.getClassLoader().getResourceAsStream("data.extractor.properties");

        properties.load( inputStream );

        return properties;
    }


}
