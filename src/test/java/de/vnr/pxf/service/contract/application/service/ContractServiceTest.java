package de.vnr.pxf.service.contract.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.base.configuration.RootConfiguration;
import de.vnr.pxf.service.base.test.ValidationTestConfiguration;
import de.vnr.pxf.service.contract.application.port.api.usecase.ManageContractUseCase;
import de.vnr.pxf.service.contract.application.port.api.usecase.ManageContractUseCase.CreateContractCommand;
import de.vnr.pxf.service.contract.application.port.api.view.ContractView;
import de.vnr.pxf.service.contract.application.port.api.view.OrderView;
import de.vnr.pxf.service.contract.application.port.resource.ContractPort;
import de.vnr.pxf.service.contract.domain.model.Contract;
import de.vnr.pxf.service.contract.domain.model.generator.ContractGenerator;
import de.vnr.pxf.service.contract.domain.model.generator.OrderGenerator;
import de.vnr.pxf.service.contract.domain.model.value.ContractState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, RootConfiguration.class, ContractService.class})
class ContractServiceTest {

  @Autowired
  private ManageContractUseCase useCase;

  @MockitoBean
  private OrderView orderView;

  @MockitoBean
  private ContractView contractView;

  @MockitoBean
  private ContractPort contractPort;

  @Test
  void createContract_whenOrderExists_createsContract() {
    // arrange
    final var order = OrderGenerator.generateDefault();
    when(orderView.getById(order.getId())).thenReturn(order);

    final var command = new CreateContractCommand(
        order.getId()
    );

    // act + assert
    assertThat(useCase.createContract(command))
        .isNotNull();

    final var contractCaptor = ArgumentCaptor.forClass(Contract.class);
    verify(contractPort).insert(contractCaptor.capture());
    assertThat(contractCaptor.getValue())
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
  void activateContract_whenContractExists_updatesStateToActive() {
    // arrange
    final var contract = ContractGenerator.generateDefault(ContractState.PENDING);
    when(contractView.getById(contract.getId())).thenReturn(contract);

    // act
    useCase.activateContract(contract.getId());

    // assert
    final var contractCaptor = ArgumentCaptor.forClass(Contract.class);
    verify(contractPort).modify(contractCaptor.capture());
    assertThat(contractCaptor.getValue())
        .isNotNull()
        .satisfies(c -> {
          assertThat(c.getId()).isEqualTo(contract.getId());
          assertThat(c.getState()).isEqualTo(ContractState.ACTIVE);
          assertThat(c.getTimeline())
              .isNotNull()
              .satisfies(tl -> {
                assertThat(tl.getStartDate()).isNotNull();
                assertThat(tl.getEndDate()).isNull();
              });
        });
  }

  @Test
  void completeContract_whenContractExists_updatesStateToCompleted() {
    // arrange
    final var contract = ContractGenerator.generateDefault(ContractState.ACTIVE);
    when(contractView.getById(contract.getId())).thenReturn(contract);

    // act
    useCase.completeContract(contract.getId());

    // assert
    final var contractCaptor = ArgumentCaptor.forClass(Contract.class);
    verify(contractPort).modify(contractCaptor.capture());
    assertThat(contractCaptor.getValue())
        .isNotNull()
        .satisfies(c -> {
          assertThat(c.getId()).isEqualTo(contract.getId());
          assertThat(c.getState()).isEqualTo(ContractState.COMPLETED);
          assertThat(c.getTimeline())
              .isNotNull()
              .satisfies(tl -> {
                assertThat(tl.getStartDate()).isNotNull();
                assertThat(tl.getEndDate()).isNotNull();
              });
        });
  }
}
