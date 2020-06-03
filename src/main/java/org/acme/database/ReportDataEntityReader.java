package org.acme.database;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import java.io.File;
import java.util.List;

/**
 * Reads ReportDataEntity objects from a CSV file with header
 */
public class ReportDataEntityReader {
   public static List<ReportDataEntity> readFile(File csvFile) throws Exception {
        MappingIterator<ReportDataEntity> entityIter = new CsvMapper().readerWithTypedSchemaFor(ReportDataEntity.class).readValues(csvFile);

        return entityIter.readAll();
   }
}
