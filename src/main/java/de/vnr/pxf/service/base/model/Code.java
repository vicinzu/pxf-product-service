package de.vnr.pxf.service.base.model;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Optional;
import lombok.Value;

/**
 * Represents a code.
 *
 * <p>Ensures that the code id is always in uppercase.</p>
 */
@Value
public class Code implements Serializable {

  public static final Code NONE = new Code("");

  public static Code of(String code) {
    return new Code(code);
  }

  @NotBlank
  String value;

  private Code(String value) {
    this.value = Optional.ofNullable(value)
        .filter(v -> !v.isBlank())
        .map(String::toUpperCase)
        .orElse("");
  }

  @Override
  public String toString() {
    return value;
  }
}
