package com.reactive.application.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.reactive.application.model.Franquicia;

public interface FranquiciaRepository extends R2dbcRepository<Franquicia, Long> {

}
