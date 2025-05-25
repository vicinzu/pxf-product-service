package de.vnr.pxf.service.product.adapter.base.persistence.inmemory;

import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.product.application.port.resource.OfferPort;
import de.vnr.pxf.service.product.domain.model.Offer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OfferInMemoryPersistenceAdapter implements OfferPort {

  private final Map<UUID, Offer> idStorage = new HashMap<>();
  private final Map<Code, Offer> codeStorage = new HashMap<>();

  @Override
  public Optional<Offer> loadById(UUID offerId) {
    return Optional.ofNullable(idStorage.get(offerId));
  }

  @Override
  public void insert(Offer offer) {
    idStorage.put(offer.getId(), offer);
    codeStorage.put(offer.getCode(), offer);
  }

  @Override
  public boolean exists(UUID offerId) {
    return idStorage.containsKey(offerId);
  }

  @Override
  public boolean exists(Code code) {
    return codeStorage.containsKey(code);
  }
}
