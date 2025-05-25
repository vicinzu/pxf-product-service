package de.vnr.pxf.service.contract.application.port.api.view;

import de.vnr.pxf.service.contract.application.port.resource.ContractPort;
import de.vnr.pxf.service.contract.domain.model.Contract;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractView {

  private final ContractPort contractPort;

  public Contract getById(UUID contractId) {
    return contractPort.loadById(contractId)
        .orElseThrow(() -> new NoSuchElementException("Product with id " + contractId + " not found"));
  }
}
