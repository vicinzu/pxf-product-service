package de.vnr.pxf.service.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.base.test.ValidationTestConfiguration;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageProductUseCase;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageProductUseCase.UpdateProductCommand;
import de.vnr.pxf.service.product.application.port.api.view.ProductView;
import de.vnr.pxf.service.product.application.port.resource.ProductPort;
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
  private ManageProductUseCase useCase;

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
    assertThat(useCase.createProduct(command))
        .isNotNull();

    final var productCaptor = ArgumentCaptor.forClass(Product.class);
    verify(productPort).insert(productCaptor.capture());
    assertThat(productCaptor.getValue())
        .isNotNull()
        .satisfies(p -> {
          assertThat(p.getId()).isNotNull();
          assertThat(p.getCode()).isEqualTo(command.code());
          assertThat(p.getTitle()).isEqualTo(command.title());
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
    useCase.updateProduct(command);

    // assert
    final var productCaptor = ArgumentCaptor.forClass(Product.class);
    verify(productPort).modify(productCaptor.capture());
    assertThat(productCaptor.getValue())
        .isNotNull()
        .satisfies(p -> {
          assertThat(p.getId()).isEqualTo(command.productId());
          assertThat(p.getTitle()).isEqualTo(command.title());
        });
  }
}
