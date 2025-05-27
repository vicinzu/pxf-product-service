package de.vnr.pxf.service.product.adapter.base.persistence.inmemory;

import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.product.application.port.resource.ItemPort;
import de.vnr.pxf.service.product.domain.model.Item;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ItemInMemoryPersistenceAdapter implements ItemPort {

  private final Map<UUID, Item> idStorage = new HashMap<>();
  private final Map<Code, Item> codeStorage = new HashMap<>();

  @Override
  public Optional<Item> loadById(UUID itemId) {
    return Optional.ofNullable(idStorage.get(itemId));
  }

  @Override
  public void insert(Item item) {
    idStorage.put(item.getId(), item);
    codeStorage.put(item.getCode(), item);
  }

  @Override
  public void modify(Item item) {
    this.insert(item);
  }

  @Override
  public boolean exists(Code code) {
    return codeStorage.containsKey(code);
  }
}
