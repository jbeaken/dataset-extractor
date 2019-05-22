package org.jack;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String... args) {


        try {

            log.info("Starting csv exporter...");

            CSVExporter csvExporter = new CSVExporter();

            csvExporter.exportCSV();

            log.info("...all done!");

        } catch (Exception e) {
            log.error("Exception during export", e);
        }

    }



}
