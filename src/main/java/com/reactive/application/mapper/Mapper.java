package com.reactive.application.mapper;

import com.reactive.application.DTO.FranquiciaDTO;
import com.reactive.application.DTO.ProductoDTO;
import com.reactive.application.DTO.SucursalDTO;
import com.reactive.application.model.Franquicia;
import com.reactive.application.model.Producto;
import com.reactive.application.model.Sucursal;

public class Mapper {
    
    public static Franquicia toFranquicia(FranquiciaDTO franquiciaDTO){
        return Franquicia
        .builder()
        .id(franquiciaDTO.getId())
        .nombre(franquiciaDTO.getNombre())
        .build();
    }

      public static FranquiciaDTO toFranquiciaDTO(Franquicia franquicia){
        return FranquiciaDTO
        .builder()
        .id(franquicia.getId())
        .nombre(franquicia.getNombre())
        .build();
    }

    public static Sucursal toSucursal(SucursalDTO sucursalDTO){
        return Sucursal
        .builder()
        .id(sucursalDTO.getId())
        .nombre(sucursalDTO.getNombre())
        .franquiciaId(sucursalDTO.getFranquiciaId())
        .build();
    }

     public static SucursalDTO toSucursalDTO(Sucursal sucursal){
        return SucursalDTO
        .builder()
        .id(sucursal.getId())
        .nombre(sucursal.getNombre())
        .franquiciaId(sucursal.getFranquiciaId())
        .build();
    }

    public static Producto toProducto(ProductoDTO productoDTO){
        return Producto
        .builder()
        .id(productoDTO.getId())
        .nombre(productoDTO.getNombre())
        .stock(productoDTO.getStock())
        .sucursalId(productoDTO.getSucursalId())
        .build();

    }

    public static ProductoDTO toProductoDTO(Producto producto){
        return ProductoDTO
        .builder()
        .id(producto.getId())
        .nombre(producto.getNombre())
        .stock(producto.getStock())
        .sucursalId(producto.getSucursalId())
        .build();

    }
}
