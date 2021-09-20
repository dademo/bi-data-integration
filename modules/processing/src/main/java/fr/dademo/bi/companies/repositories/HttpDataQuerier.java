package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.entities.HttpHashDefinition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public interface HttpDataQuerier {

    default InputStream basicQuery(@Nonnull URL queryUrl) {
        return basicQuery(queryUrl, null, Collections.emptyList());
    }

    default void basicQuery(@Nonnull URL queryUrl, @Nonnull Consumer<InputStream> resultConsumer) {
        basicQuery(queryUrl, null, Collections.emptyList(), resultConsumer);
    }

    InputStream basicQuery(@Nonnull URL queryUrl,
                           @Nullable Duration expiration,
                           @Nonnull List<HttpHashDefinition> httpHashDefinitionList
    );

    void basicQuery(@Nonnull URL queryUrl,
                    @Nullable Duration expiration,
                    @Nonnull List<HttpHashDefinition> httpHashDefinitionList,
                    @Nonnull Consumer<InputStream> resultConsumer
    );

    byte[] basicQueryByte(
            @Nonnull URL queryUrl,
            @Nullable Duration expiration,
            @Nonnull List<HttpHashDefinition> httpHashDefinitionList);
}