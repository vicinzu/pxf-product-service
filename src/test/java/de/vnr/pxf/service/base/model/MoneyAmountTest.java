package de.vnr.pxf.service.base.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class MoneyAmountTest {

  private static final BigDecimal AMOUNT = BigDecimal.TEN;
  private static final Currency CURRENCY = Currency.getInstance(Locale.getDefault());
  private static final MoneyAmount INSTANCE = new MoneyAmount(AMOUNT, CURRENCY);

  @Test
  void getValue() {
    // act + assert
    assertThat(INSTANCE.getValue())
        .isNotNull()
        .isEqualTo(AMOUNT);
  }

  @Test
  void getCurrency() {
    // act + assert
    assertThat(INSTANCE.getCurrency())
        .isNotNull()
        .isEqualTo(CURRENCY);
  }
}
