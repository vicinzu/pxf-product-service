package de.vnr.pxf.service.product.application.port.api.usecase;

import de.vnr.pxf.service.base.exception.CodeInUseException;
import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.base.model.MoneyAmount;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ManageOfferUseCase {

  UUID createOffer(@Valid CreateOfferCommand command) throws CodeInUseException;

  record CreateOfferCommand(
      @NotNull
      UUID productId,
      @Valid
      @NotNull
      Code code,
      @NotNull
      @Valid
      MoneyAmount price
  ) {

  }
}
