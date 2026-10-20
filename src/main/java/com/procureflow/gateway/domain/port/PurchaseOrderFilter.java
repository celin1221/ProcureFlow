package com.procureflow.gateway.domain.port;

import com.procureflow.gateway.domain.model.OrderStatus;

/**
 * Encapsulates canonical query criteria for purchase order searches.
 */
public record PurchaseOrderFilter(
        String clientId,
        String vendorTaxId,
        OrderStatus status,
        Boolean onlyPendingBalance
) {
}
