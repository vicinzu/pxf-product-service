package de.vnr.pxf.service.product.application.port.api.view;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.product.application.port.resource.ProductPort;
import de.vnr.pxf.service.product.domain.model.generator.ProductGenerator;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductViewTest {

  @InjectMocks
  private ProductView productView;

  @Mock
  private ProductPort productPort;

  @Test
  void getById_whenProductExists_returnsProduct() {
    // arrange
    final var product = ProductGenerator.generateDefault();
    when(productPort.loadById(product.getId())).thenReturn(Optional.of(product));

    // act + assert
    assertThat(productView.getById(product.getId()))
        .isNotNull()
        .isEqualTo(product);
  }

  @Test
  void getById_whenProductDoesNotExist_throwsNoSuchElementException() {
    // arrange
    final var productId = ProductGenerator.DEFAULT_ID;
    when(productPort.loadById(productId)).thenReturn(Optional.empty());

    // act + assert
    assertThatThrownBy(() -> productView.getById(productId))
        .isInstanceOf(NoSuchElementException.class);
  }
}
