package de.vnr.pxf.service.contract.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.vnr.pxf.service.base.test.ValidationTestConfiguration;
import de.vnr.pxf.service.contract.application.port.api.usecase.ReceiveOrderUseCase;
import de.vnr.pxf.service.contract.application.port.api.usecase.ReceiveOrderUseCase.ReceiveOrderCommand;
import de.vnr.pxf.service.contract.application.port.resource.OfferPort;
import de.vnr.pxf.service.contract.application.port.resource.OrderPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, OrderService.class})
@MockitoBean(types = {OfferPort.class, OrderPort.class})
class OrderServiceValidationTest {

  @Autowired
  private ReceiveOrderUseCase useCase;

  @Test
  void receiveOrder_whenMissingCustomerId_throwsConstraintViolationException() {
    // arrange
    final var command = new ReceiveOrderCommand(
        null,
        java.util.UUID.randomUUID()
    );

    // act + assert
    assertThatThrownBy(() -> useCase.receiveOrder(command))
        .isInstanceOf(jakarta.validation.ConstraintViolationException.class);
  }

  @Test
  void receiveOrder_whenMissingOfferId_throwsConstraintViolationException() {
    // arrange
    final var command = new ReceiveOrderCommand(
        java.util.UUID.randomUUID(),
        null
    );

    // act + assert
    assertThatThrownBy(() -> useCase.receiveOrder(command))
        .isInstanceOf(jakarta.validation.ConstraintViolationException.class);
  }
}
