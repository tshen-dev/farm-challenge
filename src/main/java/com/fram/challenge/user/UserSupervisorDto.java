package com.fram.challenge.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserSupervisorDto {

  private String supervisor;
  @JsonProperty("supervisor’s supervisor")
  private String supervisorSupervisor;
}
