package de.vnr.pxf.service.product.application.port.resource;

import de.vnr.pxf.service.product.domain.model.Item;
import de.vnr.pxf.service.product.domain.store.ItemStore;
import java.util.Optional;
import java.util.UUID;

public interface ItemPort extends ItemStore {

  Optional<Item> loadById(UUID itemId);

  void insert(Item product);

  void modify(Item product);
}
