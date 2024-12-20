package com.tienda.service.impl;

import com.tienda.dao.ProductoDao;
import com.tienda.domain.Producto;
import com.tienda.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ignac
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    //Anotacion para inyectar dependencias. En cualquier metodo usado, se inyectara y usara
    @Autowired
    private ProductoDao productoDao;

    @Override
    public List<Producto> getProductos(boolean activos) {
        //Lista para almacenar todos los registros encontrados en la base de datos
        List<Producto> productos = productoDao.findAll();

        //Si hay activos
        if (activos) {
            //Remueva de la lista los elementos donde el atributo activa sea false.
            productos.removeIf(e -> !e.isActivo());
        }

        return productos;
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    @Transactional
    public void delete(Producto producto) {
        productoDao.delete(producto);
    }

    /*
    * Lista de productos con precio entre ordendados por descripción ConsultaAmpliada 
    * El metodo se puede llamar diferente al método del Dao, sin embargo, se 
    * utilizar el mismo nombre para ser consistentes con la informacion
     */
    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup) {
        return productoDao.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> metodoJPQL(double precioInf, double precioSup) {
        return productoDao.metodoJPQL(precioInf, precioSup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> metodoNativo(double precioInf, double precioSup) {
        return productoDao.metodoNativo(precioInf, precioSup);
    }

}
