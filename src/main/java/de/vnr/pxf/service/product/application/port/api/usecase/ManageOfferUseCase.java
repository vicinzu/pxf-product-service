package de.vnr.pxf.service.product.application.port.api.usecase;

import de.vnr.pxf.service.base.Code;
import de.vnr.pxf.service.base.MoneyAmount;
import de.vnr.pxf.service.product.domain.exception.CodeInUseException;
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
