package com.tienda.dao;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Ignac
 */

/*
* Debe extender JpaRepository y dentro debemos de especificar el dominio y 
* el tipo de variable del ID del dominio
 */
public interface ProductoDao extends JpaRepository<Producto, Long> {

    /* 
    * Método generado automáticamente por Spring Data JPA utilizando Query Methods.
    * Busca productos cuyo precio esté dentro del rango especificado entre precioInf y precioSup,
    * y ordena los resultados por la descripción de los productos de forma ascendente.
    * Este método es ideal para consultas simples, ya que Spring genera automáticamente la consulta
    * basándose en el nombre del método, lo que lo hace fácil de mantener y leer.
     */
    public List<Producto> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup);

    /* 
    * Método que utiliza una consulta personalizada en JPQL para buscar productos.
    * Este método realiza la misma operación que el anterior, pero la consulta está definida explícitamente
    * mediante la anotación @Query. Busca productos cuyo precio esté dentro del rango especificado entre
    * precioInf y precioSup, y ordena los resultados por la descripción de los productos de forma ascendente.
    * La anotación @Param se usa para vincular los argumentos del método con los parámetros de la consulta JPQL.
    * Este enfoque es útil para consultas más complejas o cuando se necesita mayor control sobre la consulta SQL generada.
     */
    @Query(value = "SELECT a FROM Producto a where a.precio BETWEEN :precioInf AND :precioSup ORDER BY a.descripcion ASC")
    public List<Producto> metodoJPQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
    
     /*
    * Este método utiliza una consulta SQL nativa para buscar productos cuyos precios estén dentro del rango especificado
    * entre precioInf y precioSup. La consulta SQL se define explícitamente mediante la anotación @Query con el parámetro
    * nativeQuery=true, lo que indica que se debe ejecutar una consulta SQL nativa en lugar de JPQL.
    *
    * - La consulta busca productos en la tabla 'producto' y filtra aquellos cuyo precio esté entre los valores pasados como
    *   parámetros: 'precioInf' (precio mínimo) y 'precioSup' (precio máximo).
    * - Los resultados se ordenan por la columna 'descripcion' de forma ascendente.
    * - Los parámetros 'precioInf' y 'precioSup' son pasados al método y vinculados con la consulta SQL a través de la 
    *   anotación @Param.
    *
    * Este enfoque es ideal cuando se necesita una consulta SQL específica que aproveche características particulares de 
    * la base de datos o se requieran optimizaciones de rendimiento que no pueden lograrse mediante JPQL.
    * 
    * Sin embargo, el uso de SQL nativo limita la portabilidad del código, ya que las consultas SQL pueden depender de 
    * la sintaxis específica del sistema de gestión de base de datos (DBMS). Si se cambia de base de datos, las consultas 
    * SQL podrían necesitar ser reescritas.
    */
    @Query(nativeQuery=true,
            value="SELECT * FROM producto where producto.precio BETWEEN :precioInf AND :precioSup ORDER BY producto.descripcion ASC")
    public List<Producto> metodoNativo(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup); 

}
