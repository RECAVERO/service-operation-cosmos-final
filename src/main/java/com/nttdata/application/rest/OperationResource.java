package com.nttdata.application.rest;

import com.nttdata.btask.rest_client.ExchangeApi;
import com.nttdata.btask.rest_client.WalletApi;
import com.nttdata.domain.models.ExchangeDto;
import com.nttdata.domain.models.ResponseDto;
import com.nttdata.domain.models.WalletDto;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.redis.client.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/operations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OperationResource {


  @RestClient
  WalletApi walletApi;

  @RestClient
  ExchangeApi exchangeApi;

  @POST
  @Path("/register")
  @Counted(name = "count_register_wallet_brenda")
  @Timed(name = "time_register_wallet_brenda")
  @Fallback(fallbackMethod = "fallbackRegisterWallet")
  public Uni<ResponseDto<WalletDto>> registerWallet(WalletDto walletDto) {
    ResponseDto<WalletDto> responseDto = new ResponseDto<>();
    return walletApi.getWallet(walletDto).map(c->{
      if(c == null){
        responseDto.setStatus("ok");
      }else{
        responseDto.setStatus("ko");
      }
      return responseDto;
    }).call(doc->{
      if(responseDto.getStatus().equals("ok")){
        responseDto.setStatus("201");
        responseDto.setMsg("Se Registro Correctamente ...");
        responseDto.setObject(walletDto);
        return walletApi.add(walletDto).replaceWith(walletDto);
      }else{
        responseDto.setStatus("404");
        responseDto.setMsg("Ya existe registrado el number cell ...");
        return Uni.createFrom().item(walletDto);
      }
    });
  }

  public Uni<ResponseDto<WalletDto>> fallbackRegisterWallet(WalletDto walletDto) {
    ResponseDto res = new ResponseDto();
    res.setStatus("204");
    res.setMsg("Servicio no disponile intente en una horas nuevamente, porfavor ...");
    return Uni.createFrom().item(res);
  }

  @POST
  @Path("/exchanges/buys")
  @Counted(name = "count_register_wallet_petition")
  @Timed(name = "time_register_wallet_petition")
  @Fallback(fallbackMethod = "fallbackRegisterPetition")
  public Uni<ResponseDto> registerPetition(ExchangeDto exchangeDto){
    ResponseDto res = new ResponseDto();
    res.setStatus("200");
    res.setMsg("Se proceso Correctamente");
    res.setObject(exchangeDto);
    return exchangeApi.registerPetition(exchangeDto).map(c->{
      ExchangeDto exchange = new ExchangeDto();
      exchange.setNumberTransaction(c.getNumberTransaction());
      exchange.setNumberTelephone(c.getNumberTelephone());
      exchange.setAmount(c.getAmount());
      exchange.setTypeDocument(c.getTypeDocument());
      exchange.setTypePay(c.getTypePay());
      exchange.setNumberDocument(c.getNumberDocument());
      res.setObject(exchange);
      return res;
    });
  }

  public Uni<ResponseDto> fallbackRegisterPetition(ExchangeDto exchangeDto) {
    ResponseDto res = new ResponseDto();
    res.setStatus("204");
    res.setMsg("Servicio no disponile intente en una horas nuevamente, porfavor ...");
    return Uni.createFrom().item(res);
  }

  @POST
  @Path("/exchanges/pay")
  @Counted(name = "count_register_wallet_pay")
  @Timed(name = "time_register_wallet_pay")
  public Uni<ResponseDto> registerPay(ExchangeDto exchangeDto){
    ResponseDto res = new ResponseDto();
    res.setStatus("200");
    res.setMsg("Se proceso Correctamente");

    //res.setObject(exchangeDto);
      return exchangeApi.findByNumberTransaction(exchangeDto).map(cc->{
        res.setNumberTelephone(cc.getNumberTelephone());
        return res;
      }).call(doc->{
        WalletDto w = new WalletDto();
        w.setNumberTelephone(doc.getNumberTelephone());
        return walletApi.getWallet(w).map(x->{
          res.setObject(x);
          System.out.println(x);
          return res;
      });
      /*return walletApi.getWallet(w).map(x->{
        System.out.println(x);
        res.setObject(x);
        return res;
      });*/

  });
  }
}
