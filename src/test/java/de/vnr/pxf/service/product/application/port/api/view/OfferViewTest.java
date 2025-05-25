package de.vnr.pxf.service.product.application.port.api.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.product.application.port.resource.OfferPort;
import de.vnr.pxf.service.product.domain.model.generator.OfferGenerator;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OfferViewTest {

  @InjectMocks
  private OfferView offerView;

  @Mock
  private OfferPort offerPort;

  @Test
  void existsById_whenProductExists_returnsTrue() {
    // arrange
    final var productId = OfferGenerator.DEFAULT_ID;
    when(offerPort.exists(productId)).thenReturn(true);

    // act + assert
    assertThat(offerView.existsById(productId)).isTrue();
  }

  @Test
  void existsById_whenProductDoesNotExist_returnsFalse() {
    // arrange
    final var offerId = OfferGenerator.DEFAULT_ID;
    when(offerPort.exists(offerId)).thenReturn(false);

    // act + assert
    assertThat(offerView.existsById(offerId)).isFalse();
  }

  @Test
  void getById_whenOfferExists_returnsOffer() {
    // arrange
    final var offer = OfferGenerator.generateDefault();
    when(offerPort.loadById(offer.getId())).thenReturn(Optional.of(offer));

    // act + assert
    assertThat(offerView.getById(offer.getId()))
        .isNotNull()
        .isEqualTo(offer);
  }

  @Test
  void getById_whenOfferDoesNotExist_throwsNoSuchElementException() {
    // arrange
    final var offerId = OfferGenerator.DEFAULT_ID;
    when(offerPort.loadById(offerId)).thenReturn(Optional.empty());

    // act + assert
    assertThatThrownBy(() -> offerView.getById(offerId))
        .isInstanceOf(NoSuchElementException.class);
  }
}
