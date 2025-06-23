package com.AgendaVet.AgendaVet.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.AgendaVet.AgendaVet.controller.V2.NotificacionControllerV2;
import com.AgendaVet.AgendaVet.model.Notificacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class NotificacionModelAssembler implements RepresentationModelAssembler<Notificacion, EntityModel<Notificacion>> {
    
    @Override
    public EntityModel<Notificacion> toModel(Notificacion notificacion) {
        return EntityModel.of(notificacion,
                linkTo(methodOn(NotificacionControllerV2.class).getNotificacionById(notificacion.getId())).withSelfRel(),
                linkTo(methodOn(NotificacionControllerV2.class).getAllNotificaciones()).withRel("notificaciones"),
                linkTo(methodOn(NotificacionControllerV2.class).updateNotificacion(notificacion.getId(), notificacion)).withRel("actualizar"),
                linkTo(methodOn(NotificacionControllerV2.class).deleteNotificacion(notificacion.getId())).withRel("eliminar")
        );
    }
}