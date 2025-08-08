package com.tienda.service;

import com.tienda.domain.Constante;
import com.tienda.repository.ConstanteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConstanteService {
    
    //Se crea un objeto de manera única (sólo una instancia) para todo el proyecto y de manera automática
    @Autowired
    private ConstanteRepository constanteRepository;   
  
    @Transactional(readOnly=true)
    public List<Constante> getConstante() {       
        var lista = constanteRepository.findAll();
        return lista;
    }
    
    @Transactional(readOnly=true)
    public Constante getConstante(Constante constante) {       
        return constanteRepository.findById(constante.getIdConstante()).orElse(null);
    }
    
    @Transactional(readOnly=true)
    public String getValorDeAtrubuto(String atributo){    
        Constante constante = constanteRepository.findByAtributo(atributo);
        if (constante != null){
            return constante.getValor();
        }
        return constante.getValor();
    }
    
    @Transactional
    public void save(Constante constante) {       
        constanteRepository.save(constante);
    }
    @Transactional
    public boolean delete(Constante constante) {  
        try {
            constanteRepository.delete(constante);
            constanteRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
