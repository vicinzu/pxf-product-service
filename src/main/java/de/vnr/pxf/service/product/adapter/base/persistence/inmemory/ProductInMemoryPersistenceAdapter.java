package de.vnr.pxf.service.product.adapter.base.persistence.inmemory;

import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.product.application.port.resource.ProductPort;
import de.vnr.pxf.service.product.domain.model.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ProductInMemoryPersistenceAdapter implements ProductPort {

  private final Map<UUID, Product> idStorage = new HashMap<>();
  private final Map<Code, Product> codeStorage = new HashMap<>();

  @Override
  public Optional<Product> loadById(UUID id) {
    return Optional.ofNullable(idStorage.get(id));
  }

  @Override
  public void insert(Product product) {
    idStorage.put(product.getId(), product);
    codeStorage.put(product.getCode(), product);
  }

  @Override
  public void modify(Product product) {
    this.insert(product);
  }

  @Override
  public boolean exists(UUID id) {
    return idStorage.containsKey(id);
  }

  @Override
  public boolean exists(Code code) {
    return codeStorage.containsKey(code);
  }
}
