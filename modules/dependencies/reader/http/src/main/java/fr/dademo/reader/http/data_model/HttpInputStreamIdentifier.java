package fr.dademo.reader.http.data_model;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import lombok.*;
import okhttp3.RequestBody;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URL;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class HttpInputStreamIdentifier implements InputStreamIdentifier<URL> {

    @Nonnull
    private URL url;

    @Nonnull
    @Builder.Default
    private String method = "GET";

    @Nullable
    private RequestBody requestBody;

    @Nonnull
    @Override
    public URL getSource() {
        return getUrl();
    }

    public void setSource(@Nonnull URL url) {
        this.url = url;
    }

    @Nonnull
    @Override
    public String getDescription() {
        return String.format("Http input stream of `%s` using method `%s`", getUrl(), getMethod());
    }
}