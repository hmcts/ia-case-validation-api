package uk.gov.hmcts.reform.iacasevalidationapi.infrastructure.serialization;

public interface Deserializer<T> {

    T deserialize(String source);
}
