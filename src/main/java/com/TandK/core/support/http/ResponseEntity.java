//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.TandK.core.support.http;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

public class ResponseEntity<T> extends HttpEntity<T> {

    private Object status;

    public ResponseEntity(){

    }

    public ResponseEntity(HttpStatus status) {
        this((T)null, (MultiValueMap)null, (HttpStatus)status);
    }

    public ResponseEntity(@Nullable T body, HttpStatus status) {
        this(body, (MultiValueMap)null, (HttpStatus)status);
    }

    public ResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        this((T)null, headers, (HttpStatus)status);
    }

    public ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status) {
        this(body, headers, (Object)status);
    }

    public ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, int rawStatus) {
        this(body, headers, (Object)rawStatus);
    }

    private ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, Object status) {
        super(body, headers);
        Assert.notNull(status, "HttpStatus must not be null");
        this.status = status;
    }

    public HttpStatus getStatusCode() {
        return this.status instanceof HttpStatus ? (HttpStatus)this.status : HttpStatus.valueOf((Integer)this.status);
    }

    public int getStatusCodeValue() {
        return this.status instanceof HttpStatus ? ((HttpStatus)this.status).value() : (Integer)this.status;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else if (!super.equals(other)) {
            return false;
        } else {
            ResponseEntity<?> otherEntity = (ResponseEntity)other;
            return ObjectUtils.nullSafeEquals(this.status, otherEntity.status);
        }
    }

    @Override
    public int hashCode() {
        return 29 * super.hashCode() + ObjectUtils.nullSafeHashCode(this.status);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<");
        builder.append(this.status);
        if (this.status instanceof HttpStatus) {
            builder.append(' ');
            builder.append(((HttpStatus)this.status).getReasonPhrase());
        }

        builder.append(',');
        T body = this.getBody();
        HttpHeaders headers = this.getHeaders();
        if (body != null) {
            builder.append(body);
            builder.append(',');
        }

        builder.append(headers);
        builder.append('>');
        return builder.toString();
    }

    public static ResponseEntity.BodyBuilder status(HttpStatus status) {
        Assert.notNull(status, "HttpStatus must not be null");
        return new ResponseEntity.DefaultBuilder(status);
    }

    public static ResponseEntity.BodyBuilder status(int status) {
        return new ResponseEntity.DefaultBuilder(status);
    }

    public static ResponseEntity.BodyBuilder ok() {
        return status(HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> ok(@Nullable T body) {
        return ok().body(body);
    }

    public static <T> ResponseEntity<T> of(Optional<T> body) {
        Assert.notNull(body, "Body must not be null");
        return (ResponseEntity)body.map(ResponseEntity::ok).orElseGet(() -> {
            return notFound().build();
        });
    }

    public static ResponseEntity.BodyBuilder created(URI location) {
        return (ResponseEntity.BodyBuilder)status(HttpStatus.CREATED).location(location);
    }

    public static ResponseEntity.BodyBuilder accepted() {
        return status(HttpStatus.ACCEPTED);
    }

    public static ResponseEntity.HeadersBuilder<?> noContent() {
        return status(HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity.BodyBuilder badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity.HeadersBuilder<?> notFound() {
        return status(HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity.BodyBuilder unprocessableEntity() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ResponseEntity.BodyBuilder internalServerError() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static class DefaultBuilder implements ResponseEntity.BodyBuilder {
        private final Object statusCode;
        private final HttpHeaders headers = new HttpHeaders();

        public DefaultBuilder(Object statusCode) {
            this.statusCode = statusCode;
        }

        @Override
        public ResponseEntity.BodyBuilder header(String headerName, String... headerValues) {
            String[] var3 = headerValues;
            int var4 = headerValues.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String headerValue = var3[var5];
                this.headers.add(headerName, headerValue);
            }

            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder headers(@Nullable HttpHeaders headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }

            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder headers(Consumer<HttpHeaders> headersConsumer) {
            headersConsumer.accept(this.headers);
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet(Arrays.asList(allowedMethods)));
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder eTag(String etag) {
            if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
                etag = "\"" + etag;
            }

            if (!etag.endsWith("\"")) {
                etag = etag + "\"";
            }

            this.headers.setETag(etag);
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder lastModified(ZonedDateTime date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder lastModified(Instant date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder lastModified(long date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder location(URI location) {
            this.headers.setLocation(location);
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder cacheControl(CacheControl cacheControl) {
            this.headers.setCacheControl(cacheControl);
            return this;
        }

        @Override
        public ResponseEntity.BodyBuilder varyBy(String... requestHeaders) {
            this.headers.setVary(Arrays.asList(requestHeaders));
            return this;
        }

        @Override
        public <T> ResponseEntity<T> build() {
            return this.body((T)null);
        }

        @Override
        public <T> ResponseEntity<T> body(@Nullable T body) {
            return new ResponseEntity(body, this.headers, this.statusCode);
        }
    }

    public interface BodyBuilder extends ResponseEntity.HeadersBuilder<ResponseEntity.BodyBuilder> {
        ResponseEntity.BodyBuilder contentLength(long var1);

        ResponseEntity.BodyBuilder contentType(MediaType var1);

        <T> ResponseEntity<T> body(@Nullable T var1);
    }

    public interface HeadersBuilder<B extends ResponseEntity.HeadersBuilder<B>> {
        B header(String var1, String... var2);

        B headers(@Nullable HttpHeaders var1);

        B headers(Consumer<HttpHeaders> var1);

        B allow(HttpMethod... var1);

        B eTag(String var1);

        B lastModified(ZonedDateTime var1);

        B lastModified(Instant var1);

        B lastModified(long var1);

        B location(URI var1);

        B cacheControl(CacheControl var1);

        B varyBy(String... var1);

        <T> ResponseEntity<T> build();
    }
}
