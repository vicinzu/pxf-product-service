package de.vnr.pxf.service.contract.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.base.test.ValidationTestConfiguration;
import de.vnr.pxf.service.contract.application.port.api.usecase.ReceiveOrderUseCase;
import de.vnr.pxf.service.contract.application.port.api.usecase.ReceiveOrderUseCase.ReceiveOrderCommand;
import de.vnr.pxf.service.contract.application.port.resource.OfferPort;
import de.vnr.pxf.service.contract.application.port.resource.OrderPort;
import de.vnr.pxf.service.contract.domain.model.Order;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, OrderService.class})
class OrderServiceTest {

  @Autowired
  private ReceiveOrderUseCase useCase;

  @MockitoBean
  private OfferPort offerPort;

  @MockitoBean
  private OrderPort orderPort;

  @Test
  void receiveOrder_whenOfferExists_createsOrder() {
    final var customerId = UUID.randomUUID();
    final var offerId = UUID.randomUUID();
    when(offerPort.exists(offerId)).thenReturn(true);

    // arrange
    final var command = new ReceiveOrderCommand(
        customerId,
        offerId
    );

    // act + assert
    assertThat(useCase.receiveOrder(command))
        .isNotNull();

    final var orderCaptor = ArgumentCaptor.forClass(Order.class);
    verify(orderPort).insert(orderCaptor.capture());
    assertThat(orderCaptor.getValue())
        .isNotNull()
        .satisfies(o -> {
          assertThat(o.getId()).isNotNull();
          assertThat(o.getCustomerId()).isEqualTo(command.customerId());
          assertThat(o.getOfferId()).isEqualTo(command.offerId());
        });
  }
}
