package de.vnr.pxf.service.product.domain.model.generator;

import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.product.domain.model.Item;
import java.util.UUID;

public class ItemGenerator {

  public static final UUID DEFAULT_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
  public static final Code DEFAULT_CODE = Code.of("ITEM");
  public static final String DEFAULT_TITLE = "itemTitle";

  public static Item generateDefault() {
    return generateWithId(DEFAULT_ID);
  }

  public static Item generateRandom() {
    return generateWithId(UUID.randomUUID());
  }

  private static Item generateWithId(UUID itemId) {
    final var item = new Item(
        itemId,
        DEFAULT_CODE
    );
    item.setTitle(DEFAULT_TITLE);

    return item;
  }
}
