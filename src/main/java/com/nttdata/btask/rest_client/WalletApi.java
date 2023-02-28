package com.nttdata.btask.rest_client;

import com.nttdata.domain.models.WalletDto;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient
@Path("/wallets")
public interface WalletApi {
  @POST
  Uni<WalletDto> add(WalletDto walletDto);

  @POST
  @Path("/search")
  Uni<WalletDto> getWallet(WalletDto walletDto);
}
