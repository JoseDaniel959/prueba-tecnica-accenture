package com.reactive.application.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.reactive.application.model.Sucursal;

public interface SucursalRepository extends R2dbcRepository<Sucursal,Long>{
    
}
