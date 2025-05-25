package de.vnr.pxf.service.product.application.port.api.usecase;

import de.vnr.pxf.service.base.exception.CodeInUseException;
import de.vnr.pxf.service.base.model.Code;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ManageItemUseCase {

  UUID createItem(@Valid CreateItemCommand command) throws CodeInUseException;

  void updateItem(@Valid UpdateItemCommand command) throws NoSuchElementException;

  record CreateItemCommand(
      @NotNull
      UUID productId,
      @Valid
      @NotNull
      Code code,
      @NotBlank
      @Size(min = 3)
      String title
  ) {

  }

  record UpdateItemCommand(
      @NotNull
      UUID productId,
      @NotNull
      UUID itemId,
      @NotBlank
      @Size(min = 3)
      String title
  ) {

  }
}
