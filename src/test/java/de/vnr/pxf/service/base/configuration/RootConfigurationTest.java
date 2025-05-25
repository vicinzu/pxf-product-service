package de.vnr.pxf.service.base.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RootConfigurationTest {

  private static final RootConfiguration rootConfiguration = new RootConfiguration();

  @Test
  void getZoneId() {
    assertThat(rootConfiguration.getZoneId())
        .isNotNull();
  }
}
