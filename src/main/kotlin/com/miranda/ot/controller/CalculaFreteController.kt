package com.miranda.ot.controller

import com.google.protobuf.Any
import com.miranda.ot.CalculaFreteRequest
import com.miranda.ot.ErroDeMensagem
import com.miranda.ot.FretesServiceGrpc
import com.miranda.ot.ValorFreteResponse
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.exceptions.HttpStatusException
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Controller
class CalculaFreteController ( @Inject val grpc: FretesServiceGrpc.FretesServiceBlockingStub){

    val logger = LoggerFactory.getLogger(CalculaFreteController::class.java)

    @Get("/fretes")
    fun calcula(@QueryValue cep: String) // : FreteResponse{
    {
        val freteRequest = CalculaFreteRequest.newBuilder()
                          .setCep(cep)
                          .build()
        try {
            grpc.calculaFrete(freteRequest)
        }catch (e:StatusRuntimeException){

            val statusCode = e.status.code
            val description = e.status.description

                if(statusCode == Status.Code.PERMISSION_DENIED) {

                 val statusProto = StatusProto.fromThrowable(e)
                    statusProto ?: throw HttpStatusException(HttpStatus.FORBIDDEN, description)

                    val  details:Any? = statusProto.detailsList.get(0)
                    val errorDetails = details!!.unpack(ErroDeMensagem::class.java)

                    logger.error( "${errorDetails.mensagem} E TAMBEM ${errorDetails.mensagem2} ...")

                    throw  HttpStatusException(HttpStatus.UNAUTHORIZED ,
                                        "${errorDetails.mensagem} E TAMBEM ${errorDetails.mensagem2}")

                 }
            throw  HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR ,e.message)
        }

     }
}

data class  FreteResponse(val cep: String , val valor : Double){
}