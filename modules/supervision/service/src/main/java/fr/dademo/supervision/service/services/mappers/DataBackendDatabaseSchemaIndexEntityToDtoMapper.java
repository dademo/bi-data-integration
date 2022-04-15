/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services.mappers;

import fr.dademo.supervision.service.repository.views.DataBackendDatabaseSchemaIndexView;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaIndexEntityToDtoMapper {

    DataBackendDatabaseSchemaIndexEntityToDtoMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaIndexEntityToDtoMapper.class);

    @Mapping(source = "source.dataBackendDatabaseSchemaIndexEntity", target = ".")
    @Mapping(source = "source.dataBackendDatabaseSchemaId", target = "dataBackendDatabaseSchemaId")
    @Mapping(source = "source.statisticsCount", target = "statisticsCount")
    DataBackendDatabaseSchemaIndexDto viewToDto(DataBackendDatabaseSchemaIndexView source);
}