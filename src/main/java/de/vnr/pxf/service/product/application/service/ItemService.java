package de.vnr.pxf.service.product.application.service;

import de.vnr.pxf.service.product.application.port.api.usecase.ManageItemUseCase;
import de.vnr.pxf.service.product.application.port.api.view.ItemView;
import de.vnr.pxf.service.product.application.port.api.view.ProductView;
import de.vnr.pxf.service.product.application.port.resource.ItemPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService implements ManageItemUseCase {

  private final ProductView productView;
  private final ItemView itemView;
  private final ItemPort itemPort;

  @Override
  public UUID createItem(CreateItemCommand command) {
    // arrange
    final var product = productView.getById(command.productId());

    // act
    final var item = product.createItem(itemPort, command.code(), command.title());
    itemPort.insert(item);

    return item.getId();
  }

  @Override
  public void updateItem(UpdateItemCommand command) {
    // arrange
    final var item = itemView.getById(command.itemId());

    // act
    item.setTitle(command.title());
    itemPort.modify(item);
  }
}
