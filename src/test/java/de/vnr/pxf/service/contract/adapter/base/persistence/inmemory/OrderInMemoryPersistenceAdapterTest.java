package de.vnr.pxf.service.contract.adapter.base.persistence.inmemory;

import static org.assertj.core.api.Assertions.assertThat;

import de.vnr.pxf.service.contract.domain.model.generator.OrderGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderInMemoryPersistenceAdapterTest {

  private OrderInMemoryPersistenceAdapter adapter;

  @BeforeEach
  void setUp() {
    // reset the adapter before each test
    adapter = new OrderInMemoryPersistenceAdapter();
  }

  @Test
  void loadById() {
    // pre-condition
    final var order = OrderGenerator.generateDefault();
    assertThat(adapter.loadById(order.getId())).isEmpty();

    // arrange
    adapter.insert(order);

    //act + assert
    assertThat(adapter.loadById(order.getId()))
        .isPresent()
        .get()
        .isEqualTo(order);
  }

  @Test
  void insert() {
    // pre-condition
    final var order = OrderGenerator.generateDefault();
    assertThat(adapter.loadById(order.getId())).isEmpty();

    // act
    adapter.insert(order);

    // assert
    assertThat(adapter.loadById(order.getId()))
        .isNotNull()
        .isPresent()
        .get()
        .isEqualTo(order);
  }
}
