package com.AgendaVet.AgendaVet.assemblers;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.AgendaVet.AgendaVet.controller.V2.VeterinariaControllerV2;
import com.AgendaVet.AgendaVet.model.Veterinaria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class VeterinariaModelAssembler implements RepresentationModelAssembler<Veterinaria, EntityModel<Veterinaria>> {
    
    @Override
    public EntityModel<Veterinaria> toModel(Veterinaria veterinaria) {
        return EntityModel.of(veterinaria,
                linkTo(methodOn(VeterinariaControllerV2.class).getVeterinariaById(veterinaria.getId())).withSelfRel(),
                linkTo(methodOn(VeterinariaControllerV2.class).getAllVeterinarias()).withRel("veterinarias"),
                linkTo(methodOn(VeterinariaControllerV2.class).updateVeterinaria(veterinaria.getId(), veterinaria)).withRel("actualizar"),
                linkTo(methodOn(VeterinariaControllerV2.class).deleteVeterinaria(veterinaria.getId())).withRel("eliminar"),
                linkTo(methodOn(VeterinariaControllerV2.class).getServiciosByVeterinaria(veterinaria.getId())).withRel("servicios")
        );
    }
}