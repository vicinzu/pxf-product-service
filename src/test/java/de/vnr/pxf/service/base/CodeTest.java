package de.vnr.pxf.service.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CodeTest {

  private static final String VALUE = "default";
  private static final Code INSTANCE = Code.of(VALUE);

  @Test
  void of_returnsCode() {
    // act + assert
    assertThat(INSTANCE)
        .isNotNull()
        .satisfies(code ->
            assertThat(code.getValue()).isEqualTo(VALUE.toUpperCase())
        );
  }

  @Test
  void toString_returnsValue() {
    // act + assert
    assertThat(INSTANCE.toString()).isEqualTo(VALUE.toUpperCase());
  }

  @Test
  void getValue_returnsValue() {
    // act + assert
    assertThat(INSTANCE.getValue()).isEqualTo(VALUE.toUpperCase());
  }
}
