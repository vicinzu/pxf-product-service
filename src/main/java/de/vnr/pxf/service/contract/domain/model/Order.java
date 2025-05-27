package de.vnr.pxf.service.contract.domain.model;

import de.vnr.pxf.service.base.exception.ReferenceNotExistsException;
import de.vnr.pxf.service.contract.domain.store.OfferStore;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Order {

  private final UUID id;

  private final UUID customerId;

  private final UUID offerId;

  public Order(OfferStore offerStore, UUID customerId, UUID offerId) {
    if (!offerStore.exists(offerId)) {
      throw new ReferenceNotExistsException(offerId, "Offer with ID " + offerId + " does not exist.");
    }

    this.id = UUID.randomUUID();
    this.customerId = customerId;
    this.offerId = offerId;
  }
}
