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

  private final Map<UUID, Map<UUID, Item>> idStorage = new HashMap<>();
  private final Map<UUID, Map<Code, Item>> codeStorage = new HashMap<>();

  @Override
  public Optional<Item> loadById(UUID productId, UUID itemId) {
    final var productItems = idStorage.computeIfAbsent(productId, k -> new HashMap<>());

    return Optional.ofNullable(productItems.get(itemId));
  }

  @Override
  public void insert(UUID productId, Item item) {
    final var productItemsById = idStorage.computeIfAbsent(productId, k -> new HashMap<>());
    productItemsById.put(item.getId(), item);
    final var productItemsByCode = codeStorage.computeIfAbsent(productId, k -> new HashMap<>());
    productItemsByCode.put(item.getCode(), item);
  }

  @Override
  public void modify(UUID productId, Item item) {
    this.insert(productId, item);
  }

  @Override
  public boolean exists(UUID productId, Code code) {
    return Optional.ofNullable(codeStorage.get(productId))
        .map(items -> items.containsKey(code))
        .orElse(false);
  }
}
