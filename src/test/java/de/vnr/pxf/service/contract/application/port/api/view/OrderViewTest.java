package de.vnr.pxf.service.contract.application.port.api.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.contract.application.port.resource.OrderPort;
import de.vnr.pxf.service.contract.domain.model.generator.OrderGenerator;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderViewTest {

  @InjectMocks
  private OrderView orderView;

  @Mock
  private OrderPort orderPort;

  @Test
  void getById_whenOrderExists_returnsOrder() {
    // arrange
    final var order = OrderGenerator.generateDefault();
    when(orderPort.loadById(order.getId())).thenReturn(Optional.of(order));

    // act + assert
    assertThat(orderView.getById(order.getId()))
        .isNotNull()
        .isEqualTo(order);
  }

  @Test
  void getById_whenOrderDoesNotExist_throwsNoSuchElementException() {
    // arrange
    final var orderId = OrderGenerator.DEFAULT_ID;
    when(orderPort.loadById(orderId)).thenReturn(Optional.empty());

    // act + assert
    assertThatThrownBy(() -> orderView.getById(orderId))
        .isInstanceOf(NoSuchElementException.class);
  }
}
