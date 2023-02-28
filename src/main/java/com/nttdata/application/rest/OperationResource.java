package com.nttdata.application.rest;

import com.nttdata.btask.rest_client.WalletApi;
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

}
