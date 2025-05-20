package de.vnr.pxf.service.product.application.port.resource;

import de.vnr.pxf.service.product.domain.model.Offer;
import de.vnr.pxf.service.product.domain.store.OfferStore;

public interface OfferPort extends OfferStore {

  void create(Offer product);
}
