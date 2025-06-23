package com.AgendaVet.AgendaVet.controller;

import com.AgendaVet.AgendaVet.assemblers.MascotaModelAssembler;
import com.AgendaVet.AgendaVet.model.Mascota;
import com.AgendaVet.AgendaVet.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/carreras")
@Tag(name = "Mascotas V2 (HATEOAS)", description = "API REST con HATEOAS para gestión avanzada de mascotas")
public class MascotaControllerV2 {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private MascotaModelAssembler assembler;

    @Operation(
        summary = "Obtener todas las mascotas (HATEOAS)",
        description = "Retorna una colección de mascotas con enlaces HATEOAS para navegación hipermedia. " +
                     "Incluye enlaces de auto-referencia y navegación a recursos relacionados."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de mascotas obtenida exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(description = "Colección HAL de mascotas con enlaces hipermedia")
            )
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "No hay mascotas registradas en el sistema"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
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

    @Operation(
        summary = "Obtener mascota por ID (HATEOAS)",
        description = "Retorna una mascota específica con enlaces HATEOAS para acceder a recursos relacionados como propietario y reservas."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Mascota encontrada exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Mascota.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Mascota no encontrada"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "ID de mascota inválido"
        )
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mascota>> getMascotaByCodigo(
            @Parameter(description = "ID único de la mascota", required = true, example = "1")
            @PathVariable Long id) {
        Mascota mascota = mascotaService.findById(id);
        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(mascota));
    }

    @Operation(
        summary = "Crear nueva mascota (HATEOAS)",
        description = "Crea una nueva mascota y retorna el recurso creado con enlaces HATEOAS. " +
                     "La respuesta incluye la URI del recurso creado en el header Location."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Mascota creada exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Mascota.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de mascota inválidos"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario propietario no encontrado"
        )
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mascota>> createMascota(
            @Parameter(description = "Datos de la nueva mascota", required = true)
            @RequestBody Mascota mascota) {
        Mascota newMascota = mascotaService.save(mascota);
        return ResponseEntity
                .created(linkTo(methodOn(MascotaControllerV2.class).getMascotaByCodigo(newMascota.getId())).toUri())
                .body(assembler.toModel(newMascota));
    }

    @Operation(
        summary = "Actualizar mascota completa (HATEOAS)",
        description = "Actualiza todos los campos de una mascota existente (reemplazo completo) y retorna el recurso con enlaces HATEOAS."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Mascota actualizada exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Mascota.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Mascota no encontrada"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de mascota inválidos"
        )
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mascota>> updateMascota(
            @Parameter(description = "ID de la mascota a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos completos de la mascota", required = true)
            @RequestBody Mascota mascota) {
        mascota.setId(id);
        Mascota updatedMascota = mascotaService.save(mascota);
        return ResponseEntity.ok(assembler.toModel(updatedMascota));
    }

    @Operation(
        summary = "Actualizar mascota parcial (HATEOAS)",
        description = "Actualiza parcialmente los campos de una mascota existente (solo los campos proporcionados) y retorna el recurso con enlaces HATEOAS."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Mascota actualizada parcialmente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Mascota.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Mascota no encontrada"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de mascota inválidos"
        )
    })
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Mascota>> patchMascota(
            @Parameter(description = "ID de la mascota a actualizar parcialmente", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Campos de la mascota a actualizar (solo los campos proporcionados serán modificados)", required = true)
            @RequestBody Mascota mascota) {
        Mascota updatedMascota = mascotaService.patchMascota(id, mascota);
        if (updatedMascota == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedMascota));
    }

    @Operation(
        summary = "Eliminar mascota (HATEOAS)",
        description = "Elimina una mascota del sistema de forma permanente. Esta operación no se puede deshacer y puede afectar reservas asociadas."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Mascota eliminada exitosamente"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Mascota no encontrada"
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto - mascota tiene dependencias que impiden la eliminación"
        )
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteMascota(
            @Parameter(description = "ID de la mascota a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        Mascota mascota = mascotaService.findById(id);
        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }
        mascotaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener mascotas por usuario (HATEOAS)",
        description = "Retorna todas las mascotas asociadas a un usuario específico con enlaces HATEOAS para navegación hipermedia."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de mascotas del usuario obtenida exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(description = "Colección HAL de mascotas con enlaces hipermedia")
            )
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "El usuario no tiene mascotas registradas"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado"
        )
    })
    @GetMapping(value = "/usuario/{usuarioId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Mascota>>> getMascotasPorUsuario(
            @Parameter(description = "ID del usuario propietario de las mascotas", required = true, example = "1")
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