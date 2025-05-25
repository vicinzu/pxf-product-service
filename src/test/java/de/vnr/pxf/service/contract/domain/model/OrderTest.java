package de.vnr.pxf.service.contract.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.contract.domain.model.generator.OrderGenerator;
import de.vnr.pxf.service.contract.domain.store.OfferStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {

  private Order order;

  @BeforeEach
  void setUp() {
    // reset the order before each test
    order = OrderGenerator.generateDefault();
  }

  @Test
  void construct_whenWithId_createsOrder() {
    // arrange
    final var id = OrderGenerator.DEFAULT_ID;
    final var customerId = OrderGenerator.DEFAULT_CUSTOMER_ID;
    final var offerId = OrderGenerator.DEFAULT_OFFER_ID;

    // act + assert
    assertThat(new Order(id, customerId, offerId))
        .isNotNull()
        .satisfies(order -> {
          assertThat(order.getId()).isEqualTo(id);
          assertThat(order.getCustomerId()).isEqualTo(customerId);
          assertThat(order.getOfferId()).isEqualTo(offerId);
        });
  }

  @Test
  void construct_whenWhenProductNotExists_throwsException() {
    // arrange
    final var customerId = OrderGenerator.DEFAULT_CUSTOMER_ID;
    final var offerId = OrderGenerator.DEFAULT_OFFER_ID;

    final var offerStorage = mock(OfferStore.class);
    when(offerStorage.exists(offerId)).thenReturn(false);

    // act + assert
    assertThat(new Order(offerStorage, customerId, offerId))
        .isNotNull()
        .satisfies(order -> {
          assertThat(order.getId()).isNotNull();
          assertThat(order.getCustomerId()).isEqualTo(customerId);
          assertThat(order.getOfferId()).isEqualTo(offerId);
        });
  }

  @Test
  void construct_whenOfferExists_createsOrder() {
    // arrange
    final var customerId = OrderGenerator.DEFAULT_CUSTOMER_ID;
    final var offerId = OrderGenerator.DEFAULT_OFFER_ID;

    final var offerStorage = mock(OfferStore.class);
    when(offerStorage.exists(offerId)).thenReturn(true);

    // act + assert
    assertThat(new Order(offerStorage, customerId, offerId))
        .isNotNull()
        .satisfies(order -> {
          assertThat(order.getId()).isNotNull();
          assertThat(order.getCustomerId()).isEqualTo(customerId);
          assertThat(order.getOfferId()).isEqualTo(offerId);
        });
  }

  @Test
  void getId() {
    // act + assert
    assertThat(order.getId())
        .isNotNull()
        .isEqualTo(OrderGenerator.DEFAULT_ID);
  }

  @Test
  void getCustomerId() {
    // act + assert
    assertThat(order.getCustomerId())
        .isNotNull()
        .isEqualTo(OrderGenerator.DEFAULT_CUSTOMER_ID);
  }

  @Test
  void getOfferId() {
    // act + assert
    assertThat(order.getOfferId())
        .isNotNull()
        .isEqualTo(OrderGenerator.DEFAULT_OFFER_ID);
  }
}
