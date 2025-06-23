package com.AgendaVet.AgendaVet.assemblers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.AgendaVet.AgendaVet.controller.V2.ReservaControllerV2;
import com.AgendaVet.AgendaVet.model.Reserva;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ReservaModelAssembler implements RepresentationModelAssembler<Reserva, EntityModel<Reserva>> {
    
    @Override
    public EntityModel<Reserva> toModel(Reserva reserva) {
        return EntityModel.of(reserva,
                linkTo(methodOn(ReservaControllerV2.class).getReservaById(reserva.getId())).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withRel("reservas"),
                linkTo(methodOn(ReservaControllerV2.class).updateReserva(reserva.getId(), reserva)).withRel("actualizar"),
                linkTo(methodOn(ReservaControllerV2.class).deleteReserva(reserva.getId())).withRel("eliminar"),
                linkTo(methodOn(ReservaControllerV2.class).updateEstadoReserva(reserva.getId(), "")).withRel("actualizar-estado")
        );
    }
}