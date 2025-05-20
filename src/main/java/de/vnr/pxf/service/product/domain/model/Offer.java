package de.vnr.pxf.service.product.domain.model;

import de.vnr.pxf.service.base.Code;
import de.vnr.pxf.service.base.MoneyAmount;
import de.vnr.pxf.service.product.domain.exception.CodeInUseException;
import de.vnr.pxf.service.product.domain.store.OfferStore;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Offer {

  @Getter
  private final UUID id;

  @Getter
  private final Code code;

  @Getter
  private final Product product;

  @Getter
  private final MoneyAmount price;

  public Offer(OfferStore offerStore, Code code, Product product, MoneyAmount price) throws CodeInUseException {
    if (offerStore.exists(code)) {
      throw new CodeInUseException(null, code, "Offer with this code already exists.");
    }

    this.id = UUID.randomUUID();
    this.code = code;
    this.product = product;
    this.price = price;
  }
}
