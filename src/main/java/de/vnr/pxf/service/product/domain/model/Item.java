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

  private final Code code;

  @Setter
  private String title;

  Item(Code code, String title) {
    this(UUID.randomUUID(), code);
    this.title = title;
  }
}
