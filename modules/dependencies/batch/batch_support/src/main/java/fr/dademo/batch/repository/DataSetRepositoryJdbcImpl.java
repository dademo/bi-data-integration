/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.repository;

import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.repository.datamodel.DataSetEntity;
import fr.dademo.batch.repository.datamodel.DataSetEntityRecordMapper;
import fr.dademo.batch.repository.datamodel.DataSetRecord;
import fr.dademo.batch.repository.datamodel.DataSetTable;
import fr.dademo.batch.repository.datamodel.exceptions.DataSetEntityNotFoundException;
import fr.dademo.batch.repository.exceptions.NotAnNumericIdentifierException;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.batch.beans.BeanValues.BATCH_DATA_SOURCE_NAME;
import static fr.dademo.batch.beans.BeanValues.BATCH_DSL_CONTEXT_BEAN_NAME;

@SuppressWarnings("unused")
@Repository
public class DataSetRepositoryJdbcImpl implements DataSetRepository {

    private final DSLContext batchDSLContext;
    private final DataSetTable dataSetTable;

    public DataSetRepositoryJdbcImpl(@Qualifier(BATCH_DSL_CONTEXT_BEAN_NAME) DSLContext batchDSLContext,
                                     BatchDataSourcesConfiguration batchDataSourcesConfiguration) {

        this.batchDSLContext = batchDSLContext;

        final var dataSourceConfiguration = batchDataSourcesConfiguration
            .getJDBCDataSourceConfigurationByName(BATCH_DATA_SOURCE_NAME);

        this.dataSetTable = new DataSetTable(dataSourceConfiguration.getSchema());
    }

    @Override
    public Optional<DataSetEntity> findById(@Nonnull @NotBlank String id) {

        try (final var query = getBaseQuery()) {

            return queryExecutorWrapper(query.where(dataSetTable.FIELD_DATA_SET_ID.eq(toLong(id))))
                .findFirst();
        }
    }

    @Override
    public boolean existsById(@Nonnull @NotBlank String id) {

        try (final var query = batchDSLContext.selectCount()) {

            final var finalQuery = query.from(dataSetTable)
                .where(dataSetTable.FIELD_DATA_SET_ID.eq(toLong(id)));

            return finalQuery
                .fetch()
                .stream()
                .map(Record1::value1)
                .mapToInt(Integer::intValue)
                .sum() > 0;
        }
    }

    @Override
    public DataSetEntity save(DataSetEntity entity) {

        if (!Objects.isNull(entity.getId())) {
            return saveWithId(entity);
        } else {
            return saveWithoutId(entity);
        }
    }

    @Override
    public void delete(DataSetEntity entity) {

        try (final var deleteStatement = batchDSLContext.deleteFrom(dataSetTable)) {

            deleteStatement
                .where(dataSetTable.FIELD_DATA_SET_ID.eq(toLong(entity.getId())))
                .execute();
        }
    }

    @Override
    public Optional<DataSetEntity> findFirstByNameOrderByTimestampDesc(@Nonnull String name) {

        try (final var query = getBaseQuery()) {

            return queryExecutorWrapper(query
                .where(dataSetTable.FIELD_DATA_SET_NAME.eq(name))
                .orderBy(dataSetTable.FIELD_DATA_SET_TIMESTAMP.desc())
            ).findFirst();
        }
    }

    @Override
    public List<DataSetEntity> findByName(@Nonnull String name) {

        try (final var query = getBaseQuery()) {

            return queryExecutorWrapper(query.where(dataSetTable.FIELD_DATA_SET_NAME.eq(name)))
                .collect(Collectors.toList());
        }
    }

    @Override
    public Optional<DataSetEntity> findFirstByNameAndParentOrderByTimestampDesc(@Nonnull String name, @Nonnull @NotBlank String parent) {

        try (final var query = getBaseQuery()) {

            return queryExecutorWrapper(query.where(
                dataSetTable.FIELD_DATA_SET_NAME.eq(name),
                dataSetTable.FIELD_DATA_SET_PARENT.eq(toLong(parent))
            )).findFirst();
        }
    }

