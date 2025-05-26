package de.vnr.pxf.service.contract.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.vnr.pxf.service.base.configuration.RootConfiguration;
import de.vnr.pxf.service.base.test.ValidationTestConfiguration;
import de.vnr.pxf.service.contract.application.port.api.usecase.ManageContractUseCase;
import de.vnr.pxf.service.contract.application.port.api.usecase.ManageContractUseCase.CreateContractCommand;
import de.vnr.pxf.service.contract.application.port.api.view.ContractView;
import de.vnr.pxf.service.contract.application.port.api.view.OrderView;
import de.vnr.pxf.service.contract.application.port.resource.ContractPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ValidationTestConfiguration.class, ContractService.class})
@MockitoBean(types = {RootConfiguration.class, OrderView.class, ContractView.class, ContractPort.class})
class ContractServiceValidationTest {

  @Autowired
  private ManageContractUseCase useCase;

  @Test
  void createContract_whenMissingOrderId_throwsConstraintViolationException() {
    // arrange
    final var command = new CreateContractCommand(null);

    // act + assert
    assertThatThrownBy(() -> useCase.createContract(command))
        .isInstanceOf(jakarta.validation.ConstraintViolationException.class);
  }

  @Test
  void activateContract_whenMissingContractId_throwsConstraintViolationException() {    // act + assert
    assertThatThrownBy(() -> useCase.activateContract(null))
        .isInstanceOf(jakarta.validation.ConstraintViolationException.class);
  }

  @Test
  void completeContract_whenMissingContractId_throwsConstraintViolationException() {
    // act + assert
    assertThatThrownBy(() -> useCase.completeContract(null))
        .isInstanceOf(jakarta.validation.ConstraintViolationException.class);
  }
}
