package de.vnr.pxf.service.product.application.port.resource;

import de.vnr.pxf.service.product.domain.model.Offer;
import de.vnr.pxf.service.product.domain.store.OfferStore;
import java.util.Optional;
import java.util.UUID;

public interface OfferPort extends OfferStore {

  Optional<Offer> loadById(UUID offerId);

  void insert(Offer product);
}
