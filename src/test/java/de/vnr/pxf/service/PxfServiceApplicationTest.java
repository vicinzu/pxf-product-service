package de.vnr.pxf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

class PxfServiceApplicationTest {

  @Test
  void main_whenGetsArguments_runsSpringApplicationWithArguments() {
    try (MockedStatic<SpringApplication> pfxServiceApplication = mockStatic(SpringApplication.class)) {
      // arrange
      final var appArguments = new String[]{"a", "b", "c"};

      // act
      PxfServiceApplication.main(appArguments);

      // assert
      final var appArgumentsCaptor = ArgumentCaptor.forClass(String[].class);
      pfxServiceApplication.verify(
          () -> SpringApplication.run(eq(PxfServiceApplication.class), appArgumentsCaptor.capture())
      );
      assertThat(appArgumentsCaptor.getValue())
          .isNotNull()
          .isEqualTo(appArguments);
    }
  }
}