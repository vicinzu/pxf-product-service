package de.vnr.pxf.service.contract.adapter.product.plain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.product.application.port.api.view.OfferView;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OfferPlainAdapterTest {

  @InjectMocks
  private OfferPlainAdapter offerPlainAdapter;

  @Mock
  private OfferView offerView;

  @Test
  void exists_whenNotExists_returnsFalse() {
    // arrange
    final var offerId = UUID.randomUUID();
    when(offerView.existsById(offerId)).thenReturn(false);

    // act + assert
    assertThat(offerPlainAdapter.exists(offerId)).isFalse();

    final var offerIdCaptor = ArgumentCaptor.forClass(UUID.class);
    verify(offerView).existsById(offerIdCaptor.capture());
    assertThat(offerIdCaptor.getValue()).isEqualTo(offerId);
  }

  @Test
  void exists_whenExists_returnsTrue() {
    // arrange
    final var offerId = UUID.randomUUID();
    when(offerView.existsById(offerId)).thenReturn(true);

    // act + assert
    assertThat(offerPlainAdapter.exists(offerId)).isTrue();

    final var offerIdCaptor = ArgumentCaptor.forClass(UUID.class);
    verify(offerView).existsById(offerIdCaptor.capture());
    assertThat(offerIdCaptor.getValue()).isEqualTo(offerId);
  }
}
