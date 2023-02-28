package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDto {
  private int typeDocument;
  private Long numberDocument;
  private String numberTelephone;
  private int typePay;
  private double amount;
  private String numberTransaction;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
