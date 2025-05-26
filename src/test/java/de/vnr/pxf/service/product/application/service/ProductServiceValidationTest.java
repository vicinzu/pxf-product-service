package de.vnr.pxf.service.product.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.base.test.ValidationTestConfiguration;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageProductUseCase;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageProductUseCase.UpdateProductCommand;
import de.vnr.pxf.service.product.application.port.api.view.ProductView;
import de.vnr.pxf.service.product.application.port.resource.ProductPort;
import de.vnr.pxf.service.product.domain.model.generator.ProductGenerator;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, ProductService.class})
@MockitoBean(types = {ProductView.class, ProductPort.class})
class ProductServiceValidationTest {

  @Autowired
  private ManageProductUseCase useCase;

  @Test
  void createProduct_whenMissingCode_throwsConstraintViolationException() {
    // arrange
    final var command = new ManageProductUseCase.CreateProductCommand(
        null,
        ProductGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThatThrownBy(() -> useCase.createProduct(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createProduct_whenEmptyCode_throwsConstraintViolationException() {
    // arrange
    final var command = new ManageProductUseCase.CreateProductCommand(
        Code.NONE,
        ProductGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThatThrownBy(() -> useCase.createProduct(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createProduct_whenMissingTitle_throwsConstraintViolationException() {
    // arrange
    final var command = new ManageProductUseCase.CreateProductCommand(
        ProductGenerator.DEFAULT_CODE,
        null
    );

    // act + assert
    assertThatThrownBy(() -> useCase.createProduct(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createProduct_whenBlankTitle_throwsConstraintViolationException() {
    // arrange
    final var command = new ManageProductUseCase.CreateProductCommand(
        ProductGenerator.DEFAULT_CODE,
        "   "
    );

    // act + assert
    assertThatThrownBy(() -> useCase.createProduct(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void updateProduct_whenMissingProductId_throwsConstraintViolationException() {
    // arrange
    final var command = new UpdateProductCommand(
        null,
        ProductGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThatThrownBy(() -> useCase.updateProduct(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void updateProduct_whenMissingTitle_throwsConstraintViolationException() {
    // arrange
    final var command = new UpdateProductCommand(
        ProductGenerator.DEFAULT_ID,
        null
    );

    // act + assert
    assertThatThrownBy(() -> useCase.updateProduct(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void updateProduct_whenBlankTitle_throwsConstraintViolationException() {
    // arrange
    final var command = new UpdateProductCommand(
        ProductGenerator.DEFAULT_ID,
        "   "
    );

    // act + assert
    assertThatThrownBy(() -> useCase.updateProduct(command))
        .isInstanceOf(ConstraintViolationException.class);
  }
}
