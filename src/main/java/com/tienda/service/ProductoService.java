package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {
    
    //Se crea un objeto de manera única (sólo una instancia) para todo el proyecto y de manera automática
    @Autowired
    private ProductoRepository productoRepository;   
    @Transactional(readOnly=true)
    public List<Producto> getProductos(boolean activo) {
        var lista = productoRepository.findAll();       
        //Se valida si sólo se desean las productos activas...
        if (activo) {
            //Sólo se quieren activas...
            lista.removeIf(c -> !c.isActivo());
        }       
        return lista;
    }
    
    @Transactional(readOnly=true)
    public Producto getProducto(Producto producto) {       
        return productoRepository.findById(producto.getIdProducto())
                .orElse(null);
    }
    
    @Transactional
    public void save(Producto producto) {       
        productoRepository.save(producto);
    }
    @Transactional
    public boolean delete(Producto producto) {  
        try {
            productoRepository.delete(producto);
            productoRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
