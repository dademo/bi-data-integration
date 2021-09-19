package fr.dademo.bi.companies.jobs.stg.companies_history;

import io.quarkus.hibernate.orm.PersistenceUnit;
import org.jboss.logging.Logger;

import javax.batch.api.chunk.ItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

import static fr.dademo.bi.companies.jobs.stg.companies_history.BatchJobStep.PERSISTENCE_UNIT_NAME;

@Dependent
@Named(CompaniesHistoryWriter.BEAN_NAME)
public class CompaniesHistoryWriter implements ItemWriter {

    public static final String BEAN_NAME = "CompaniesHistoryWriter";
    private static final Logger LOGGER = Logger.getLogger(CompaniesHistoryWriter.class);
    @Inject
    @PersistenceUnit(PERSISTENCE_UNIT_NAME)
    EntityManager entityManager;

    @Override
    public void open(Serializable checkpoint) throws Exception {
    }

    @Override
    public void close() throws Exception {

    }

    public void writeItems(List<Object> items) throws Exception {
        LOGGER.info(String.format("Writing %d items", items.size()));
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
