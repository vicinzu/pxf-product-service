package de.vnr.pxf.service.product.application.service;

import de.vnr.pxf.service.product.application.port.api.usecase.ManageProductUseCase;
import de.vnr.pxf.service.product.application.port.api.view.ProductView;
import de.vnr.pxf.service.product.application.port.resource.ProductPort;
import de.vnr.pxf.service.product.domain.model.Product;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements ManageProductUseCase {

  private final ProductView view;
  private final ProductPort port;

  @Override
  public UUID createProduct(CreateProductCommand command) {
    // act
    final var product = new Product(port, command.code(), command.title());
    port.insert(product);

    return product.getId();
  }

  @Override
  public void updateProduct(UpdateProductCommand command) {
    // arrange
    final var product = view.getById(command.productId());

    // act
    product.setTitle(command.title());
    port.modify(product);
  }
}
