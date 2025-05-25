package de.vnr.pxf.service.product.adapter.base.persistence.inmemory;

import static org.assertj.core.api.Assertions.assertThat;

import de.vnr.pxf.service.product.domain.model.generator.OfferGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OfferInMemoryPersistenceAdapterTest {

  private OfferInMemoryPersistenceAdapter adapter;

  @BeforeEach
  void setUp() {
    // reset the adapter before each test
    adapter = new OfferInMemoryPersistenceAdapter();
  }

  @Test
  void loadById() {
    // pre-condition
    final var offer = OfferGenerator.generateDefault();
    assertThat(adapter.loadById(offer.getId())).isEmpty();

    // arrange
    adapter.insert(offer);

    // act + assert
    assertThat(adapter.loadById(offer.getId()))
        .isPresent()
        .get()
        .isEqualTo(offer);
  }

  @Test
  void insert() {
    // pre-condition
    final var offer = OfferGenerator.generateDefault();
    assertThat(adapter.loadById(offer.getId())).isEmpty();

    // act
    adapter.insert(offer);

    // assert
    assertThat(adapter.loadById(offer.getId()))
        .isNotNull()
        .isPresent()
        .get()
        .isEqualTo(offer);
  }

  @Test
  void exists_whenById() {
    // pre-condition
    final var offer = OfferGenerator.generateDefault();
    assertThat(adapter.exists(offer.getId())).isFalse();

    // arrange
    adapter.insert(offer);

    // act + assert
    assertThat(adapter.exists(offer.getId())).isTrue();
  }

  @Test
  void exists_whenByCode() {
    // pre-condition
    final var offer = OfferGenerator.generateDefault();
    assertThat(adapter.exists(offer.getCode())).isFalse();

    // arrange
    adapter.insert(offer);

    // act + assert
    assertThat(adapter.exists(offer.getCode())).isTrue();
  }
}
