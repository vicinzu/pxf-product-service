package de.vnr.pxf.service.product.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import de.vnr.pxf.service.product.domain.model.generator.ItemGenerator;
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
    final var code = ItemGenerator.DEFAULT_CODE;

    // act + assert
    assertThat(new Item(id, code))
        .isNotNull()
        .satisfies(item -> {
          assertThat(item.getId()).isEqualTo(id);
          assertThat(item.getCode()).isEqualTo(code);
          assertThat(item.getTitle()).isNull();
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
