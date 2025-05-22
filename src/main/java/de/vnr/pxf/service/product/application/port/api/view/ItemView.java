package de.vnr.pxf.service.product.application.port.api.view;

import de.vnr.pxf.service.product.application.port.resource.ItemPort;
import de.vnr.pxf.service.product.domain.model.Item;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemView {

  private final ItemPort itemPort;

  public Item getById(UUID productId, UUID itemId) throws NoSuchElementException {
    return itemPort.loadById(productId, itemId)
        .orElseThrow(() -> new NoSuchElementException("Item with id " + itemId + " not found"));
  }
}
