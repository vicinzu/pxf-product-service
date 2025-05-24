package de.vnr.pxf.service.product.application.port.resource;

import de.vnr.pxf.service.product.domain.model.Product;
import de.vnr.pxf.service.product.domain.store.ProductStore;
import java.util.Optional;
import java.util.UUID;

public interface ProductPort extends ProductStore {

  Optional<Product> loadById(UUID id);

  void insert(Product product);

  void modify(Product product);
}
