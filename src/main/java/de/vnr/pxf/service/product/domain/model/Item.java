package de.vnr.pxf.service.product.domain.model;

import de.vnr.pxf.service.base.exception.CodeInUseException;
import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.product.domain.store.ItemStore;
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
public class Item {

  private final UUID id;

  private final UUID productId;

  private final Code code;

  @Setter
  private String title;

  Item(ItemStore store, UUID productId, Code code, String title) {
    if (store.exists(code)) {
      throw new CodeInUseException(productId, code, "Item with this code already exists.");
    }
    this.id = UUID.randomUUID();
    this.productId = productId;
    this.code = code;
    this.title = title;
  }

  Item(ItemStore store, Code code, String title) {
    this(store, null, code, title);
  }
}
