package de.vnr.pxf.service.base.configuration;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.stereotype.Component;

@Component
public class RootConfiguration {

  private static final ZoneId DEFAULT_ZONE_ID = TimeZone.getTimeZone(Locale.GERMANY.toLanguageTag()).toZoneId();

  public ZoneId getZoneId() {
    return DEFAULT_ZONE_ID;
  }
}
