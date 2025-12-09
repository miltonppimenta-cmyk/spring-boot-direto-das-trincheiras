package academy.devdojo.mapper;

import academy.devdojo.domain.Producers;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.request.ProducersPostRequest;
import academy.devdojo.response.ProducersGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProducerMapper {
    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);


    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(100_000))")
    Producers toProducers(ProducersPostRequest postRequest);

    Producers toProducers(ProducerPutRequest request, LocalDateTime createdAt);

    ProducersGetResponse toProducersGetResponse(Producers producer);

    List<ProducersGetResponse> toProducerGetResponseList(List<Producers> producers);
}
