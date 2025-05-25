package de.vnr.pxf.service.contract.application.port.api.view;

import de.vnr.pxf.service.contract.application.port.resource.OrderPort;
import de.vnr.pxf.service.contract.domain.model.Order;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderView {

  private final OrderPort orderPort;

  public Order getById(UUID orderId) {
    return orderPort.loadById(orderId)
        .orElseThrow(() -> new NoSuchElementException("Product with id " + orderId + " not found"));
  }
}