    @Override
    public Optional<DataSetEntity> findFirstByNameAndSourceAndSourceSubOrderByTimestampDesc(@Nonnull String name, String source, String sourceSub) {

        try (final var query = getBaseQuery()) {

            return queryExecutorWrapper(query.where(
                dataSetTable.FIELD_DATA_SET_NAME.eq(name),
                dataSetTable.FIELD_DATA_SET_SOURCE.eq(source),
                dataSetTable.FIELD_DATA_SET_SOURCE_SUB.eq(sourceSub)
            )).findFirst();
        }
    }

    private SelectWhereStep<Record> getBaseQuery() {

        return batchDSLContext
            .select(dataSetTable.getFieldsList())
            .from(dataSetTable);
    }

    private Stream<DataSetEntity> queryExecutorWrapper(ResultQuery<? extends Record> query) {

        final var mapper = getMapper();

        return query
            .stream()
            .map(r -> r.into(dataSetTable))
            .map(mapper::map)
            .filter(Objects::nonNull);
    }

    private DataSetEntity saveWithId(DataSetEntity entity) {

        try (final var insertStatement = batchDSLContext
            .insertInto(dataSetTable, dataSetTable.getFieldsList())
            .values(dataSetEntityValuesWithId(entity))) {
            return handleSaveOnDuplicateUpdateStatement(insertStatement, entity);
        }
    }

    private DataSetEntity saveWithoutId(DataSetEntity entity) {

        try (final var insertStatement = batchDSLContext
            .insertInto(dataSetTable, dataSetTable.getFieldsListWithoutId())
            .values(dataSetEntityValuesWithoutId(entity))) {
            return handleSaveOnDuplicateUpdateStatement(insertStatement, entity);
        }
    }

    private DataSetEntity handleSaveOnDuplicateUpdateStatement(InsertValuesStepN<DataSetRecord> insertStatement, DataSetEntity entity) {

        try (final var onDuplicateUpdateStatement = handleDuplicationOptions(insertStatement, entity)) {
            return queryExecutorWrapper(onDuplicateUpdateStatement.returning(dataSetTable.getFieldsList()))
                .findFirst()
                .orElseThrow(DataSetEntityNotFoundException::new);
        }
    }

    private Object[] dataSetEntityValuesWithoutId(DataSetEntity dataSetEntity) {

        return new Object[]{
            dataSetEntity.getName(),
            dataSetEntity.getParent(),
            dataSetEntity.getSource(),
            dataSetEntity.getSourceSub(),
            dataSetEntity.getState().name(),
            dataSetEntity.getTimestamp()
        };
    }

    private Object[] dataSetEntityValuesWithId(DataSetEntity dataSetEntity) {
        return Stream.concat(
            Stream.of(dataSetEntity.getId()),
            Arrays.stream(dataSetEntityValuesWithoutId(dataSetEntity))
        ).toArray();
    }

    @SuppressWarnings("resource")
    private InsertOnDuplicateSetMoreStep<DataSetRecord> handleDuplicationOptions(InsertValuesStepN<DataSetRecord> insertStatement, DataSetEntity entity) {

        return insertStatement
            .onDuplicateKeyUpdate()
            .set(dataSetTable.FIELD_DATA_SET_STATE, entity.getState().name())
            .set(dataSetTable.FIELD_DATA_SET_TIMESTAMP, entity.getTimestamp());
    }

    private Long toLong(String value) {

        try {
            return Optional.ofNullable(value)
                .map(Long::parseLong)
                .orElse(null);
        } catch (NumberFormatException ex) {
            throw new NotAnNumericIdentifierException(ex, value);
        }
    }

    private RecordMapper<DataSetRecord, DataSetEntity> getMapper() {
        return new DataSetEntityRecordMapper(dataSetTable);
    }
}
