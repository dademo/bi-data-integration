/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal;

import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaViewStatisticsDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;

import javax.annotation.Nonnull;
import java.util.Date;

@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public final class DataBackendDatabaseSchemaViewStatisticsRepresentationCollectionModelAssembler extends AbstractDataBackendDatabaseSchemaViewStatisticsRepresentationModelAssembler {

    private final Long viewId;
    private final Date from;
    private final Date to;

    @Nonnull
    public CollectionModel<DataBackendDatabaseSchemaViewStatisticsDto> toCollectionModel(@Nonnull Iterable<DataBackendDatabaseSchemaViewStatisticsDto> entities) {

        return CollectionModel.of(
            entities,
            getLinks()
        );
    }
}