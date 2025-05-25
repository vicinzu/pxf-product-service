package de.vnr.pxf.service.contract.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.vnr.pxf.service.base.exception.OperationNotPossibleException;
import de.vnr.pxf.service.contract.domain.model.generator.ContractGenerator;
import de.vnr.pxf.service.contract.domain.model.generator.OrderGenerator;
import de.vnr.pxf.service.contract.domain.model.value.ContractState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContractTest {

  private Contract contract;

  @BeforeEach
  void setUp() {
    // reset the contract before each test
    contract = ContractGenerator.generateDefault();
  }

  @Test
  void construct_whenWithId_returnsContract() {
    final var id = ContractGenerator.DEFAULT_ID;
    final var orderId = ContractGenerator.DEFAULT_ORDER_ID;
    final var customerId = ContractGenerator.DEFAULT_CUSTOMER_ID;
    final var offerId = ContractGenerator.DEFAULT_OFFER_ID;

    assertThat(new Contract(id, orderId, customerId, offerId))
        .isNotNull()
        .satisfies(c -> {
          assertThat(c.getId()).isEqualTo(id);
          assertThat(c.getOrderId()).isEqualTo(orderId);
          assertThat(c.getCustomerId()).isEqualTo(customerId);
          assertThat(c.getOfferId()).isEqualTo(offerId);
          assertThat(c.getState()).isEqualTo(ContractState.PENDING);
          assertThat(c.getTimeline()).isNull();
        });
  }

  @Test
  void construct_whenWithOrder_returnsContact() {
    final var order = OrderGenerator.generateDefault();

    assertThat(new Contract(order))
        .isNotNull()
        .satisfies(c -> {
          assertThat(c.getId()).isNotNull();
          assertThat(c.getOrderId()).isEqualTo(order.getId());
          assertThat(c.getCustomerId()).isEqualTo(order.getCustomerId());
          assertThat(c.getOfferId()).isEqualTo(order.getOfferId());
          assertThat(c.getState()).isEqualTo(ContractState.PENDING);
          assertThat(c.getTimeline()).isNull();
        });
  }

  @Test
  void getId() {
    assertThat(contract.getId()).isEqualTo(ContractGenerator.DEFAULT_ID);
  }

  @Test
  void getCustomerId() {
    assertThat(contract.getCustomerId()).isEqualTo(ContractGenerator.DEFAULT_CUSTOMER_ID);
  }

  @Test
  void getOrderId() {
    assertThat(contract.getOrderId()).isEqualTo(ContractGenerator.DEFAULT_ORDER_ID);
  }

  @Test
  void getOfferId() {
    assertThat(contract.getOfferId()).isEqualTo(ContractGenerator.DEFAULT_OFFER_ID);
  }

  @Test
  void getState() {
    assertThat(contract.getState()).isEqualTo(ContractGenerator.DEFAULT_STATE);
  }

  @Test
  void getTimeline() {
    assertThat(contract.getTimeline()).isEqualTo(ContractGenerator.DEFAULT_TIMELINE);
  }

  @Test
  void activate_whenContractIsNotPending_throwsException() {
    // pre-condition
    contract = ContractGenerator.generateDefault(ContractState.ACTIVE);
    assertThat(contract.getState()).isEqualTo(ContractState.ACTIVE);
    assertThat(contract.getTimeline().getStartDate()).isNotNull();
    assertThat(contract.getTimeline().getEndDate()).isNull();

    // act + assert
    assertThatThrownBy(() -> contract.activate(ContractGenerator.DEFAULT_ZONE_ID))
        .isInstanceOfSatisfying(OperationNotPossibleException.class, e ->
            assertThat(e.getOperationId()).isNotNull()
        );
  }

  @Test
  void activate_whenContractIsPending_thenActivatesContract() {
    // pre-condition
    contract = ContractGenerator.generateDefault(ContractState.PENDING);
    assertThat(contract.getState()).isEqualTo(ContractState.PENDING);
    assertThat(contract.getTimeline()).isNull();

    // act
    contract.activate(ContractGenerator.DEFAULT_ZONE_ID);

    // assert
    assertThat(contract.getState()).isEqualTo(ContractState.ACTIVE);
    assertThat(contract.getTimeline()).isNotNull();
    assertThat(contract.getTimeline().getStartDate()).isNotNull();
    assertThat(contract.getTimeline().getEndDate()).isNull();
  }

  @Test
  void complete_whenContractIsNotActive_throwsException() {
    // pre-condition
    contract = ContractGenerator.generateDefault(ContractState.PENDING);
    assertThat(contract.getState()).isEqualTo(ContractState.PENDING);
    assertThat(contract.getTimeline()).isNull();

    // act + assert
    assertThatThrownBy(() -> contract.complete(ContractGenerator.DEFAULT_ZONE_ID))
        .isInstanceOfSatisfying(OperationNotPossibleException.class, e ->
            assertThat(e.getOperationId()).isNotNull()
        );
  }

  @Test
  void complete_whenContractIsActive_thenCompletesContract() {
    // pre-condition
    contract = ContractGenerator.generateDefault(ContractState.ACTIVE);
    assertThat(contract.getState()).isEqualTo(ContractState.ACTIVE);
    assertThat(contract.getTimeline().getStartDate()).isNotNull();
    assertThat(contract.getTimeline().getEndDate()).isNull();

    // act
    contract.complete(ContractGenerator.DEFAULT_ZONE_ID);

    // assert
    assertThat(contract.getState()).isEqualTo(ContractState.COMPLETED);
    assertThat(contract.getTimeline().getStartDate()).isNotNull();
    assertThat(contract.getTimeline().getEndDate()).isNotNull();
  }
}
