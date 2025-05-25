package de.vnr.pxf.service.base.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MoneyAmount {

  @Positive
  BigDecimal value;

  @NotNull
  Currency currency;
}
