package de.vnr.pxf.service.contract.application.port.resource;

import de.vnr.pxf.service.contract.domain.model.Order;
import java.util.Optional;
import java.util.UUID;

public interface OrderPort {

  Optional<Order> loadById(UUID id);

  void insert(Order order);
}
