package de.vnr.pxf.service.product.domain.store;

import de.vnr.pxf.service.base.model.Code;

public interface ItemStore {

  boolean exists(Code code);
}
