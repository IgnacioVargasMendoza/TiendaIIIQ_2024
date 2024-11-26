    package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.service.CategoriaService;
import com.tienda.service.impl.FirebaseStorageServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;

    /*
        * Maneja la acción de listar la información de categoría. Para acceder al 
        * método "public String inicio", se debe utilizar la ruta "/categoria/listado".
     */
    @GetMapping("/listado")
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

    @PostMapping("/guardar")
    public String categoriaGuardar(Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()) {
            categoriaService.save(categoria);
            categoria.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile,
                            "categoria",
                            categoria.getIdCategoria()));
        }
        categoriaService.save(categoria);
        
        /*
            redirect se utiliza para indicarle al codigo que se devuelva a un recurso y no a una vista, es decir,
            a un metodo dentro de la clase. --Preguntarle a profersor si se puede devolver a un recurso de otra clase.
        */
        return "redirect:/categoria/listado";
    }

    //Al pasarle el parametro entre corchetes, le estoy indicando al codigo que despues de eliminar, le voy a enviar el parametro idCategoria
    @GetMapping("/eliminar/{idCategoria}")
    public String categoriaEliminar(Categoria categoria) {
        categoriaService.delete(categoria);
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")
    public String categoriaModificar(Categoria categoria, Model model) {
        
        //Se obtienen los datos de la categoria 
        categoria = categoriaService.getCategoria(categoria);
        
        //Se agregan los datos de categoria al modelo
        model.addAttribute("categoria", categoria);
        
        //Se devuelve la vista modifica dentro del la carpeta de categoria
        return "/categoria/modifica";
    }

}
