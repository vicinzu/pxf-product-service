package de.vnr.pxf.service.product.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.base.model.MoneyAmount;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageOfferUseCase;
import de.vnr.pxf.service.product.application.port.resource.OfferPort;
import de.vnr.pxf.service.product.application.port.resource.ProductPort;
import de.vnr.pxf.service.product.application.service.config.ValidationTestConfiguration;
import de.vnr.pxf.service.product.domain.model.generator.OfferGenerator;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, OfferService.class})
@MockitoBean(types = {ProductPort.class, OfferPort.class})
class OfferServiceValidationTest {

  @Autowired
  private ManageOfferUseCase manageOfferUseCase;

  @Test
  void createOffer_whenMissingProductId_throwsException() {
    // arrange
    final var command = new ManageOfferUseCase.CreateOfferCommand(
        null,
        OfferGenerator.DEFAULT_CODE,
        OfferGenerator.DEFAULT_PRICE
    );

    // act + assert
    assertThatThrownBy(() -> manageOfferUseCase.createOffer(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createOffer_whenMissingCode_throwsException() {
    // arrange
    final var command = new ManageOfferUseCase.CreateOfferCommand(
        OfferGenerator.DEFAULT_PRODUCT_ID,
        null,
        OfferGenerator.DEFAULT_PRICE
    );

    // act + assert
    assertThatThrownBy(() -> manageOfferUseCase.createOffer(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createOffer_whenEmptyCode_throwsException() {
    // arrange
    final var command = new ManageOfferUseCase.CreateOfferCommand(
        OfferGenerator.DEFAULT_PRODUCT_ID,
        Code.NONE,
        OfferGenerator.DEFAULT_PRICE
    );

    // act + assert
    assertThatThrownBy(() -> manageOfferUseCase.createOffer(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createOffer_whenMissingPrice_throwsException() {
    // arrange
    final var command = new ManageOfferUseCase.CreateOfferCommand(
        OfferGenerator.DEFAULT_PRODUCT_ID,
        OfferGenerator.DEFAULT_CODE,
        null
    );

    // act + assert
    assertThatThrownBy(() -> manageOfferUseCase.createOffer(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createOffer_whenInvalidPrice_throwsException() {
    // arrange
    final var command = new ManageOfferUseCase.CreateOfferCommand(
        OfferGenerator.DEFAULT_PRODUCT_ID,
        OfferGenerator.DEFAULT_CODE,
        new MoneyAmount(BigDecimal.valueOf(-1.0), OfferGenerator.DEFAULT_PRICE.getCurrency())
    );

    // act + assert
    assertThatThrownBy(() -> manageOfferUseCase.createOffer(command))
        .isInstanceOf(ConstraintViolationException.class);
  }
}
