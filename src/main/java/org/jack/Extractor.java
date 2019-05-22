package org.jack;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Extractor {

    public static void main(String... args) {


        try (  CSVExporter csvExporter = new CSVExporter() ) {

            log.info("Starting csv exporter...");

            csvExporter.exportCSV();

            log.info("...all done!");

        } catch (Exception e) {
            log.error("Exception during export", e);
        }

    }



}
