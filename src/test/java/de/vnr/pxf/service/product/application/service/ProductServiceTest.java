package de.vnr.pxf.service.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.product.application.port.api.usecase.ManageProductUseCase;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageProductUseCase.UpdateProductCommand;
import de.vnr.pxf.service.product.application.port.api.view.ProductView;
import de.vnr.pxf.service.product.application.port.resource.ProductPort;
import de.vnr.pxf.service.product.application.service.config.ValidationTestConfiguration;
import de.vnr.pxf.service.product.domain.model.Product;
import de.vnr.pxf.service.product.domain.model.generator.ProductGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, ProductService.class})
class ProductServiceTest {

  @Autowired
  private ManageProductUseCase manageUseCase;

  @MockitoBean
  private ProductView productView;

  @MockitoBean
  private ProductPort productPort;

  @Test
  void createProduct_whenCodeNotInUse_createsProduct() {
    // arrange
    final var command = new ManageProductUseCase.CreateProductCommand(
        ProductGenerator.DEFAULT_CODE,
        ProductGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThat(manageUseCase.createProduct(command))
        .isNotNull();

    final var productCaptor = ArgumentCaptor.forClass(Product.class);
    verify(productPort).insert(productCaptor.capture());
    assertThat(productCaptor.getValue())
        .isNotNull()
        .satisfies(prd -> {
          assertThat(prd.getId()).isNotNull();
          assertThat(prd.getCode()).isEqualTo(command.code());
          assertThat(prd.getTitle()).isEqualTo(command.title());
        });
  }

  @Test
  void updateProduct_whenProductExists_updatesProduct() {
    // arrange
    final var product = ProductGenerator.generateDefault();
    when(productView.getById(product.getId())).thenReturn(product);

    final var command = new UpdateProductCommand(
        product.getId(),
        ProductGenerator.DEFAULT_TITLE
    );

    // act
    manageUseCase.updateProduct(command);

    // assert
    final var productCaptor = ArgumentCaptor.forClass(Product.class);
    verify(productPort).modify(productCaptor.capture());
    assertThat(productCaptor.getValue())
        .isNotNull()
        .satisfies(prd -> {
          assertThat(prd.getId()).isEqualTo(command.productId());
          assertThat(prd.getCode()).isEqualTo(product.getCode());
          assertThat(prd.getTitle()).isEqualTo(command.title());
        });
  }
}
