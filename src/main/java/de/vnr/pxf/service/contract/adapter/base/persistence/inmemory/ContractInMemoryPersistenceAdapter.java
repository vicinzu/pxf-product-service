package de.vnr.pxf.service.contract.adapter.base.persistence.inmemory;

import de.vnr.pxf.service.contract.application.port.resource.ContractPort;
import de.vnr.pxf.service.contract.domain.model.Contract;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ContractInMemoryPersistenceAdapter implements ContractPort {

  private final Map<UUID, Contract> idStorage = new HashMap<>();

  @Override
  public Optional<Contract> loadById(UUID id) {
    return Optional.ofNullable(idStorage.get(id));
  }

  @Override
  public void insert(Contract contract) {
    idStorage.put(contract.getId(), contract);
  }

  @Override
  public void modify(Contract order) {
    this.insert(order);
  }
}
