package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ignac
 */
@Controller
@RequestMapping("/categoria") //Todas las acciones que contenga la clase CategoriaController responden con el prefijo "/categoria"
public class CategoriaController {

    /*
        * Al igual que realizamos la inyección de dependencias en la clase CategoriaServiceImpl, 
        * también es necesario hacerlo en el controlador.

        * Se recomienda declarar una variable de tipo CategoriaService(Interface) en lugar de CategoriaServiceImpl(Clase)
        * para reducir el acoplamiento y evitar modificaciones adicionales en el código si la implementación 
        * de CategoriaService cambia en el futuro, como podría suceder al cambiar el proveedor de base de datos.
     */
    @Autowired
    CategoriaService categoriaService;

    /*
        * Maneja la acción de listar la información de categoría. Para acceder al 
        * método "public String inicio", se debe utilizar la ruta "/categoria/listado".
     */
    @RequestMapping("/listado")
    public String inicio(Model model) {//Dentro de la variable Model se almacena los datos que se mostraran en la vista de la aplicacion

        //Se obtiene la lista de categorias disponibles en la base de datos
        List<Categoria> lista = categoriaService.getCategorias(false);
        
        /*
         * Utilizando el método .addAttribute de la clase Model, primero se asigna el nombre de la clave 
         * ("categorias") y luego el valor (la lista de categorías).
         * Este nombre de clave será utilizado en la vista para acceder a los datos proporcionados.
         * Esta instrucción envía la lista de categorías a la vista correspondiente.
         */
        model.addAttribute("categorias", lista);
        
        
        /*
         * Esta instrucción envía el total de elementos en la lista de categorías 
         * a la vista correspondiente, permitiendo mostrar el número total de categorías.
         */
        model.addAttribute("totalCategorias", lista.size());
             
        // Se devuelve el nombre de la vista que gestionará y mostrará los valores asignados al modelo
        return "/categoria/listado"; //Se indica el folder donde se encuentra la vista. Tener cuidado porque el mapping no siempre coincide con la vista
    }

}
