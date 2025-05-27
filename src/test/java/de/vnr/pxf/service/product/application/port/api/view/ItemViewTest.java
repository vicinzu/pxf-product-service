package de.vnr.pxf.service.product.application.port.api.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.product.application.port.resource.ItemPort;
import de.vnr.pxf.service.product.domain.model.generator.ItemGenerator;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemViewTest {

  @InjectMocks
  private ItemView itemView;

  @Mock
  private ItemPort itemPort;

  @Test
  void getById_whenItemExists_returnsItem() {
    // arrange
    final var item = ItemGenerator.generateDefault();
    when(itemPort.loadById(item.getId())).thenReturn(Optional.of(item));

    // act + assert
    assertThat(itemView.getById(item.getId()))
        .isNotNull()
        .isEqualTo(item);
  }

  @Test
  void getById_whenItemDoesNotExist_throwsNoSuchElementException() {
    // arrange
    final var itemId = ItemGenerator.DEFAULT_ID;
    when(itemPort.loadById(itemId)).thenReturn(Optional.empty());

    // act + assert
    assertThatThrownBy(() -> itemView.getById(itemId))
        .isInstanceOf(NoSuchElementException.class);
  }
}
