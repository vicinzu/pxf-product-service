package de.vnr.pxf.service.product.domain.store;

import de.vnr.pxf.service.base.Code;
import java.util.UUID;

public interface ItemStore {

  boolean exists(UUID productId, Code code);
}
