package de.vnr.pxf.service.contract.domain.store;

import java.util.UUID;

public interface OfferStore {

  boolean exists(UUID offerId);
}
