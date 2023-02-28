package com.nttdata.btask.rest_client;

import com.nttdata.domain.models.ExchangeDto;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient
@Path("/exchanges")
public interface ExchangeApi {
  @POST
  Uni<ExchangeDto> registerPetition(ExchangeDto exchangeDto);

  @POST
  @Path("/search")
  Uni<ExchangeDto> findByNumberTransaction(ExchangeDto exchangeDto);
}
