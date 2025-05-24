package de.vnr.pxf.service.product.domain.model.generator;

import de.vnr.pxf.service.base.Code;
import de.vnr.pxf.service.base.MoneyAmount;
import de.vnr.pxf.service.product.domain.model.Offer;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

public class OfferGenerator {

  public static final UUID DEFAULT_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");
  public static final Code DEFAULT_CODE = Code.of("OFFER");
  public static final UUID DEFAULT_PRODUCT_ID = ProductGenerator.DEFAULT_ID;
  public static final MoneyAmount DEFAULT_PRICE = new MoneyAmount(BigDecimal.TWO,
      Currency.getInstance(Locale.getDefault()));

  public static Offer generateDefault() {
    return generateWithId(DEFAULT_ID);
  }

  public static Offer generateRandom() {
    return generateWithId(UUID.randomUUID());
  }

  private static Offer generateWithId(UUID productId) {
    return new Offer(
        productId,
        DEFAULT_CODE,
        DEFAULT_PRODUCT_ID,
        DEFAULT_PRICE
    );
  }
}
