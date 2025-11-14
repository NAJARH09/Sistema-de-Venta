package com.crud.com.Producto.Controller;

import com.crud.com.Producto.model.Cliente;
import com.crud.com.Producto.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("ventas/cliente")
// 🔹 Esta anotación define la ruta base: todas las URLs de este controlador
// comenzarán con "ventas/cliente". Ejemplo: /ventas/cliente/nuevo
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepo;
    // 🔹 @Autowired le dice a Spring Boot que inyecte automáticamente
    // una instancia de ClienteRepository para usar sus métodos (findAll, save, etc.)
    // 💡 Mejora: el nombre de variable debe empezar en minúscula (clienteRepo), no ClienteRepo.

    // 🔹 LISTAR CLIENTES
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteRepo.findAll());
        // 💡 Mejora: usa "clientes" en plural porque representa una lista
        // model.addAttribute() permite enviar datos del backend a la vista (HTML)
        return "ventas/cliente/lista"; // 🌸 Se dirige a templates/ventas/cliente/lista.html
    }

    // 🔹 FORMULARIO NUEVO CLIENTE
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        // Aquí se crea un objeto vacío para llenar en el formulario
        return "ventas/cliente/form"; // 🌸 Se dirige a templates/ventas/cliente/form.html
    }

    // 🔹 GUARDAR CLIENTE
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        clienteRepo.save(cliente);
        // @ModelAttribute conecta los campos del formulario con el objeto Cliente
        // save() guarda o actualiza según si el id ya existe
        return "redirect:/ventas/cliente";
        // ✅ Redirige a la lista después de guardar
    }

    // 🔹 EDITAR CLIENTE
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        Cliente cliente = clienteRepo.findById(id).orElse(null);
        // orElse(null) evita errores si no se encuentra el cliente
        model.addAttribute("cliente", cliente);
        return "ventas/cliente/form"; // Usa el mismo formulario para editar
    }

    // 🔹 ELIMINAR CLIENTE
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        clienteRepo.deleteById(id);
        return "redirect:/ventas/cliente"; // ⚠️ Corregido: antes tenías "ventas/clientes"
    }

}
