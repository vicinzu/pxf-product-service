package de.vnr.pxf.service.product.application.port.api.usecase;

import de.vnr.pxf.service.base.Code;
import de.vnr.pxf.service.product.domain.exception.CodeInUseException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ManageProductUseCase {

  UUID createProduct(@Valid CreateProductCommand command) throws CodeInUseException;

  void updateProduct(@Valid UpdateProductCommand command) throws NoSuchElementException;

  record CreateProductCommand(
      @Valid
      @NotNull
      Code code,
      @NotBlank
      @Size(min = 3)
      String title
  ) {

  }

  record UpdateProductCommand(
      @NotNull
      UUID productId,
      @NotBlank
      @Size(min = 3)
      String title
  ) {

  }
}
