package com.crud.com.Producto.Controller;

import com.crud.com.Producto.model.Campana;
import com.crud.com.Producto.model.Cliente;
import com.crud.com.Producto.repository.CampanaRepository;
import com.crud.com.Producto.service.CampanaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ventas/campanas")
public class CampanaController {

    @Autowired
    private CampanaRepository campanaRepository;


    // ------------------------------


        // LISTA
        @GetMapping("/lista")
        public String listarCampanas(Model model) {
            List<Campana> campanas = campanaRepository.findAll();
            model.addAttribute("campanas", campanas);
            return "ventas/campanas/lista";
        }

        // NUEVO
        @GetMapping("/nuevo")
        public String nuevo(Model model) {
            model.addAttribute("campana", new Campana());
            return "ventas/campanas/form";
        }

    @Autowired
    private CampanaService campanaService;

        // GUARDAR
        @PostMapping("/guardar")
        public String guardar(
                @Valid @ModelAttribute("campana") Campana campana,
                BindingResult result,
                Model model) {

            if (result.hasErrors()) {
                return "ventas/campanas/form";
            }

            try {
                campanaService.guardar(campana);
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", e.getMessage());
                return "ventas/campanas/form";
            }

            return "redirect:/ventas/campanas/lista";
        }


    // EDITAR
        @GetMapping("/editar/{id}")
        public String editar(@PathVariable int id, Model model) {
            Campana campana = campanaRepository.findById(id).orElse(null);
            model.addAttribute("campana", campana);
            return "ventas/campanas/form";
        }

        // ELIMINAR
        @GetMapping("/eliminar/{id}")
        public String eliminar(@PathVariable Integer id, RedirectAttributes flash) {
            try {
                campanaService.eliminar(id);
                flash.addFlashAttribute("mensaje", "Campaña eliminada correctamente");
            } catch (Exception e) {
                flash.addFlashAttribute("error", e.getMessage());
            }
            return "redirect:/ventas/campanas/lista";
        }

    @GetMapping("/{id}/clientes")
    public String verClientes(@PathVariable int id, Model model) {

        Campana campana = campanaRepository.findById(id).orElse(null);

        model.addAttribute("campana", campana);
        model.addAttribute("clientes", campana.getClientes());

        return "ventas/campanas/clientes";
    }

}
