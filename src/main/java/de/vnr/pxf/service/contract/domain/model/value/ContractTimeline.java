package de.vnr.pxf.service.contract.domain.model.value;

import java.time.LocalDate;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ContractTimeline {

  private final LocalDate startDate;

  private LocalDate endDate;

  public ContractTimeline(ZoneId timezone) {
    this.startDate = LocalDate.now(timezone);
  }

  public void end(ZoneId timezone) {
    this.endDate = LocalDate.now(timezone);
  }
}
