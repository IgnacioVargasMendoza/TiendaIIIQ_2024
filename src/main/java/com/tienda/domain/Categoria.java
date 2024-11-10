
package com.tienda.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Ignac
 */

//Estas anotaciones van encima de la clase y son importantes para utilizar JPA y hibernate 
@Data //Esta anotacion es importante para utilizar los metodos de hibernate
@Entity
@Table(name="categoria") //Esta anotacion es importante para enlazar la tabla de la base de datos con la clase Java
public class Categoria implements Serializable {
    
    //Esto lo va a contener todas las clases que implementen serializable
    private static final long serialVersionUID = 1L;
    
    // @Column(name="id_categoria") Hibernate cuando lee el campo en SQL, lo interpreta como id_categoria aun cuando la declaracion en java no es la misma
    // @Id, la anotacion de ID se utiliza para indicar que es la llave primaria de la tabla
    // @GeneratedValue(strategy = GenerationType.IDENTITY), se utiliza porque el id_categoria tiene un atributo de auto_increment el cual se conoce como IDENTITY
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private Long idCategoria; 
    
    private String descripcion;
    
    @Column(name="ruta_imagen")
    private String rutaImagen;
    
    private boolean activo;
    

    public Categoria() {
    }

    public Categoria(String descripcion, String rutaImagen, boolean activo) {
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.activo = activo;
    }
     
    
}