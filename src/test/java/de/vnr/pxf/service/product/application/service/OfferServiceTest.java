package de.vnr.pxf.service.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.base.test.ValidationTestConfiguration;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageOfferUseCase;
import de.vnr.pxf.service.product.application.port.resource.OfferPort;
import de.vnr.pxf.service.product.application.port.resource.ProductPort;
import de.vnr.pxf.service.product.domain.model.Offer;
import de.vnr.pxf.service.product.domain.model.generator.OfferGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, OfferService.class})
class OfferServiceTest {

  @Autowired
  private ManageOfferUseCase useCase;

  @MockitoBean
  private ProductPort productPort;

  @MockitoBean
  private OfferPort offerPort;

  @Test
  void createOffer_whenProductExistsAndCodeNotInUse_createsOffer() {
    // arrange
    final var command = new ManageOfferUseCase.CreateOfferCommand(
        OfferGenerator.DEFAULT_PRODUCT_ID,
        OfferGenerator.DEFAULT_CODE,
        OfferGenerator.DEFAULT_PRICE
    );

    when(productPort.exists(command.productId())).thenReturn(true);

    // act + assert
    assertThat(useCase.createOffer(command))
        .isNotNull();

    final var offerCaptor = ArgumentCaptor.forClass(Offer.class);
    verify(offerPort).insert(offerCaptor.capture());
    assertThat(offerCaptor.getValue())
        .isNotNull()
        .satisfies(o -> {
          assertThat(o.getId()).isNotNull();
          assertThat(o.getProductId()).isEqualTo(command.productId());
          assertThat(o.getCode()).isEqualTo(command.code());
          assertThat(o.getPrice()).isEqualTo(command.price());
        });
  }
}
