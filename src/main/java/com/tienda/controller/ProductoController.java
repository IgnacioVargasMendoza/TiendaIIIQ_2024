package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.domain.Producto;
import com.tienda.service.CategoriaService;
import com.tienda.service.ProductoService;
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
@RequestMapping("/producto") //Todas las acciones que contenga la clase ProductoController responden con el prefijo "/producto"
public class ProductoController {

    /*
        * Al igual que realizamos la inyección de dependencias en la clase ProductoServiceImpl, 
        * también es necesario hacerlo en el controlador.

        * Se recomienda declarar una variable de tipo ProductoService(Interface) en lugar de ProductoServiceImpl(Clase)
        * para reducir el acoplamiento y evitar modificaciones adicionales en el código si la implementación 
        * de ProductoService cambia en el futuro, como podría suceder al cambiar el proveedor de base de datos.
     */
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FirebaseStorageServiceImpl firebaseStorageService;

    /*
        * Maneja la acción de listar la información de categoría. Para acceder al 
        * método "public String inicio", se debe utilizar la ruta "/producto/listado".
     */
    @GetMapping("/listado")
    public String inicio(Model model) {//Dentro de la variable Model se almacena los datos que se mostraran en la vista de la aplicacion

        //Se obtiene la lista de productos disponibles en la base de datos
        List<Producto> productos = productoService.getProductos(true);
        
        List<Categoria> categorias = categoriaService.getCategorias(true);

        /*
         * Utilizando el método .addAttribute de la clase Model, primero se asigna el nombre de la clave 
         * ("productos") y luego el valor (la lista de productos).
         * Este nombre de clave será utilizado en la vista para acceder a los datos proporcionados.
         * Esta instrucción envía la lista de productos a la vista correspondiente.
         */
        model.addAttribute("productos", productos);

        /*
         * Esta instrucción envía el total de elementos en la lista de productos 
         * a la vista correspondiente, permitiendo mostrar el número total de productos.
         */
        model.addAttribute("totalProductos", productos.size());
        
        model.addAttribute("categorias", categorias);

        // Se devuelve el nombre de la vista que gestionará y mostrará los valores asignados al modelo
        return "/producto/listado"; //Se indica el folder donde se encuentra la vista. Tener cuidado porque el mapping no siempre coincide con la vista
    }

    @PostMapping("/guardar")
    public String productoGuardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile) {
        if (!imagenFile.isEmpty()) {
            productoService.save(producto);
            producto.setRutaImagen(
                    firebaseStorageService.cargaImagen(
                            imagenFile,
                            "producto",
                            producto.getIdProducto()));
        }
        productoService.save(producto);
        
        /*
            redirect se utiliza para indicarle al codigo que se devuelva a un recurso y no a una vista, es decir,
            a un metodo dentro de la clase. --Preguntarle a profersor si se puede devolver a un recurso de otra clase.
        */
        return "redirect:/producto/listado";
    }

    //Al pasarle el parametro entre corchetes, le estoy indicando al codigo que despues de eliminar, le voy a enviar el parametro idProducto
    @GetMapping("/eliminar/{idProducto}")
    public String productoEliminar(Producto producto) {
        productoService.delete(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/modificar/{idProducto}")
    public String productoModificar(Producto producto, Model model) {
        
        //Se obtienen los datos de la producto 
        producto = productoService.getProducto(producto);
        List<Categoria> categorias = categoriaService.getCategorias(true);
        
        
        //Se agregan los datos de producto al modelo
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);

        
        //Se devuelve la vista modifica dentro del la carpeta de producto
        return "/producto/modifica";
    }

}
