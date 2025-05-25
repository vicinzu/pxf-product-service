package de.vnr.pxf.service.contract.adapter.base.persistence.inmemory;

import static org.assertj.core.api.Assertions.assertThat;

import de.vnr.pxf.service.contract.domain.model.generator.ContractGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContractInMemoryPersistenceAdapterTest {

  private ContractInMemoryPersistenceAdapter adapter;

  @BeforeEach
  void setUp() {
    // reset the adapter before each test
    adapter = new ContractInMemoryPersistenceAdapter();
  }

  @Test
  void loadById() {
    // pre-condition
    final var contract = ContractGenerator.generateDefault();
    assertThat(adapter.loadById(contract.getId())).isEmpty();

    // arrange
    adapter.insert(contract);

    //act + assert
    assertThat(adapter.loadById(contract.getId()))
        .isPresent()
        .get()
        .isEqualTo(contract);
  }

  @Test
  void insert() {
    // pre-condition
    final var contract = ContractGenerator.generateDefault();
    assertThat(adapter.loadById(contract.getId())).isEmpty();

    // act
    adapter.insert(contract);

    // assert
    assertThat(adapter.loadById(contract.getId()))
        .isNotNull()
        .isPresent()
        .get()
        .isEqualTo(contract);
  }

  @Test
  void modify() {
    // pre-condition
    final var contract = ContractGenerator.generateDefault();
    assertThat(adapter.loadById(contract.getId())).isEmpty();
    adapter.insert(contract);
    assertThat(adapter.loadById(contract.getId())).isPresent();

    // act
    adapter.modify(contract);

    // assert
    assertThat(adapter.loadById(contract.getId()))
        .isNotNull()
        .isPresent()
        .get()
        .isEqualTo(contract);
  }
}
