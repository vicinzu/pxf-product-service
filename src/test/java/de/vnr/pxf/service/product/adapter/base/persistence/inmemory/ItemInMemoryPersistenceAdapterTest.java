package de.vnr.pxf.service.product.adapter.base.persistence.inmemory;

import static org.assertj.core.api.Assertions.assertThat;

import de.vnr.pxf.service.product.domain.model.generator.ItemGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemInMemoryPersistenceAdapterTest {

  private ItemInMemoryPersistenceAdapter adapter;

  @BeforeEach
  void setUp() {
    // reset the adapter before each test
    adapter = new ItemInMemoryPersistenceAdapter();
  }

  @Test
  void loadById() {
    // pre-condition
    final var item = ItemGenerator.generateDefault();
    assertThat(adapter.loadById(item.getId())).isEmpty();

    // arrange
    adapter.insert(item);

    // act + assert
    assertThat(adapter.loadById(item.getId()))
        .isPresent()
        .get()
        .isEqualTo(item);
  }

  @Test
  void insert() {
    // pre-condition
    final var item = ItemGenerator.generateDefault();
    assertThat(adapter.loadById(item.getId())).isEmpty();

    // act
    adapter.insert(item);

    // assert
    assertThat(adapter.loadById(item.getId()))
        .isNotNull()
        .isPresent()
        .get()
        .isEqualTo(item);
  }

  @Test
  void modify() {
    // pre-condition
    final var item = ItemGenerator.generateDefault();
    assertThat(adapter.loadById(item.getId())).isEmpty();
    adapter.insert(item);
    assertThat(adapter.loadById(item.getId())).isPresent();

    // act
    final var newTitle = "newTitle";
    item.setTitle(newTitle);
    adapter.modify(item);

    // assert
    assertThat(adapter.loadById(item.getId()))
        .isPresent()
        .get()
        .satisfies(itm -> {
          assertThat(itm.getId()).isEqualTo(item.getId());
          assertThat(itm.getCode()).isEqualTo(item.getCode());
          assertThat(itm.getTitle()).isEqualTo(newTitle);
        });
  }

  @Test
  void exists() {
    // pre-condition
    final var item = ItemGenerator.generateDefault();
    assertThat(adapter.exists(item.getCode())).isFalse();

    // arrange
    adapter.insert(item);

    // act + assert
    assertThat(adapter.exists(item.getCode())).isTrue();
  }
}
