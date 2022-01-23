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

package fr.dademo.supervision.entities.database.globaldatabase;

import fr.dademo.supervision.entities.DataBackendStateExecutionEntity;
import fr.dademo.supervision.entities.database.connection.DataBackendDatabaseConnectionEntity;
import fr.dademo.supervision.entities.database.database.DataBackendDatabaseEntity;
import lombok.*;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
@Table(name = "data_backend_global_database")
public class DataBackendGlobalDatabaseEntity implements Serializable {

    private static final long serialVersionUID = 2888789316495194833L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_backend_state_execution", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private DataBackendStateExecutionEntity backendStateExecution;

    @Nonnull
    @OneToMany(mappedBy = "globalDatabase", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<DataBackendDatabaseConnectionEntity> connections;

    @Nonnull
    @OneToMany(mappedBy = "globalDatabase", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<DataBackendDatabaseEntity> databases;
}