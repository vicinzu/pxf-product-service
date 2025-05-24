package de.vnr.pxf.service.product.adapter.base.persistence.inmemory;

import static org.assertj.core.api.Assertions.assertThat;

import de.vnr.pxf.service.product.domain.model.generator.ItemGenerator;
import java.util.UUID;
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
    final var productId = UUID.randomUUID();
    final var item = ItemGenerator.generateDefault();
    assertThat(adapter.loadById(productId, item.getId())).isEmpty();

    // arrange
    adapter.insert(productId, item);

    // act + assert
    assertThat(adapter.loadById(productId, item.getId()))
        .isPresent()
        .get()
        .isEqualTo(item);
  }

  @Test
  void insert() {
    // pre-condition
    final var productId = UUID.randomUUID();
    final var item = ItemGenerator.generateDefault();
    assertThat(adapter.loadById(productId, item.getId())).isEmpty();

    // act
    adapter.insert(productId, item);

    // assert
    assertThat(adapter.loadById(productId, item.getId()))
        .isNotNull()
        .isPresent()
        .get()
        .isEqualTo(item);
  }

  @Test
  void modify() {
    // pre-condition
    final var productId = UUID.randomUUID();
    final var item = ItemGenerator.generateDefault();
    assertThat(adapter.loadById(productId, item.getId())).isEmpty();
    adapter.insert(productId, item);
    assertThat(adapter.loadById(productId, item.getId())).isPresent();

    // act
    final var newTitle = "newTitle";
    item.setTitle(newTitle);
    adapter.modify(productId, item);

    // assert
    assertThat(adapter.loadById(productId, item.getId()))
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
    final var productId = UUID.randomUUID();
    final var item = ItemGenerator.generateDefault();
    assertThat(adapter.exists(productId, item.getCode())).isFalse();

    // arrange
    adapter.insert(productId, item);

    // act + assert
    assertThat(adapter.exists(productId, item.getCode())).isTrue();
  }
}
