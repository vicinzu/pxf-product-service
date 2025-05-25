package de.vnr.pxf.service.contract.application.service;

import de.vnr.pxf.service.contract.application.port.api.usecase.ReceiveOrderUseCase;
import de.vnr.pxf.service.contract.application.port.resource.OfferPort;
import de.vnr.pxf.service.contract.application.port.resource.OrderPort;
import de.vnr.pxf.service.contract.domain.model.Order;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements ReceiveOrderUseCase {

  private final OfferPort offerPort;
  private final OrderPort orderPort;

  @Override
  public UUID receiveOrder(ReceiveOrderCommand command) {
    final var order = new Order(offerPort, command.customerId(), command.offerId());
    orderPort.insert(order);

    return order.getId();
  }
}
