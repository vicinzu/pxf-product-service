package de.vnr.pxf.service.product.domain.model;


import de.vnr.pxf.service.base.exception.CodeInUseException;
import de.vnr.pxf.service.base.exception.ReferenceNotExistsException;
import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.base.model.MoneyAmount;
import de.vnr.pxf.service.product.domain.store.OfferStore;
import de.vnr.pxf.service.product.domain.store.ProductStore;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Offer {

  private final UUID id;

  private final Code code;

  private final UUID productId;

  private final MoneyAmount price;

  public Offer(OfferStore offerStore, ProductStore productStore, Code code, UUID productId, MoneyAmount price)
      throws CodeInUseException {
    if (offerStore.exists(code)) {
      throw new CodeInUseException(null, code, "Offer with this code already exists.");
    } else if (!productStore.exists(productId)) {
      throw new ReferenceNotExistsException(productId, "Product does not exist.");
    }

    this.id = UUID.randomUUID();
    this.code = code;
    this.productId = productId;
    this.price = price;
  }
}
