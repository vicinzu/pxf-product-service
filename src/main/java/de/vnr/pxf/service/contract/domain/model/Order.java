package de.vnr.pxf.service.contract.domain.model;

import de.vnr.pxf.service.contract.domain.store.OfferStore;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Order {

  private final UUID id;

  private final UUID customerId;

  private final UUID offerId;

  public Order(OfferStore offerStore, UUID customerId, UUID offerId) {
    this(UUID.randomUUID(), customerId, offerId);
  }
}
