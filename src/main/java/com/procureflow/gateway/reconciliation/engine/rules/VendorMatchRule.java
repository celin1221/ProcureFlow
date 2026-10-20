package com.procureflow.gateway.reconciliation.engine.rules;

import com.procureflow.gateway.domain.model.DivergenceType;
import com.procureflow.gateway.domain.model.PurchaseOrder;
import com.procureflow.gateway.domain.model.Vendor;
import com.procureflow.gateway.reconciliation.engine.ReconciliationContext;
import com.procureflow.gateway.reconciliation.engine.ReconciliationRule;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(20)
public class VendorMatchRule implements ReconciliationRule {

    @Override
    public boolean evaluate(ReconciliationContext context) {
        PurchaseOrder order = context.getPurchaseOrder();
        if (order == null || order.getVendor() == null) {
            return true;
        }

        String invoiceTaxId = Vendor.normalizeTaxId(context.getRequest().vendorTaxId());
        String orderTaxId = Vendor.normalizeTaxId(order.getVendor().taxId());

        if (!orderTaxId.equals(invoiceTaxId)) {
            context.addDivergence(
                    DivergenceType.VENDOR_MISMATCH,
                    null,
                    null,
                    "CNPJ do vendor na invoice ('" + invoiceTaxId + "') não confere com o vendor acordado no purchase order ('" + orderTaxId + "')",
                    orderTaxId,
                    invoiceTaxId,
                    null
            );
        }

        return true;
    }
}
