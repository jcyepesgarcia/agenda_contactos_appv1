package com.aplicacionesjc.agenda_contactos_appv1.repository;

import com.aplicacionesjc.agenda_contactos_appv1.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {
}
