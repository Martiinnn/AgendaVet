package com.AgendaVet.AgendaVet.controller.V2;


import com.AgendaVet.AgendaVet.assemblers.NotificacionModelAssembler;
import com.AgendaVet.AgendaVet.model.Notificacion;
import com.AgendaVet.AgendaVet.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/notificaciones")
@Tag(name = "Notificaciones V2", description = "API V2 para gestión de notificaciones con HATEOAS")
public class NotificacionControllerV2 {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Obtener todas las notificaciones")
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida exitosamente")
    public CollectionModel<EntityModel<Notificacion>> getAllNotificaciones() {
        List<EntityModel<Notificacion>> notificaciones = notificacionService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(notificaciones,
                linkTo(methodOn(NotificacionControllerV2.class).getAllNotificaciones()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por ID")
    @ApiResponse(responseCode = "200", description = "Notificación encontrada")
    public ResponseEntity<EntityModel<Notificacion>> getNotificacionById(
            @Parameter(description = "ID de la notificación") @PathVariable Long id) {
        Notificacion notificacion = notificacionService.findById(id);
        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(notificacion));
    }

    @PostMapping
    @Operation(summary = "Crear nueva notificación")
    @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente")
    public ResponseEntity<EntityModel<Notificacion>> createNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion savedNotificacion = notificacionService.save(notificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedNotificacion));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar notificación")
    @ApiResponse(responseCode = "200", description = "Notificación actualizada exitosamente")
    public ResponseEntity<EntityModel<Notificacion>> updateNotificacion(
            @Parameter(description = "ID de la notificación") @PathVariable Long id,
            @RequestBody Notificacion notificacion) {
        Notificacion existingNotificacion = notificacionService.findById(id);
        if (existingNotificacion == null) {
            return ResponseEntity.notFound().build();
        }
        notificacion.setId(id);
        Notificacion updatedNotificacion = notificacionService.save(notificacion);
        return ResponseEntity.ok(assembler.toModel(updatedNotificacion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación")
    @ApiResponse(responseCode = "204", description = "Notificación eliminada exitosamente")
    public ResponseEntity<Void> deleteNotificacion(
            @Parameter(description = "ID de la notificación") @PathVariable Long id) {
        Notificacion existingNotificacion = notificacionService.findById(id);
        if (existingNotificacion == null) {
            return ResponseEntity.notFound().build();
        }
        notificacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}