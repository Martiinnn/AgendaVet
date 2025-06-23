package com.AgendaVet.AgendaVet.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.AgendaVet.AgendaVet.controller.V2.MascotaControllerV2;
import com.AgendaVet.AgendaVet.model.Mascota;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MascotaModelAssembler implements RepresentationModelAssembler<Mascota, EntityModel<Mascota>> {
    
    @SuppressWarnings("null")
    @Override
    public EntityModel<Mascota> toModel(Mascota mascota) {
        return EntityModel.of(mascota,
                linkTo(methodOn(MascotaControllerV2.class).getMascotaByCodigo(mascota.getId())).withSelfRel(),
                linkTo(methodOn(MascotaControllerV2.class).getAllmascotas()).withRel("mascotas"),
                linkTo(methodOn(MascotaControllerV2.class).updateMascota(mascota.getId(), null)).withRel("actualizar"),
                linkTo(methodOn(MascotaControllerV2.class).deleteMascota(mascota.getId())).withRel("eliminar"),
                linkTo(methodOn(MascotaControllerV2.class).patchMascota(mascota.getId(), null)).withRel("actualizar-parcial")
        );
    }
}
