/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tienda.service;

import com.tienda.domain.Categoria;
import java.util.List;

//Interfaz que define las acciones o métodos disponibles en el servicio de Categoría dentro de la aplicación.
public interface CategoriaService {
    
    public List<Categoria> getCategorias(boolean activos);
}
