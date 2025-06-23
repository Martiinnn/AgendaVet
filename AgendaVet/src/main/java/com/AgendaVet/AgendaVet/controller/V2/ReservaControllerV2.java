package com.AgendaVet.AgendaVet.controller.V2;

import com.AgendaVet.AgendaVet.assemblers.ReservaModelAssembler;
import com.AgendaVet.AgendaVet.model.Reserva;
import com.AgendaVet.AgendaVet.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/reservas")
@Tag(name = "Reservas V2", description = "API V2 para gestión de reservas con HATEOAS")
public class ReservaControllerV2 {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Obtener todas las reservas")
    @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente")
    public CollectionModel<EntityModel<Reserva>> getAllReservas() {
        List<EntityModel<Reserva>> reservas = reservaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reserva por ID")
    @ApiResponse(responseCode = "200", description = "Reserva encontrada")
    public ResponseEntity<EntityModel<Reserva>> getReservaById(
            @Parameter(description = "ID de la reserva") @PathVariable Long id) {
        Reserva reserva = reservaService.findById(id);
        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(reserva));
    }

    @PostMapping
    @Operation(summary = "Crear nueva reserva")
    @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente")
    public ResponseEntity<EntityModel<Reserva>> createReserva(@RequestBody Reserva reserva) {
        Reserva savedReserva = reservaService.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedReserva));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva")
    @ApiResponse(responseCode = "200", description = "Reserva actualizada exitosamente")
    public ResponseEntity<EntityModel<Reserva>> updateReserva(
            @Parameter(description = "ID de la reserva") @PathVariable Long id,
            @RequestBody Reserva reserva) {
        Reserva existingReserva = reservaService.findById(id);
        if (existingReserva == null) {
            return ResponseEntity.notFound().build();
        }
        reserva.setId(id);
        Reserva updatedReserva = reservaService.save(reserva);
        return ResponseEntity.ok(assembler.toModel(updatedReserva));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva")
    @ApiResponse(responseCode = "204", description = "Reserva eliminada exitosamente")
    public ResponseEntity<Void> deleteReserva(
            @Parameter(description = "ID de la reserva") @PathVariable Long id) {
        Reserva existingReserva = reservaService.findById(id);
        if (existingReserva == null) {
            return ResponseEntity.notFound().build();
        }
        reservaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener reservas por usuario")
    @ApiResponse(responseCode = "200", description = "Lista de reservas del usuario obtenida exitosamente")
    public CollectionModel<EntityModel<Reserva>> getReservasByUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        List<EntityModel<Reserva>> reservas = reservaService.findByUsuarioId(usuarioId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasByUsuario(usuarioId)).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withRel("reservas"));
    }

    @GetMapping("/veterinaria/{veterinariaId}")
    @Operation(summary = "Obtener reservas por veterinaria")
    @ApiResponse(responseCode = "200", description = "Lista de reservas de la veterinaria obtenida exitosamente")
    public CollectionModel<EntityModel<Reserva>> getReservasByVeterinaria(
            @Parameter(description = "ID de la veterinaria") @PathVariable Long veterinariaId) {
        List<EntityModel<Reserva>> reservas = reservaService.findByVeterinariaId(veterinariaId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservasByVeterinaria(veterinariaId)).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).getAllReservas()).withRel("reservas"));
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado de reserva")
    @ApiResponse(responseCode = "200", description = "Estado de reserva actualizado exitosamente")
    public ResponseEntity<EntityModel<Reserva>> updateEstadoReserva(
            @Parameter(description = "ID de la reserva") @PathVariable Long id,
            @RequestBody String estado) {
        Reserva updatedReserva = reservaService.updateEstado(id, estado);
        return ResponseEntity.ok(assembler.toModel(updatedReserva));
    }
}