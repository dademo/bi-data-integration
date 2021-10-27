package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.jobs.stg.association.datamodel.AssociationTable.ASSOCIATION;
import static fr.dademo.bi.companies.tools.DefaultAppBeans.STG_DSL_CONTEXT;

@Component
public class AssociationJdbcWriter implements ItemWriter<Association> {

    private static final Logger LOGGER = Logger.getLogger(AssociationJdbcWriter.class);

    @Autowired
    @Qualifier(STG_DSL_CONTEXT)
    @Getter
    private DSLContext dslContext;

    @SneakyThrows
    @Override
    public void write(List<? extends Association> items) {

        LOGGER.info(String.format("Writing %d association documents", items.size()));

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(ASSOCIATION,
                ASSOCIATION.FIELD_ASSOCIATION_ID,
                ASSOCIATION.FIELD_ASSOCIATION_ID_EX,
                ASSOCIATION.FIELD_ASSOCIATION_SIRET,
                ASSOCIATION.FIELD_ASSOCIATION_GESTION,
                ASSOCIATION.FIELD_ASSOCIATION_CREATION_DATE,
                ASSOCIATION.FIELD_ASSOCIATION_PUBLICATION_DATE,
                ASSOCIATION.FIELD_ASSOCIATION_NATURE,
                ASSOCIATION.FIELD_ASSOCIATION_GROUPEMENT,
                ASSOCIATION.FIELD_ASSOCIATION_TITLE,
                ASSOCIATION.FIELD_ASSOCIATION_OBJECT,
                ASSOCIATION.FIELD_ASSOCIATION_SOCIAL_OBJECT_1,
                ASSOCIATION.FIELD_ASSOCIATION_SOCIAL_OBJECT_2,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_1,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_2,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_3,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_INSEE_CODE,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE,
                ASSOCIATION.FIELD_ASSOCIATION_LEADER_CIVILITY,
                //ASSOCIATION.FIELD_ASSOCIATION_PHONE,
                ASSOCIATION.FIELD_ASSOCIATION_WEBSITE,
                //ASSOCIATION.FIELD_ASSOCIATION_EMAIL,
                ASSOCIATION.FIELD_ASSOCIATION_OBSERVATION,
                ASSOCIATION.FIELD_ASSOCIATION_POSITION,
                ASSOCIATION.FIELD_ASSOCIATION_RUP_CODE,
                ASSOCIATION.FIELD_ASSOCIATION_LAST_UPDATED
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null));

        items.stream()
                .map(this::companyBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final int totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info(String.format("%d rows affected", totalUpdated));
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> companyBind(Association association) {

        return items -> items.bind(
                association.getId(),
                association.getIdEx(),
                association.getSiret(),
                association.getGestion(),
                association.getCreationDate(),
                association.getPublicationDate(),
                association.getNature(),
                association.getGroupement(),
                association.getTitle(),
                association.getObject(),
                association.getSocialObject1(),
                association.getSocialObject2(),
                association.getAddress1(),
                association.getAddress2(),
                association.getAddress3(),
                association.getAddressPostalCode(),
                association.getAddressInseeCode(),
                association.getAddressCityLibelle(),
                association.getLeaderCivility(),
                //association.getTelephone(),
                association.getWebsite(),
                //association.getEmail(),
                association.getObservation(),
                association.getPosition(),
                association.getRupCode(),
                association.getLastUpdated()
        );
    }
}
