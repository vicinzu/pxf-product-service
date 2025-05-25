package de.vnr.pxf.service.base.exception;


import de.vnr.pxf.service.base.model.Code;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CodeInUseException extends RuntimeException {

  private final UUID parentId;
  private final Code code;

  public CodeInUseException(UUID parentId, Code code, String message) {
    super(message);
    this.parentId = parentId;
    this.code = code;
  }
}
