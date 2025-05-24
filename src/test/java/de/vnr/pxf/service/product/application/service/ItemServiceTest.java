package de.vnr.pxf.service.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.product.application.port.api.usecase.ManageItemUseCase;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageItemUseCase.CreateItemCommand;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageItemUseCase.UpdateItemCommand;
import de.vnr.pxf.service.product.application.port.api.view.ItemView;
import de.vnr.pxf.service.product.application.port.api.view.ProductView;
import de.vnr.pxf.service.product.application.port.resource.ItemPort;
import de.vnr.pxf.service.product.application.service.config.ValidationTestConfiguration;
import de.vnr.pxf.service.product.domain.model.Item;
import de.vnr.pxf.service.product.domain.model.generator.ItemGenerator;
import de.vnr.pxf.service.product.domain.model.generator.ProductGenerator;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, ItemService.class})
class ItemServiceTest {

  @Autowired
  private ManageItemUseCase manageUseCase;

  @MockitoBean
  private ProductView productView;

  @MockitoBean
  private ItemView itemView;

  @MockitoBean
  private ItemPort itemPort;

  @Test
  void createItem_whenProductExistsAndCodeNotInUse_createsItem() {
    // arrange#
    final var product = ProductGenerator.generateDefault();
    when(productView.getById(product.getId())).thenReturn(product);
    when(itemPort.exists(product.getId(), ItemGenerator.DEFAULT_CODE)).thenReturn(false);

    final var command = new CreateItemCommand(
        product.getId(),
        ItemGenerator.DEFAULT_CODE,
        ItemGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThat(manageUseCase.createItem(command))
        .isNotNull();

    final var itemCaptor = ArgumentCaptor.forClass(Item.class);
    verify(itemPort).insert(eq(product.getId()), itemCaptor.capture());
    assertThat(itemCaptor.getValue())
        .isNotNull()
        .satisfies(itm -> {
          assertThat(itm.getId()).isNotNull();
          assertThat(itm.getCode()).isEqualTo(command.code());
          assertThat(itm.getTitle()).isEqualTo(command.title());
        });
  }

  @Test
  void updateItem_whenItemExists_updatesItem() {
    // arrange
    final var productId = UUID.randomUUID();
    final var item = ItemGenerator.generateDefault();
    when(itemView.getById(productId, item.getId())).thenReturn(item);

    final var command = new UpdateItemCommand(
        productId,
        item.getId(),
        ItemGenerator.DEFAULT_TITLE
    );

    // act + assert
    manageUseCase.updateItem(command);

    final var itemCaptor = ArgumentCaptor.forClass(Item.class);
    verify(itemPort).modify(eq(productId), itemCaptor.capture());
    assertThat(itemCaptor.getValue())
        .isNotNull()
        .satisfies(itm -> {
          assertThat(itm.getId()).isEqualTo(command.itemId());
          assertThat(itm.getCode()).isEqualTo(item.getCode());
          assertThat(itm.getTitle()).isEqualTo(command.title());
        });
  }
}
