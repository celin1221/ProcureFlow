package com.procureflow.gateway.reconciliation.engine.rules;

import com.procureflow.gateway.domain.model.DivergenceType;
import com.procureflow.gateway.domain.model.OrderStatus;
import com.procureflow.gateway.domain.model.PurchaseOrder;
import com.procureflow.gateway.reconciliation.engine.ReconciliationContext;
import com.procureflow.gateway.reconciliation.engine.ReconciliationRule;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(30)
public class OrderStatusRule implements ReconciliationRule {

    @Override
    public boolean evaluate(ReconciliationContext context) {
        PurchaseOrder order = context.getPurchaseOrder();
        if (order == null) {
            return true;
        }

        if (order.getStatus() == OrderStatus.BLOCKED) {
            context.addDivergence(
                    DivergenceType.ORDER_BLOCKED,
                    null,
                    null,
                    "Pedido de compra encontra-se bloqueado para recebimento ou faturamento",
                    OrderStatus.OPEN.name(),
                    OrderStatus.BLOCKED.name(),
                    null
            );
        } else if (order.getStatus() == OrderStatus.CLOSED) {
            context.addDivergence(
                    DivergenceType.ORDER_CLOSED,
                    null,
                    null,
                    "Pedido de compra encontra-se encerrado",
                    OrderStatus.OPEN.name(),
                    OrderStatus.CLOSED.name(),
                    null
            );
        }

        return true;
    }
}
