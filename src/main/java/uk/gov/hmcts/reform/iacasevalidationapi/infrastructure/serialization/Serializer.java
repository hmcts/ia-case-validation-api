package uk.gov.hmcts.reform.iacasevalidationapi.infrastructure.serialization;

public interface Serializer<T> {

    String serialize(T data);
}
