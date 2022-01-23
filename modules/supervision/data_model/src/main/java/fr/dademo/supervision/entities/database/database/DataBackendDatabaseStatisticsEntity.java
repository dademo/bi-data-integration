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

package fr.dademo.supervision.entities.database.database;

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
@Table(name = "data_backend_database_statistics")
public class DataBackendDatabaseStatisticsEntity implements Serializable {

    private static final long serialVersionUID = -1134026069481113531L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_database", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendDatabaseEntity database;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_backend_state_execution", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendStateExecutionEntity backendStateExecution;


    @Nullable
    @Min(0)
    @Column(name = "memory_usage_bytes", updatable = false)
    private Long memoryUsageBytes;

    @Nullable
    @Min(0)
    @Column(name = "cpu_usage_millicpu", updatable = false)
    private Long cpuUsageMilliCPU;

    @Nullable
    @Min(0)
    @Column(name = "commits_count", updatable = false)
    private Long commitCounts;

    @Nullable
    @Min(0)
    @Column(name = "rollbacks_count", updatable = false)
    private Long rollbacksCounts;

    @Nullable
    @Min(0)
    @Column(name = "buffer_blocks_read", updatable = false)
    private Long bufferBlocksRead;

    @Nullable
    @Min(0)
    @Column(name = "disk_blocks_read", updatable = false)
    private Long diskBlocksRead;

    @Nullable
    @Min(0)
    @Column(name = "returned_rows_count", updatable = false)
    private Long returnedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "fetched_rows_count", updatable = false)
    private Long fetchedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "inserted_rows_count", updatable = false)
    private Long insertedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "updated_rows_count", updatable = false)
    private Long updatedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "deleted_rows_count", updatable = false)
    private Long deletedRowsCount;

    @Nullable
    @Min(0)
    @Column(name = "conflicts_count", updatable = false)
    private Long conflictsCount;

    @Nullable
    @Min(0)
    @Column(name = "dead_locks_count", updatable = false)
    private Long deadlocksCount;

    @Nullable
    @Column(name = "read_time_milliseconds", updatable = false)
    private Long readTimeMilliseconds;

    @Nullable
    @Column(name = "write_time_milliseconds", updatable = false)
    private Long writeTimeMilliseconds;
}