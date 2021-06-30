package com.miranda.ot.client

import com.miranda.ot.FretesServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory {
  @Singleton
    fun fretesClientStub(@GrpcChannel ("fretes") channel: ManagedChannel): FretesServiceGrpc.FretesServiceBlockingStub? {
     return FretesServiceGrpc.newBlockingStub(channel) }

    //  @Singleton
    //  fun fretesClientStubAlternativo(): FretesServiceGrpc.FretesServiceBlockingStub? {
    //      val channel : ManagedChannel = ManagedChannelBuilder
    //       .forAddress("localhost",5001)
    //       .maxRetryAttempts(10)
    //       .usePlaintext()
    //       .usePlaintext()
    //       .build()
    //   return FretesServiceGrpc.newBlockingStub(channel)
    //}
}