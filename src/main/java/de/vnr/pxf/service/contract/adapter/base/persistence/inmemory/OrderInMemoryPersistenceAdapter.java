package de.vnr.pxf.service.contract.adapter.base.persistence.inmemory;

import de.vnr.pxf.service.contract.application.port.resource.OrderPort;
import de.vnr.pxf.service.contract.domain.model.Order;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OrderInMemoryPersistenceAdapter implements OrderPort {

  private final Map<UUID, Order> idStorage = new HashMap<>();

  @Override
  public Optional<Order> loadById(UUID id) {
    return Optional.ofNullable(idStorage.get(id));
  }

  @Override
  public void insert(Order order) {
    idStorage.put(order.getId(), order);
  }
}
