package de.vnr.pxf.service.contract.application.port.api.usecase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ReceiveOrderUseCase {

  UUID receiveOrder(@Valid ReceiveOrderCommand command);

  record ReceiveOrderCommand(
      @NotNull
      UUID customerId,
      @NotNull
      UUID offerId
  ) {

  }
}
