package de.vnr.pxf.service.base.exception;

import lombok.Getter;

@Getter
public class OperationNotPossibleException extends RuntimeException {

  private final String operationId;

  public OperationNotPossibleException(String operationId, String message) {
    super(message);
    this.operationId = operationId;
  }
}
