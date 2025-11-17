package com.crud.com.Producto.Controller;

import com.crud.com.Producto.model.Cliente;
import com.crud.com.Producto.model.Producto;
import com.crud.com.Producto.repository.ClienteRepository;
import com.crud.com.Producto.repository.ProductoRepository;
import com.crud.com.Producto.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ventas/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteRepository clienteRepo;

    // ==============================
    // 1. LISTAR PRODUCTOS DE UN CLIENTE
    // ==============================
    @GetMapping("/cliente/{idCliente}")//para diferencia al cliente
    public String listarProductosCliente(@PathVariable Integer idCliente, Model model,
                                         @ModelAttribute("msg") String msg) {

        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + idCliente));

        Producto nuevoProducto = new Producto();
        nuevoProducto.setCliente(cliente);

        model.addAttribute("title", "Productos de " + cliente.getNombre());
        model.addAttribute("cliente", cliente);
        model.addAttribute("productos", cliente.getProductos());
        model.addAttribute("producto", nuevoProducto);
        model.addAttribute("msg", msg);
        model.addAttribute("campanaId", cliente.getCampana().getId());

        return "ventas/productos/lista";
    }

    // ==============================
    // 2. FORMULARIO EDITAR
    // ==============================
    @GetMapping("/editar/{idProducto}")
    public String editarProducto(@PathVariable Integer idProducto, Model model) {

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + idProducto));

        Cliente cliente = producto.getCliente();

        model.addAttribute("title", "Editar Producto");
        model.addAttribute("cliente", cliente);
        model.addAttribute("productos", cliente.getProductos());
        model.addAttribute("producto", producto);

        return "ventas/productos/lista";
    }

    // ==============================
    // 3. GUARDAR NUEVO PRODUCTO
    // ==============================
    @PostMapping("/guardar")
    public String guardarProducto(
            @Valid @ModelAttribute Producto producto,
            BindingResult result,
            RedirectAttributes flash) {

        if (result.hasErrors()) {
            flash.addFlashAttribute("msg", "⚠️ Error: Revisar los campos.");
            return "redirect:/ventas/productos/cliente/" + producto.getCliente().getId();
        }

        double importe = productoService.calcularImporte(producto.getPrecio(), producto.getCantidad());
        producto.setImporte(importe);

        productoRepository.save(producto);

        flash.addFlashAttribute("msg", "✔️ Producto agregado correctamente.");

        return "redirect:/ventas/productos/cliente/" + producto.getCliente().getId();
    }

    // ==============================
    // 4. ACTUALIZAR
    // ==============================
    @PostMapping("/actualizar/{idProducto}")
    public String actualizarProducto(
            @PathVariable Integer idProducto,
            @Valid @ModelAttribute Producto productoForm,
            BindingResult result,
            RedirectAttributes flash) {

        Producto productoBD = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (result.hasErrors()) {
            flash.addFlashAttribute("msg", "⚠️ Error al actualizar. Verifique los campos.");
            return "redirect:/ventas/productos/cliente/" + productoBD.getCliente().getId();
        }

        productoBD.setNombre(productoForm.getNombre());
        productoBD.setDes(productoForm.getDes());
        productoBD.setCantidad(productoForm.getCantidad());
        productoBD.setPrecio(productoForm.getPrecio());

        double importe = productoService.calcularImporte(productoForm.getPrecio(), productoForm.getCantidad());
        productoBD.setImporte(importe);

        productoRepository.save(productoBD);

        flash.addFlashAttribute("msg", "✔️ Producto actualizado correctamente.");

        return "redirect:/ventas/productos/cliente/" + productoBD.getCliente().getId();
    }

    // ==============================
    // 5. ELIMINAR
    // ==============================
    @GetMapping("/eliminar/{idProducto}")
    public String eliminarProducto(@PathVariable Integer idProducto, RedirectAttributes flash) {

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + idProducto));

        int idCliente = producto.getCliente().getId();

        productoRepository.delete(producto);

        flash.addFlashAttribute("msg", "🗑️ Producto eliminado correctamente.");

        return "redirect:/ventas/productos/cliente/" + idCliente;
    }
}
