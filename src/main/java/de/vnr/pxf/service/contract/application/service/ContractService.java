package de.vnr.pxf.service.contract.application.service;

import de.vnr.pxf.service.base.configuration.RootConfiguration;
import de.vnr.pxf.service.contract.application.port.api.usecase.ManageContractUseCase;
import de.vnr.pxf.service.contract.application.port.api.view.ContractView;
import de.vnr.pxf.service.contract.application.port.api.view.OrderView;
import de.vnr.pxf.service.contract.application.port.resource.ContractPort;
import de.vnr.pxf.service.contract.domain.model.Contract;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractService implements ManageContractUseCase {

  private final RootConfiguration configuration;
  private final OrderView orderView;
  private final ContractView contractView;
  private final ContractPort contractPort;

  @Override
  public UUID createContract(CreateContractCommand command) {
    final var order = orderView.getById(command.orderId());
    final var contract = new Contract(order);
    contractPort.insert(contract);

    return contract.getId();
  }

  @Override
  public void activateContract(UUID contractId) {
    final var zoneId = configuration.getZoneId();
    final var contract = contractView.getById(contractId);
    contract.activate(zoneId);

    contractPort.modify(contract);
  }

  @Override
  public void completeContract(UUID contractId) {
    final var zoneId = configuration.getZoneId();
    final var contract = contractView.getById(contractId);
    contract.complete(zoneId);

    contractPort.modify(contract);
  }
}
