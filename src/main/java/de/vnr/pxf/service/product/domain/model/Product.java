package de.vnr.pxf.service.product.domain.model;

import de.vnr.pxf.service.base.exception.CodeInUseException;
import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.product.domain.store.ItemStore;
import de.vnr.pxf.service.product.domain.store.ProductStore;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {

  private final UUID id;

  private final Code code;

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
    return new Item(store, this.id, code, title);
  }
}
