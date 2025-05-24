package de.vnr.pxf.service.product.domain.model.generator;

import de.vnr.pxf.service.base.Code;
import de.vnr.pxf.service.product.domain.model.Product;
import java.util.UUID;

public class ProductGenerator {

  public static final UUID DEFAULT_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
  public static final Code DEFAULT_CODE = Code.of("PRODUCT");
  public static final String DEFAULT_TITLE = "productTitle";

  public static Product generateDefault() {
    return generateWithId(DEFAULT_ID);
  }

  public static Product generateRandom() {
    return generateWithId(UUID.randomUUID());
  }

  private static Product generateWithId(UUID productId) {
    final var product = new Product(
        productId,
        DEFAULT_CODE
    );
    product.setTitle(DEFAULT_TITLE);

    return product;
  }
}
