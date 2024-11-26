package com.tienda.controller;

import com.tienda.domain.Categoria;
import com.tienda.domain.Producto;
import com.tienda.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tienda.service.ProductoService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pruebas")
public class PruebasController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    /**
     * Nota: El objeto `Model` actúa como un contenedor que permite almacenar
     * información que será enviada a la vista. Puede contener atributos de
     * distintas clases, los cuales estarán disponibles para ser accedidos y
     * mostrados en la plantilla correspondiente.
     */
    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    @GetMapping("/listado/{idCategoria}")
    public String listado(Model model, Categoria categoria) {
        var productos = categoriaService.getCategoria(categoria).getProductos();
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("categorias", categorias);
        return "/pruebas/listado";
    }

    //Los métodos siguientes son para la prueba de consultas ampliadas
    @GetMapping("/consulta")
    public String listado2(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        return "/pruebas/listado2";
    }

    /**
     * La anotación `@RequestParam(value = "precioInf")` se utiliza para extraer
     * el valor del parámetro de consulta `precioInf` de la URL de la petición
     * HTTP. Ademas, esta anotación es util para evitar tener que crear un nuevo
     * modelo con la informacion de precios. En este caso, está asociada al
     * método anotado con `@PostMapping("/query1")`, permitiendo que el
     * parámetro recibido en la consulta se pase como argumento al método.
     *
     * Nota: `@RequestParam` no se utiliza para extraer datos del cuerpo de una
     * petición POST; para ese propósito, se puede emplear `@RequestBody` o
     * anotaciones específicas para formularios.
     *
     * Adicionalmente, en este método se utiliza el objeto `Model` para enviar
     * datos a la vista asociada. Esto permite que el usuario visualice y
     * recuerde los valores ingresados al realizar la consulta.
     */
    @PostMapping("/query1")
    public String consultaQuery1(@RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup, Model model) {
        List<Producto> productos = productoService.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

    @PostMapping("/query2")
    public String consultaQuery2(@RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup, Model model) {
        var productos = productoService.metodoJPQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

    @PostMapping("/query3")
    public String consultaQuery3(@RequestParam(value = "precioInf") double precioInf,
            @RequestParam(value = "precioSup") double precioSup, Model model) {
        var productos = productoService.metodoNativo(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("totalProductos", productos.size());
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/pruebas/listado2";
    }

}
