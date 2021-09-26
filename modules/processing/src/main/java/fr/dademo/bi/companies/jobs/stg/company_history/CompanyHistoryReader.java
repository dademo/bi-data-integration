package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import fr.dademo.bi.companies.repositories.entities.HashDefinition;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.reader.RecordReader;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static fr.dademo.bi.companies.jobs.stg.company_history.entities.CompanyHistoryEntity.HEADER;

@ApplicationScoped
public class CompanyHistoryReader implements RecordReader<CSVRecord> {

    private static final Logger LOGGER = Logger.getLogger(CompanyHistoryReader.class);
    private static final String DATASET_URL = "https://files.data.gouv.fr/insee-sirene/StockEtablissementHistorique_utf8.zip";
    private static final String DATASET_SHA256 = "80B73F9742564B030D97D090B44A7E6F85AF992E9EB80A44AC3AC4E4DC232B43";

    private final AtomicLong recordNumber = new AtomicLong(0L);

    @Inject
    HttpDataQuerier httpDataQuerier;

    private ZipArchiveInputStream archiveInputStream;
    private Iterator<CSVRecord> iterator = Collections.emptyIterator();

    @Override
    public void open() throws Exception {

        LOGGER.info("Reading values");
        // Querying for values
        var queryUrl = new URL(DATASET_URL);

        archiveInputStream = new ZipArchiveInputStream(httpDataQuerier.basicQuery(queryUrl, Collections.singletonList(
                HashDefinition.of(DATASET_SHA256, "SHA-256")
        )));
    }

    @Override
    public void close() throws Exception {
        archiveInputStream.close();
    }

    @Override
    public Record<CSVRecord> readRecord() {

        return nextItem()
                .map(this::toRecord)
                .orElse(null);
    }

    private Record<CSVRecord> toRecord(CSVRecord item) {
        return new GenericRecord<>(generateHeader(recordNumber.getAndIncrement()), item);
    }

    private Header generateHeader(@Nullable Long recordNumber) {

        return new Header(
                recordNumber,
                DATASET_URL,
                LocalDateTime.now()
        );
    }

    @SneakyThrows
    private synchronized Optional<CSVRecord> nextItem() {

        if (iterator.hasNext()) {
            return Optional.of(iterator.next());
        } else {
            while (true) {
                ArchiveEntry archiveEntry;
                if ((archiveEntry = archiveInputStream.getNextEntry()) != null) {
                    if (!archiveEntry.isDirectory()) {
                        iterator = getCsvStreamIterator();
                        if (iterator.hasNext()) {
                            return Optional.of(iterator.next());
                        }
                    }
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @SneakyThrows
    private Iterator<CSVRecord> getCsvStreamIterator() {

        return csvFormat()
                .parse(new InputStreamReader(archiveInputStream))
                .iterator();
    }

    private CSVFormat csvFormat() {

        return CSVFormat.DEFAULT.builder()
                .setHeader(HEADER)
                .setSkipHeaderRecord(true)
                .setDelimiter(",")
                .setRecordSeparator("\n")
                .setNullString("")
                .build();
    }
}