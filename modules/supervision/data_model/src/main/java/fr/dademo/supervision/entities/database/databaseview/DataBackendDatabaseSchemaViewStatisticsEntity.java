/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.entities.database.databaseview;

import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author dademo
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "data_backend_database_schema_view_statistics")
public class DataBackendDatabaseSchemaViewStatisticsEntity implements Serializable {

    private static final long serialVersionUID = 148773153132763617L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_view", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDatabaseSchemaViewEntity view;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_backend_state_execution", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendStateExecutionEntity backendStateExecution;

    @Nullable
    @Column(name = "rows_count", updatable = false)
    @Min(0)
    private Long rowsCount;

    @Nullable
    @Column(name = "total_size", updatable = false)
    @Min(0)
    private Long totalSize;
}