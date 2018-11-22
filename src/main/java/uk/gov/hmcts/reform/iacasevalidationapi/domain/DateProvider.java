package uk.gov.hmcts.reform.iacasevalidationapi.domain;

import java.time.LocalDate;

public interface DateProvider {

    LocalDate now();
}
