package de.vnr.pxf.service.product.domain.store;

import de.vnr.pxf.service.base.model.Code;
import java.util.UUID;

public interface ProductStore {

  boolean exists(UUID id);

  boolean exists(Code code);
}
