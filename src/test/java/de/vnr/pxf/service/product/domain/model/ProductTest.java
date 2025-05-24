package de.vnr.pxf.service.product.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.product.domain.exception.CodeInUseException;
import de.vnr.pxf.service.product.domain.model.generator.ItemGenerator;
import de.vnr.pxf.service.product.domain.model.generator.ProductGenerator;
import de.vnr.pxf.service.product.domain.store.ItemStore;
import de.vnr.pxf.service.product.domain.store.ProductStore;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTest {

  private Product product;

  @BeforeEach
  void setUp() {
    // reset the product before each test
    product = ProductGenerator.generateDefault();
  }

  @Test
  void construct_whenWithId_createsProduct() {
    // arrange
    final var id = ProductGenerator.DEFAULT_ID;
    final var code = ProductGenerator.DEFAULT_CODE;

    // act + assert
    assertThat(new Product(id, code))
        .isNotNull()
        .satisfies(product -> {
          assertThat(product.getId()).isEqualTo(id);
          assertThat(product.getCode()).isEqualTo(code);
          assertThat(product.getTitle()).isNull();
        });
  }

  @Test
  void construct_whenCodeInUse_throwsException() {
    // arrange
    final var code = ProductGenerator.DEFAULT_CODE;
    final var title = ProductGenerator.DEFAULT_TITLE;

    final var productStorage = mock(ProductStore.class);
    when(productStorage.exists(code)).thenReturn(true);

    // act + assert
    assertThatThrownBy(() -> new Product(productStorage, code, title))
        .isInstanceOfSatisfying(CodeInUseException.class, e -> {
          assertThat(e.getParentId()).isNull();
          assertThat(e.getCode()).isEqualTo(code);
        });
  }

  @Test
  void construct_whenCodeNotInUse_createsProduct() {
    // arrange
    final var code = ProductGenerator.DEFAULT_CODE;
    final var title = ProductGenerator.DEFAULT_TITLE;

    final var productStorage = mock(ProductStore.class);
    when(productStorage.exists(code)).thenReturn(false);

    // act + assert
    assertThat(new Product(productStorage, code, title))
        .isNotNull()
        .satisfies(product -> {
          assertThat(product.getId()).isNotNull();
          assertThat(product.getCode()).isEqualTo(code);
          assertThat(product.getTitle()).isEqualTo(title);
        });
  }

  @Test
  void getId() {
    // act + assert
    assertThat(product.getId()).isEqualTo(ProductGenerator.DEFAULT_ID);
  }

  @Test
  void getCode() {
    // act + assert
    assertThat(product.getCode()).isEqualTo(ProductGenerator.DEFAULT_CODE);
  }

  @Test
  void getTitle() {
    // act + assert
    assertThat(product.getTitle()).isEqualTo(ProductGenerator.DEFAULT_TITLE);
  }

  @Test
  void setTitle() {
    // pre-condition
    final var newTitle = "new";
    assertThat(product.getTitle()).isNotEqualTo(newTitle);

    // act
    product.setTitle(newTitle);

    // assert
    assertThat(product.getTitle()).isEqualTo(newTitle);
  }

  @Test
  void createItem_whenCodeAlreadyExists_throwsException() {
    // arrange
    final var itemStorage = mock(ItemStore.class);
    final var itemCode = ItemGenerator.DEFAULT_CODE;
    final var itemTitle = ItemGenerator.DEFAULT_TITLE;
    when(itemStorage.exists(product.getId(), itemCode)).thenReturn(true);

    // act + assert
    assertThatThrownBy(() -> product.createItem(itemStorage, itemCode, itemTitle))
        .isInstanceOfSatisfying(CodeInUseException.class, e -> {
          assertThat(e.getParentId()).isEqualTo(product.getId());
          assertThat(e.getCode()).isEqualTo(itemCode);
        });
  }

  @Test
  void createItem_whenCodeDoesNotYetExist_returnsItem() {
    // arrange
    final var itemStorage = mock(ItemStore.class);
    final var itemCode = ItemGenerator.DEFAULT_CODE;
    final var itemTitle = ItemGenerator.DEFAULT_TITLE;
    when(itemStorage.exists(product.getId(), itemCode)).thenReturn(false);

    // act + assert
    assertThat(product.createItem(itemStorage, itemCode, itemTitle))
        .isNotNull()
        .satisfies(item -> {
          assertThat(item.getCode()).isEqualTo(itemCode);
          assertThat(item.getTitle()).isEqualTo(itemTitle);
        });
  }

  @Test
  void getItems() {
    // pre-condition
    final var items = List.of(
        ItemGenerator.generateRandom(),
        ItemGenerator.generateRandom()
    );
    product.setItems(items);

    // arrange
    assertThat(product.getItems())
        .hasSize(2)
        .containsExactlyInAnyOrderElementsOf(items);
  }

  @Test
  void setItems() {
    // pre-condition
    final var items = List.of(
        ItemGenerator.generateRandom(),
        ItemGenerator.generateRandom()
    );
    assertThat(product.getItems()).isEmpty();

    // act
    product.setItems(items);

    // assert
    assertThat(product.getItems())
        .hasSize(2)
        .containsExactlyInAnyOrderElementsOf(items);
  }
}
