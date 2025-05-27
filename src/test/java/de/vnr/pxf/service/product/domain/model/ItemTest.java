package de.vnr.pxf.service.product.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.base.exception.CodeInUseException;
import de.vnr.pxf.service.product.domain.model.generator.ItemGenerator;
import de.vnr.pxf.service.product.domain.store.ItemStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemTest {

  private Item item;

  @BeforeEach
  void setUp() {
    // reset the offer before each test
    item = ItemGenerator.generateDefault();
  }

  @Test
  void construct_whenWithId_createsItem() {
    // arrange
    final var id = ItemGenerator.DEFAULT_ID;
    final var productId = ItemGenerator.DEFAULT_PRODUCT_ID;
    final var code = ItemGenerator.DEFAULT_CODE;

    // act + assert
    assertThat(new Item(id, productId, code))
        .isNotNull()
        .satisfies(item -> {
          assertThat(item.getId()).isEqualTo(id);
          assertThat(item.getCode()).isEqualTo(code);
          assertThat(item.getTitle()).isNull();
        });
  }

  @Test
  void construct_whenCodeInUse_throwsCodeInUseException() {
    // arrange
    final var code = ItemGenerator.DEFAULT_CODE;
    final var title = ItemGenerator.DEFAULT_TITLE;
    final var itemStorage = mock(ItemStore.class);
    when(itemStorage.exists(code)).thenReturn(true);

    // act + assert
    assertThatThrownBy(() -> new Item(itemStorage, code, title))
        .isInstanceOfSatisfying(CodeInUseException.class, e -> {
          assertThat(e.getParentId()).isNull();
          assertThat(e.getCode()).isEqualTo(code);
        });
  }

  @Test
  void construct_whenCodeNotInUse_createsItem() {
    // arrange
    final var code = ItemGenerator.DEFAULT_CODE;
    final var title = ItemGenerator.DEFAULT_TITLE;
    final var itemStorage = mock(ItemStore.class);
    when(itemStorage.exists(code)).thenReturn(false);

    // act + assert
    assertThat(new Item(itemStorage, code, title))
        .isNotNull()
        .satisfies(item -> {
          assertThat(item.getId()).isNotNull();
          assertThat(item.getCode()).isEqualTo(code);
          assertThat(item.getTitle()).isEqualTo(title);
        });
  }

  @Test
  void getId() {
    // act + assert
    assertThat(item.getId()).isEqualTo(ItemGenerator.DEFAULT_ID);
  }

  @Test
  void getCode() {
    // act + assert
    assertThat(item.getCode()).isEqualTo(ItemGenerator.DEFAULT_CODE);
  }

  @Test
  void getTitle() {
    // act + assert
    assertThat(item.getTitle()).isEqualTo(ItemGenerator.DEFAULT_TITLE);
  }

  @Test
  void setTitle() {
    // pre-condition
    final var newTitle = "new";
    assertThat(item.getTitle()).isNotEqualTo(newTitle);

    // act
    item.setTitle(newTitle);

    // assert
    assertThat(item.getTitle()).isEqualTo(newTitle);
  }
}
