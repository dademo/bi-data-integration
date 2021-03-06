/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal;

import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Optional;

import static fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal.*;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.*;

/**
 * @author dademo
 */
@Component
public class CompanyLegalItemMapper implements ItemProcessor<CSVRecord, CompanyLegal> {

    @Override
    public CompanyLegal process(@Nonnull CSVRecord item) {
        return mappedToCompanyHistory(item);
    }

    private CompanyLegal mappedToCompanyHistory(CSVRecord csvRecord) {

        return CompanyLegal.builder()
            .siren(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_SIREN))
            .diffusionStatus(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_DIFFUSION_STATUS))
            .isPurged(Optional.ofNullable(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_IS_PURGED))).orElse(false))
            .creationDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_CREATION_DATE)))
            .acronym(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_ACRONYM))
            .sex(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_SEX))
            .firstName1(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_1))
            .firstName2(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_2))
            .firstName3(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_3))
            .firstName4(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_4))
            .usualFirstName(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_FIRST_NAME))
            .pseudonym(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_PSEUDONYM))
            .associationIdentifier(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_ASSOCIATION_IDENTIFIER))
            .staffNumberRange(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_RANGE))
            .staffNumberYear(toInteger(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_YEAR)))
            .lastProcessing(toLocalDateTime(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_LAST_PROCESSING)))
            .periodsCount(toInteger(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_PERIODS_COUNT)))
            .companyCategory(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY))
            .companyCategoryYear(toInteger(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY_YEAR)))
            .beginDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_BEGIN_DATE)))
            .administrativeState(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_ADMINISTRATIVE_STATE))
            .name(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_NAME))
            .usualName(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_NAME))
            .denomination(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_DENOMINATION))
            .usualDenomination1(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_1))
            .usualDenomination2(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_2))
            .usualDenomination3(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_3))
            .legalCategory(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_LEGAL_CATEGORY))
            .principalActivity(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY))
            .principalActivityNomenclature(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE))
            .headquartersNic(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_HEADQUARTERS_NIC))
            .isSocialAndSolidarityEconomy(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY))
            .isEmployer(csvRecord.get(CSV_FIELD_COMPANY_LEGAL_UNIT_IS_EMPLOYER))
            .build();
    }
}
