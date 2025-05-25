package de.vnr.pxf.service.contract.domain.model.generator;

import de.vnr.pxf.service.contract.domain.model.Order;
import java.util.UUID;

public class OrderGenerator {

  public static final UUID DEFAULT_ID = UUID.fromString("00000000-0000-0000-0000-000000000005");
  public static final UUID DEFAULT_CUSTOMER_ID = UUID.fromString("00000000-0000-0000-0000-000000000004");
  public static final UUID DEFAULT_OFFER_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");

  public static Order generateDefault() {
    return generateWithId(DEFAULT_ID);
  }

  public static Order generateRandom() {
    return generateWithId(UUID.randomUUID());
  }

  private static Order generateWithId(UUID orderId) {
    return new Order(
        orderId,
        DEFAULT_CUSTOMER_ID,
        DEFAULT_OFFER_ID
    );
  }
}
