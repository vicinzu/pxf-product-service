package de.vnr.pxf.service.contract.application.port.api.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import de.vnr.pxf.service.contract.application.port.resource.ContractPort;
import de.vnr.pxf.service.contract.domain.model.generator.ContractGenerator;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContractViewTest {

  @InjectMocks
  private ContractView contractView;

  @Mock
  private ContractPort contractPort;

  @Test
  void getById_whenContractExists_returnsContract() {
    // arrange
    final var contract = ContractGenerator.generateDefault();
    when(contractPort.loadById(contract.getId())).thenReturn(Optional.of(contract));

    // act + assert
    assertThat(contractView.getById(contract.getId()))
        .isNotNull()
        .isEqualTo(contract);
  }

  @Test
  void getById_whenContractDoesNotExist_throwsNoSuchElementException() {
    // arrange
    final var contractId = ContractGenerator.DEFAULT_ID;
    when(contractPort.loadById(contractId)).thenReturn(Optional.empty());

    // act + assert
    assertThatThrownBy(() -> contractView.getById(contractId))
        .isInstanceOf(NoSuchElementException.class);
  }
}
