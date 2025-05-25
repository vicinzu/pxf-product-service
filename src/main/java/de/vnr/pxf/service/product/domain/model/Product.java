package de.vnr.pxf.service.product.domain.model;

import de.vnr.pxf.service.base.exception.CodeInUseException;
import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.product.domain.store.ItemStore;
import de.vnr.pxf.service.product.domain.store.ProductStore;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class Product {

  @Getter
  private final UUID id;

  @Getter
  private final Code code;

  private final Set<Item> items = new HashSet<>();

  @Getter
  @Setter
  private String title;

  public Product(ProductStore store, Code code, String title) throws CodeInUseException {
    if (store.exists(code)) {
      throw new CodeInUseException(null, code, "Product with this code already exists.");
    }

    this.id = UUID.randomUUID();
    this.code = code;
    this.title = title;
  }

  // Item Management
  public Item createItem(ItemStore store, Code code, String title) throws CodeInUseException {
    if (store.exists(this.id, code)) {
      throw new CodeInUseException(this.id, code, "Item with this code already exists for this product.");
    }

    return new Item(code, title);
  }

  public Set<Item> getItems() {
    return Collections.unmodifiableSet(this.items);
  }

  public void setItems(Collection<Item> items) {
    this.items.clear();
    this.items.addAll(items);
  }
}
