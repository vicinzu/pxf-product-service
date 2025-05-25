package de.vnr.pxf.service.contract.domain.model.generator;

import de.vnr.pxf.service.contract.domain.model.Contract;
import de.vnr.pxf.service.contract.domain.model.value.ContractState;
import de.vnr.pxf.service.contract.domain.model.value.ContractTimeline;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

public class ContractGenerator {

  public static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();
  public static final UUID DEFAULT_ID = UUID.fromString("00000000-0000-0000-0000-000000000006");
  public static final UUID DEFAULT_ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000005");
  public static final UUID DEFAULT_CUSTOMER_ID = UUID.fromString("00000000-0000-0000-0000-000000000004");
  public static final UUID DEFAULT_OFFER_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");
  public static final ContractState DEFAULT_STATE = ContractState.COMPLETED;
  public static final LocalDate DEFAULT_TIMELINE_START = LocalDate.now();
  public static final LocalDate DEFAULT_TIMELINE_END = DEFAULT_TIMELINE_START.plusYears(1);
  public static final ContractTimeline DEFAULT_TIMELINE = new ContractTimeline(
      DEFAULT_TIMELINE_START,
      DEFAULT_TIMELINE_END
  );

  public static Contract generateDefault() {
    return generateWithId(DEFAULT_ID, ContractState.COMPLETED);
  }

  public static Contract generateDefault(ContractState state) {
    return generateWithId(DEFAULT_ID, state);
  }

  public static Contract generateRandom() {
    return generateWithId(UUID.randomUUID(), ContractState.ACTIVE);
  }

  private static Contract generateWithId(UUID contractId, ContractState state) {
    final var timeline = ContractState.PENDING.equals(state) ? null : new ContractTimeline(
        DEFAULT_TIMELINE_START,
        ContractState.COMPLETED.equals(state) ? DEFAULT_TIMELINE_END : null
    );

    return new Contract(
        contractId,
        DEFAULT_ORDER_ID,
        DEFAULT_CUSTOMER_ID,
        DEFAULT_OFFER_ID,
        state,
        timeline
    );
  }
}
