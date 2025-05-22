package de.vnr.pxf.service.product.application.port.api.view;

import de.vnr.pxf.service.product.application.port.resource.OfferPort;
import de.vnr.pxf.service.product.domain.model.Offer;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferView {

  private final OfferPort offerPort;

  public Offer getById(UUID offerId) throws NoSuchElementException {
    return offerPort.loadById(offerId)
        .orElseThrow(() -> new NoSuchElementException("Offer with id " + offerId + " not found"));
  }
}
