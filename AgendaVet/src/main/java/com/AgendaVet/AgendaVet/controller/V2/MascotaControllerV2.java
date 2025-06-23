package com.AgendaVet.AgendaVet.controller.V2;

import com.AgendaVet.AgendaVet.assemblers.MascotaModelAssembler;
import com.AgendaVet.AgendaVet.model.Mascota;
import com.AgendaVet.AgendaVet.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/mascotas")
@Tag(name = "Mascotas V2", description = "Gestión de mascotas con HATEOAS")
public class MascotaControllerV2 {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private MascotaModelAssembler assembler;

    @Operation(summary = "Obtener todas las mascotas")
    @ApiResponse(responseCode = "200", description = "Lista de mascotas")
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Mascota>>> getAllmascotas() {
        List<EntityModel<Mascota>> mascotas = mascotaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                mascotas,
                linkTo(methodOn(MascotaControllerV2.class).getAllmascotas()).withSelfRel()
        ));
    }

    @Operation(summary = "Obtener mascota por ID")
    @ApiResponse(responseCode = "200", description = "Mascota encontrada")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mascota>> getMascotaByCodigo(
            @Parameter(description = "ID de la mascota")
            @PathVariable Long id) {
        Mascota mascota = mascotaService.findById(id);
        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(mascota));
    }

    @Operation(summary = "Crear nueva mascota")
    @ApiResponse(responseCode = "201", description = "Mascota creada")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mascota>> createMascota(
            @Parameter(description = "Datos de la mascota")
            @RequestBody Mascota mascota) {
        Mascota newMascota = mascotaService.save(mascota);
        return ResponseEntity
                .created(linkTo(methodOn(MascotaControllerV2.class).getMascotaByCodigo(newMascota.getId())).toUri())
                .body(assembler.toModel(newMascota));
    }

    @Operation(summary = "Actualizar mascota")
    @ApiResponse(responseCode = "200", description = "Mascota actualizada")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mascota>> updateMascota(
            @Parameter(description = "ID de la mascota")
            @PathVariable Long id,
            @Parameter(description = "Datos de la mascota")
            @RequestBody Mascota mascota) {
        mascota.setId(id);
        Mascota updatedMascota = mascotaService.save(mascota);
        return ResponseEntity.ok(assembler.toModel(updatedMascota));
    }

    @Operation(summary = "Actualizar mascota parcialmente")
    @ApiResponse(responseCode = "200", description = "Mascota actualizada")
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mascota>> patchMascota(
            @Parameter(description = "ID de la mascota")
            @PathVariable Long id,
            @Parameter(description = "Campos a actualizar")
            @RequestBody Mascota mascota) {
        Mascota updatedMascota = mascotaService.patchMascota(id, mascota);
        if (updatedMascota == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedMascota));
    }

    @Operation(summary = "Eliminar mascota")
    @ApiResponse(responseCode = "204", description = "Mascota eliminada")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMascota(
            @Parameter(description = "ID de la mascota")
            @PathVariable Long id) {
        Mascota mascota = mascotaService.findById(id);
        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }
        mascotaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener mascotas por usuario")
    @ApiResponse(responseCode = "200", description = "Lista de mascotas del usuario")
    @GetMapping(value = "/usuario/{usuarioId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Mascota>>> getMascotasPorUsuario(
            @Parameter(description = "ID del usuario")
            @PathVariable Long usuarioId) {
        List<Mascota> mascotas = mascotaService.findByUsuarioId(usuarioId);

        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Mascota>> recursos = mascotas.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                recursos,
                linkTo(methodOn(MascotaControllerV2.class).getMascotasPorUsuario(usuarioId)).withSelfRel()
        ));
    }
}