package de.vnr.pxf.service.product.adapter.base.persistence.inmemory;

import static org.assertj.core.api.Assertions.assertThat;

import de.vnr.pxf.service.product.domain.model.generator.ProductGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductInMemoryPersistenceAdapterTest {

  private ProductInMemoryPersistenceAdapter adapter;

  @BeforeEach
  void setUp() {
    // reset the adapter before each test
    adapter = new ProductInMemoryPersistenceAdapter();
  }

  @Test
  void loadById() {
    // pre-condition
    final var product = ProductGenerator.generateDefault();
    assertThat(adapter.loadById(product.getId())).isEmpty();

    // arrange
    adapter.insert(product);

    //act + assert
    assertThat(adapter.loadById(product.getId()))
        .isPresent()
        .get()
        .isEqualTo(product);
  }

  @Test
  void insert() {
    // pre-condition
    final var product = ProductGenerator.generateDefault();
    assertThat(adapter.loadById(product.getId())).isEmpty();

    // act
    adapter.insert(product);

    // assert
    assertThat(adapter.loadById(product.getId()))
        .isNotNull()
        .isPresent()
        .get()
        .isEqualTo(product);
  }

  @Test
  void modify() {
    // pre-condition
    final var product = ProductGenerator.generateDefault();
    assertThat(adapter.loadById(product.getId())).isEmpty();
    adapter.insert(product);
    assertThat(adapter.loadById(product.getId())).isPresent();

    // act
    final var newTitle = "newTitle";
    product.setTitle(newTitle);
    adapter.modify(product);

    // assert
    assertThat(adapter.loadById(product.getId()))
        .isNotNull()
        .isPresent()
        .get()
        .satisfies(prd -> {
          assertThat(prd.getId()).isEqualTo(product.getId());
          assertThat(prd.getCode()).isEqualTo(product.getCode());
          assertThat(prd.getTitle()).isEqualTo(newTitle);
        });
  }

  @Test
  void exists_whenById() {
    // pre-condition
    final var product = ProductGenerator.generateDefault();
    assertThat(adapter.exists(product.getId())).isFalse();

    // arrange
    adapter.insert(product);

    // assert
    assertThat(adapter.exists(product.getId())).isTrue();
  }

  @Test
  void exists_whenByCode() {
    // pre-condition
    final var product = ProductGenerator.generateDefault();
    assertThat(adapter.exists(product.getCode())).isFalse();

    // arrange
    adapter.insert(product);

    // assert
    assertThat(adapter.exists(product.getCode())).isTrue();
  }
}
