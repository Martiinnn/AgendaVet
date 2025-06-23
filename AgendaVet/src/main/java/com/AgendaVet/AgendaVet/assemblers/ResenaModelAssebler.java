package com.AgendaVet.AgendaVet.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.AgendaVet.AgendaVet.controller.ResenaControllerV2;
import com.AgendaVet.AgendaVet.model.Resena;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ResenaModelAssebler implements RepresentationModelAssembler<Resena, EntityModel<Resena>> {
    
    @SuppressWarnings("null")
    @Override
    public EntityModel<Resena> toModel(Resena resena) {
        return EntityModel.of(resena,
                linkTo(methodOn(ResenaControllerV2.class).getResenaById(resena.getId())).withSelfRel(),
                linkTo(methodOn(ResenaControllerV2.class).getAllResenas()).withRel("resenas")
        );
    }
}
