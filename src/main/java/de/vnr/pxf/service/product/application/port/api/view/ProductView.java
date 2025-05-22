package de.vnr.pxf.service.product.application.port.api.view;

import de.vnr.pxf.service.product.application.port.resource.ProductPort;
import de.vnr.pxf.service.product.domain.model.Product;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductView {

  private final ProductPort productPort;

  public Product getById(UUID productId) throws NoSuchElementException {
    return productPort.loadById(productId)
        .orElseThrow(() -> new NoSuchElementException("Product with id " + productId + " not found"));
  }
}
