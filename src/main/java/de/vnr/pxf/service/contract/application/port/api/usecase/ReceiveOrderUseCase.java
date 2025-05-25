package de.vnr.pxf.service.contract.application.port.api.usecase;

import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ReceiveOrderUseCase {

  UUID receiveOrder(@Valid ReceiveOrderCommand command);

  record ReceiveOrderCommand(
      UUID customerId,
      UUID offerId
  ) {

  }
}
