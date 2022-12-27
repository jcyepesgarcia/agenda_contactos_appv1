package com.aplicacionesjc.agenda_contactos_appv1.controller;

import com.aplicacionesjc.agenda_contactos_appv1.entity.Contacto;
import com.aplicacionesjc.agenda_contactos_appv1.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ContactoController {
    @Autowired
    private ContactoRepository contactoRepository;

    @GetMapping({"/", ""})
    public String mostrarPaginaDeInicio(Model modelo){
        List<Contacto> contactoList = contactoRepository.findAll();
        modelo.addAttribute("contactoList", contactoList);
        return "index";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistro(Model model){
        model.addAttribute("contacto", new Contacto());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    public String guardarContacto(@Validated Contacto contacto, BindingResult bindingResult, RedirectAttributes redirect, Model modelo){
        if(bindingResult.hasErrors()){
            modelo.addAttribute("contacto", contacto);
            return "nuevo";
        }
        contactoRepository.save(contacto);
        redirect.addFlashAttribute("msgExito", "El contacto ha sido agregado con éxito.");
        return "redirect:/";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditarContacto(@PathVariable Integer id, Model modelo){
        Contacto contacto = contactoRepository.getById(id);
        modelo.addAttribute("contacto", contacto);
        return "nuevo";
    }

    @PostMapping("/{id}/editar")
    public String actualizarContacto(@PathVariable Integer id, @Validated Contacto contacto, BindingResult bindingResult, RedirectAttributes redirect, Model modelo){
        Contacto contactoBuscado = contactoRepository.getById(id);
        if(bindingResult.hasErrors()){
            modelo.addAttribute("contacto", contacto);
            return "nuevo";
        }
        contactoBuscado.setNombre(contacto.getNombre());
        contactoBuscado.setCelular(contacto.getCelular());
        contactoBuscado.setEmail(contacto.getEmail());
        contactoBuscado.setFechaNacimiento(contacto.getFechaNacimiento());
        contactoRepository.save(contactoBuscado);
        redirect.addFlashAttribute("msgExito", "El contacto ha sido actualizado correctamente.");
        return "redirect:/";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarContacto(@PathVariable Integer id, RedirectAttributes redirect){
        Contacto contacto = contactoRepository.getById(id);
        contactoRepository.delete(contacto);
        redirect.addFlashAttribute("msgExito", "El contacto ha sido eliminado correctamente.");
        return "redirect:/";
    }

}
