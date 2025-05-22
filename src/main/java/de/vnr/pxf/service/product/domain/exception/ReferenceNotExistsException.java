package de.vnr.pxf.service.product.domain.exception;

import java.util.UUID;
import lombok.Getter;

@Getter
public class ReferenceNotExistsException extends RuntimeException {

  private final UUID referenceId;

  public ReferenceNotExistsException(UUID referenceId, String message) {
    super(message);
    this.referenceId = referenceId;
  }
}
