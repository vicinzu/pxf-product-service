package de.vnr.pxf.service.product.application.service;

import de.vnr.pxf.service.product.application.port.api.usecase.ManageOfferUseCase;
import de.vnr.pxf.service.product.application.port.resource.OfferPort;
import de.vnr.pxf.service.product.application.port.resource.ProductPort;
import de.vnr.pxf.service.product.domain.model.Offer;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService implements ManageOfferUseCase {

  private final ProductPort productPort;
  private final OfferPort offerPort;

  @Override
  public UUID createOffer(CreateOfferCommand command) {
    // act
    final var offer = new Offer(offerPort, productPort, command.code(), command.productId(), command.price());
    offerPort.insert(offer);

    return offer.getId();
  }
}
