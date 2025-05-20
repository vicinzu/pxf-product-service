package de.vnr.pxf.service.product.domain.store;

import de.vnr.pxf.service.base.Code;

public interface ProductStore {

  boolean exists(Code code);
}
