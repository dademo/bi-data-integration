/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance.writers;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.CompanyInheritanceItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceRecord;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.jooq.InsertOnDuplicateStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.JobDefinition.COMPANY_INHERITANCE_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritanceTable.COMPANY_INHERITANCE;

/**
 * @author dademo
 */
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_INHERITANCE_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class CompanyInheritanceJdbcItemWriterImpl implements CompanyInheritanceItemWriter {

    @Autowired
    @Qualifier(STG_DATA_SOURCE_DSL_CONTEXT_BEAN_NAME)
    @Getter
    private DSLContext dslContext;

    @Override
    public void write(List<? extends CompanyInheritance> items) {

        log.info("Writing {} company inheritance documents", items.size());

        try (final var insertStatement = getInsertStatement()) {

            final var batchInsertStatement = dslContext.batch(insertStatement);

            items.stream()
                .map(this::companyInheritanceBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

            final var batchResult = batchInsertStatement.execute();
            if (batchResult.length > 0) {
                final int totalUpdated = Arrays.stream(batchResult).sum();
                log.info("{} rows affected", totalUpdated);
            } else {
                log.error("An error occurred while running batch");
            }
        }
    }

    @SuppressWarnings("resource")
    private InsertOnDuplicateStep<CompanyInheritanceRecord> getInsertStatement() {

        return dslContext.insertInto(COMPANY_INHERITANCE,
            COMPANY_INHERITANCE.FIELD_COMPANY_PREDECESSOR_SIREN,
            COMPANY_INHERITANCE.FIELD_COMPANY_SUCCESSOR_SIREN,
            COMPANY_INHERITANCE.FIELD_COMPANY_SUCCESSION_DATE,
            COMPANY_INHERITANCE.FIELD_COMPANY_HEADQUARTER_CHANGE,
            COMPANY_INHERITANCE.FIELD_COMPANY_ECONOMICAL_CONTINUITY,
            COMPANY_INHERITANCE.FIELD_COMPANY_PROCESSING_DATE
        ).values((String) null, null, null, null, null, null);
    }

    private Consumer<BatchBindStep> companyInheritanceBind(CompanyInheritance companyInheritance) {

        return items -> items.bind(
            companyInheritance.getCompanyPredecessorSiren(),
            companyInheritance.getCompanySuccessorSiren(),
            companyInheritance.getCompanySuccessionDate(),
            companyInheritance.getCompanyHeaderChanged(),
            companyInheritance.getCompanyEconomicalContinuity(),
            companyInheritance.getCompanyProcessingTimestamp()
        );
    }
}
