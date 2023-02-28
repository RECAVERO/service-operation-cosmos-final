package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {
  private String name;
  private String lastName;
  private String email;
  private Long nroDocument;
  private int typeDocument;
  private String numberCard;
  private String numberAccount;
  private int password;
  private int typeOperation;
  private String numberTelephone;
  private double amount;
  private String registrationDate;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
