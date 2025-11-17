package com.crud.com.Producto.Controller;

import com.crud.com.Producto.model.Campana;
import com.crud.com.Producto.model.Cliente;
import com.crud.com.Producto.repository.CampanaRepository;
import com.crud.com.Producto.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/ventas/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private CampanaRepository campanaRepo;

    // LISTAR CLIENTES POR CAMPAÑA
    @GetMapping("/lista/{idCampana}")
    public String listar(@PathVariable int idCampana, Model model) {

        Campana campana = campanaRepo.findById(idCampana)
                .orElseThrow(() -> new RuntimeException("Campaña no encontrada"));

        model.addAttribute("campana", campana);
        model.addAttribute("clientes", campana.getClientes());
        model.addAttribute("totalClientes", campana.getClientes().size());

        return "ventas/cliente/lista";
    }

    // NUEVO CLIENTE
    @GetMapping("/nuevo/{idCampana}")
    public String nuevo(@PathVariable int idCampana, Model model) {

        Campana campana = campanaRepo.findById(idCampana)
                .orElseThrow(() -> new RuntimeException("Campaña no encontrada"));

        Cliente cliente = new Cliente();
        cliente.setCampana(campana); // ASOCIAR

        model.addAttribute("cliente", cliente); // MUY IMPORTANTE
        model.addAttribute("campana", campana);
        return "ventas/cliente/form"; // DEVOLVEMOS FORMULARIO
    }


    // EDITAR CLIENTE
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {

        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        model.addAttribute("cliente", cliente);
        model.addAttribute("campana", cliente.getCampana());
        return "ventas/cliente/form";
    }

    // GUARDAR CLIENTE
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute @Valid Cliente cliente,
            BindingResult result,
            Model model) {

        int idCampana = cliente.getCampana().getId();

        // Validación de duplicados
        if (cliente.getId() == 0) { // NUEVO
            if (clienteRepo.existsByNombreAndCampanaId(cliente.getNombre(), idCampana)) {
                result.rejectValue("nombre", "error.cliente", "El nombre ya existe en esta campaña");
            }
            if (clienteRepo.existsByTelefonoAndCampanaId(cliente.getTelefono(), idCampana)) {
                result.rejectValue("telefono", "error.cliente", "El teléfono ya existe en esta campaña");
            }
        } else { // EDICIÓN
            if (clienteRepo.existsByNombreAndCampanaIdAndIdNot(cliente.getNombre(), idCampana, cliente.getId())) {
                result.rejectValue("nombre", "error.cliente", "El nombre ya existe en esta campaña");
            }
            if (clienteRepo.existsByTelefonoAndCampanaIdAndIdNot(cliente.getTelefono(), idCampana, cliente.getId())) {
                result.rejectValue("telefono", "error.cliente", "El teléfono ya existe en esta campaña");
            }
        }

        // SI HAY ERRORES, volvemos al FORMULARIO
        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente); // obligatorio
            model.addAttribute("campana", cliente.getCampana());
            return "ventas/cliente/form"; // NO REDIRECT
        }

        // GUARDAR CLIENTE
        clienteRepo.save(cliente);

        // DESPUÉS DEL GUARDADO, sí podemos redirigir a la lista
        return "redirect:/ventas/cliente/lista/" + idCampana;
    }


    // ELIMINAR CLIENTE
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {

        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        int idCampana = cliente.getCampana().getId();

        clienteRepo.delete(cliente);

        return "redirect:/ventas/cliente/lista/" + idCampana;
    }
}
