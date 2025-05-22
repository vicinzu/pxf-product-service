package de.vnr.pxf.service.product.domain.model;

import de.vnr.pxf.service.base.Code;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Item {

  private final UUID id;

  private final UUID productId;

  private final Code code;

  @Setter
  private String title;

  Item(UUID productId, Code code, String title) {
    this(UUID.randomUUID(), productId, code);
    this.title = title;
  }
}
