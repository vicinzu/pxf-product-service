package de.vnr.pxf.service.contract.application.port.resource;

import de.vnr.pxf.service.contract.domain.model.Contract;
import java.util.Optional;
import java.util.UUID;

public interface ContractPort {

  Optional<Contract> loadById(UUID id);

  void insert(Contract order);

  void modify(Contract order);
}
