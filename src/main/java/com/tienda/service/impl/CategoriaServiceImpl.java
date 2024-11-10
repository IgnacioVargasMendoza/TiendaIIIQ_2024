
package com.tienda.service.impl;

import com.tienda.dao.CategoriaDao;
import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ignac
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {
    
    //Anotacion para inyectar dependencias. En cualquier metodo usado, se inyectara y usara
    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public List<Categoria> getCategorias(boolean activos) {
        //Lista para almacenar todos los registros encontrados en la base de datos
        List<Categoria> lista = categoriaDao.findAll();
        
        //Si hay activos
        if(activos){
            //Remueva de la lista los elementos donde el atributo activa sea false.
            lista.removeIf(e -> !e.isActivo());
        }
        
        return lista;
    }
    
}
