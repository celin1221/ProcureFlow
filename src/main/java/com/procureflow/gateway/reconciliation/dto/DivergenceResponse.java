package com.procureflow.gateway.reconciliation.dto;

import com.procureflow.gateway.domain.model.DivergenceType;
import com.procureflow.gateway.domain.model.ReconciliationDivergence;

public record DivergenceResponse(
        DivergenceType code,
        Integer lineNumber,
        String materialCode,
        String description,
        String expectedValue,
        String actualValue,
        String difference
) {
    public static DivergenceResponse fromDomain(ReconciliationDivergence d) {
        return new DivergenceResponse(
                d.getCode(),
                d.getLineNumber(),
                d.getMaterialCode(),
                d.getDescription(),
                d.getExpectedValue(),
                d.getActualValue(),
                d.getDifference()
        );
    }
}
