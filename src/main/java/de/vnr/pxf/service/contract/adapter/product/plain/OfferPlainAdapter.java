package de.vnr.pxf.service.contract.adapter.product.plain;

import de.vnr.pxf.service.contract.application.port.resource.OfferPort;
import de.vnr.pxf.service.product.application.port.api.view.OfferView;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OfferPlainAdapter implements OfferPort {

  private final OfferView offerView;

  @Override
  public boolean exists(UUID offerId) {
    return offerView.existsById(offerId);
  }
}
