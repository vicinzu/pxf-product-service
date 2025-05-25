package de.vnr.pxf.service.contract.application.port.api.usecase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ManageContractUseCase {

  UUID createContract(@Valid CreateContractCommand command);

  record CreateContractCommand(
      UUID orderId
  ) {

  }

  void activateContract(@NotNull UUID contractId);

  void completeContract(@NotNull UUID contractId);
}
