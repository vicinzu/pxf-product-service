package de.vnr.pxf.service.product.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.base.exception.CodeInUseException;
import de.vnr.pxf.service.base.exception.ReferenceNotExistsException;
import de.vnr.pxf.service.product.domain.model.generator.OfferGenerator;
import de.vnr.pxf.service.product.domain.store.OfferStore;
import de.vnr.pxf.service.product.domain.store.ProductStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OfferTest {

  private Offer offer;

  @BeforeEach
  void setUp() {
    // reset the offer before each test
    offer = OfferGenerator.generateDefault();
  }

  @Test
  void construct_whenWithId_createsOffer() {
    // arrange
    final var id = OfferGenerator.DEFAULT_ID;
    final var code = OfferGenerator.DEFAULT_CODE;
    final var productId = OfferGenerator.DEFAULT_PRODUCT_ID;
    final var price = OfferGenerator.DEFAULT_PRICE;

    // act + assert
    assertThat(new Offer(id, code, productId, price))
        .isNotNull()
        .satisfies(offer -> {
          assertThat(offer.getId()).isEqualTo(id);
          assertThat(offer.getCode()).isEqualTo(code);
          assertThat(offer.getProductId()).isEqualTo(productId);
          assertThat(offer.getPrice()).isEqualTo(price);
        });
  }

  @Test
  void construct_whenCodeInUse_throwsCodeInUseException() {
    // arrange
    final var code = OfferGenerator.DEFAULT_CODE;
    final var productId = OfferGenerator.DEFAULT_PRODUCT_ID;
    final var price = OfferGenerator.DEFAULT_PRICE;

    final var offerStorage = mock(OfferStore.class);
    when(offerStorage.exists(code)).thenReturn(true);

    final var productStorage = mock(ProductStore.class);
    when(productStorage.exists(productId)).thenReturn(true);

    // act + assert
    assertThatThrownBy(() -> new Offer(offerStorage, productStorage, code, productId, price))
        .isInstanceOfSatisfying(CodeInUseException.class, e -> {
          assertThat(e.getParentId()).isNull();
          assertThat(e.getCode()).isEqualTo(code);
        });
  }

  @Test
  void construct_whenProductNotExists_throwsReferenceNotExistsException() {
    // arrange
    final var code = OfferGenerator.DEFAULT_CODE;
    final var productId = OfferGenerator.DEFAULT_PRODUCT_ID;
    final var price = OfferGenerator.DEFAULT_PRICE;

    final var offerStorage = mock(OfferStore.class);
    when(offerStorage.exists(code)).thenReturn(false);

    final var productStorage = mock(ProductStore.class);
    when(productStorage.exists(productId)).thenReturn(false);

    // act + assert
    assertThatThrownBy(() -> new Offer(offerStorage, productStorage, code, productId, price))
        .isInstanceOfSatisfying(ReferenceNotExistsException.class, e ->
            assertThat(e.getReferenceId()).isEqualTo(productId)
        );
  }

  @Test
  void getId() {
    // act + assert
    assertThat(offer.getId()).isEqualTo(OfferGenerator.DEFAULT_ID);
  }

  @Test
  void getCode() {
    // act + assert
    assertThat(offer.getCode()).isEqualTo(OfferGenerator.DEFAULT_CODE);
  }

  @Test
  void getProductId() {
    // act + assert
    assertThat(offer.getProductId()).isEqualTo(OfferGenerator.DEFAULT_PRODUCT_ID);
  }

  @Test
  void getPrice() {
    // act + assert
    assertThat(offer.getPrice()).isEqualTo(OfferGenerator.DEFAULT_PRICE);
  }
}
