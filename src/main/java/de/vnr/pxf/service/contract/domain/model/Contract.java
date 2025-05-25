package de.vnr.pxf.service.contract.domain.model;

import de.vnr.pxf.service.base.exception.OperationNotPossibleException;
import de.vnr.pxf.service.contract.domain.model.value.ContractState;
import de.vnr.pxf.service.contract.domain.model.value.ContractTimeline;
import java.time.ZoneId;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Contract {

  private final UUID id;

  private final UUID orderId;

  private final UUID customerId;

  private final UUID offerId;

  private ContractState state = ContractState.PENDING;

  private ContractTimeline timeline;

  public Contract(Order order) {
    this(UUID.randomUUID(), order.getId(), order.getCustomerId(), order.getOfferId());
  }

  public void activate(ZoneId timezone) {
    if (!ContractState.PENDING.equals(state)) {
      throw new OperationNotPossibleException("contract:start", "The contract state is not pending.");
    }

    this.timeline = new ContractTimeline(timezone);
    this.state = ContractState.ACTIVE;
  }

  public void complete(ZoneId timezone) {
    if (!ContractState.ACTIVE.equals(state)) {
      throw new OperationNotPossibleException("contract:complete", "The contract state is not active.");
    }

    this.timeline.end(timezone);
    this.state = ContractState.COMPLETED;
  }
}
