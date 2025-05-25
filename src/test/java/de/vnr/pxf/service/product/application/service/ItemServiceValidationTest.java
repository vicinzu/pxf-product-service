package de.vnr.pxf.service.product.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.vnr.pxf.service.base.model.Code;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageItemUseCase;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageItemUseCase.CreateItemCommand;
import de.vnr.pxf.service.product.application.port.api.usecase.ManageItemUseCase.UpdateItemCommand;
import de.vnr.pxf.service.product.application.port.api.view.ItemView;
import de.vnr.pxf.service.product.application.port.api.view.ProductView;
import de.vnr.pxf.service.product.application.port.resource.ItemPort;
import de.vnr.pxf.service.product.application.service.config.ValidationTestConfiguration;
import de.vnr.pxf.service.product.domain.model.generator.ItemGenerator;
import jakarta.validation.ConstraintViolationException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, ItemService.class})
@MockitoBean(types = {ProductView.class, ItemView.class, ItemPort.class})
class ItemServiceValidationTest {

  @Autowired
  private ManageItemUseCase manageUseCase;

  @Test
  void createItem_whenMissingProductId_throwsException() {
    // arrange
    final var command = new CreateItemCommand(
        null,
        ItemGenerator.DEFAULT_CODE,
        ItemGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThatThrownBy(() -> manageUseCase.createItem(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createItem_whenMissingCode_throwsException() {
    // arrange
    final var command = new CreateItemCommand(
        UUID.randomUUID(),
        null,
        ItemGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThatThrownBy(() -> manageUseCase.createItem(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createItem_whenEmptyCode_throwsException() {
    // arrange
    final var command = new CreateItemCommand(
        UUID.randomUUID(),
        Code.NONE,
        ItemGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThatThrownBy(() -> manageUseCase.createItem(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createItem_whenMissingTitle_throwsException() {
    // arrange
    final var command = new CreateItemCommand(
        UUID.randomUUID(),
        ItemGenerator.DEFAULT_CODE,
        null
    );

    // act + assert
    assertThatThrownBy(() -> manageUseCase.createItem(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void createItem_whenBlankTitle_throwsException() {
    // arrange
    final var command = new CreateItemCommand(
        UUID.randomUUID(),
        ItemGenerator.DEFAULT_CODE,
        "   "
    );

    // act + assert
    assertThatThrownBy(() -> manageUseCase.createItem(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void updateItem_whenMissingProductId_throwsException() {
    // arrange
    final var command = new UpdateItemCommand(
        null,
        ItemGenerator.DEFAULT_ID,
        ItemGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThatThrownBy(() -> manageUseCase.updateItem(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void updateItem_whenMissingItemId_throwsException() {
    // arrange
    final var command = new UpdateItemCommand(
        UUID.randomUUID(),
        null,
        ItemGenerator.DEFAULT_TITLE
    );

    // act + assert
    assertThatThrownBy(() -> manageUseCase.updateItem(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void updateItem_whenMissingTitle_throwsException() {
    // arrange
    final var command = new UpdateItemCommand(
        UUID.randomUUID(),
        ItemGenerator.DEFAULT_ID,
        null
    );

    // act + assert
    assertThatThrownBy(() -> manageUseCase.updateItem(command))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void updateItem_whenBlankTitle_throwsException() {
    // arrange
    final var command = new UpdateItemCommand(
        UUID.randomUUID(),
        ItemGenerator.DEFAULT_ID,
        "   "
    );

    // act + assert
    assertThatThrownBy(() -> manageUseCase.updateItem(command))
        .isInstanceOf(ConstraintViolationException.class);
  }
}
