/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.dao;

import com.tienda.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Ignac
 */

//Debe extender JpaRepository y dentro debemos de especificar el dominio y el tipo de variable del ID del dominio
public interface ProductoDao extends JpaRepository<Producto, Long>{
    
}