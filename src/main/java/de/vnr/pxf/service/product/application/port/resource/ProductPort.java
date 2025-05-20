package de.vnr.pxf.service.product.application.port.resource;

import de.vnr.pxf.service.product.domain.model.Product;
import de.vnr.pxf.service.product.domain.store.ItemStore;
import de.vnr.pxf.service.product.domain.store.ProductStore;

public interface ProductPort extends ProductStore, ItemStore {

  void create(Product product);

  void update(Product product);
}
