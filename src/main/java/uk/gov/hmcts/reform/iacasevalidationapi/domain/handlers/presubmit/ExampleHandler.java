package uk.gov.hmcts.reform.iacasevalidationapi.domain.handlers.presubmit;

import static java.util.Objects.requireNonNull;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.iacasevalidationapi.domain.entities.AsylumCase;
import uk.gov.hmcts.reform.iacasevalidationapi.domain.entities.ccd.Event;
import uk.gov.hmcts.reform.iacasevalidationapi.domain.entities.ccd.callback.Callback;
import uk.gov.hmcts.reform.iacasevalidationapi.domain.entities.ccd.callback.PreSubmitCallbackResponse;
import uk.gov.hmcts.reform.iacasevalidationapi.domain.entities.ccd.callback.PreSubmitCallbackStage;
import uk.gov.hmcts.reform.iacasevalidationapi.domain.handlers.PreSubmitCallbackHandler;

@Component
public class ExampleHandler implements PreSubmitCallbackHandler<AsylumCase> {

    public boolean canHandle(
        PreSubmitCallbackStage callbackStage,
        Callback<AsylumCase> callback
    ) {
        requireNonNull(callbackStage, "callbackStage must not be null");
        requireNonNull(callback, "callback must not be null");

        return callbackStage == PreSubmitCallbackStage.ABOUT_TO_SUBMIT
               && callback.getEvent() == Event.START_APPEAL;
    }

    public PreSubmitCallbackResponse<AsylumCase> handle(
        PreSubmitCallbackStage callbackStage,
        Callback<AsylumCase> callback
    ) {
        if (!canHandle(callbackStage, callback)) {
            throw new IllegalStateException("Cannot handle callback");
        }

        AsylumCase asylumCase =
            callback
                .getCaseDetails()
                .getCaseData();

        // does nothing -- will be replaced by story requirements

        PreSubmitCallbackResponse<AsylumCase> callbackResponse =
            new PreSubmitCallbackResponse<>(asylumCase);

        return callbackResponse;
    }
}
