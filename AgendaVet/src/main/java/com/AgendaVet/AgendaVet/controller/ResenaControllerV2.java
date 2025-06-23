package com.AgendaVet.AgendaVet.controller;

import com.AgendaVet.AgendaVet.assemblers.ResenaModelAssebler;
import com.AgendaVet.AgendaVet.model.Resena;
import com.AgendaVet.AgendaVet.service.ResenaService;
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
@RequestMapping("/api/v2/resenas")
@Tag(name = "Reseñas V2 (HATEOAS)", description = "API REST con HATEOAS para gestión avanzada de reseñas y calificaciones")
public class ResenaControllerV2 {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private ResenaModelAssebler assembler;

    @Operation(
        summary = "Obtener todas las reseñas (HATEOAS)",
        description = "Retorna una colección de reseñas con enlaces HATEOAS para navegación hipermedia. " +
                     "Incluye enlaces de auto-referencia y navegación a recursos relacionados."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de reseñas obtenida exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(description = "Colección HAL de reseñas con enlaces hipermedia")
            )
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "No hay reseñas registradas en el sistema"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Resena>>> getAllResenas() {
        List<EntityModel<Resena>> resenas = resenaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                resenas,
                linkTo(methodOn(ResenaControllerV2.class).getAllResenas()).withSelfRel()
        ));
    }

    @Operation(
        summary = "Obtener reseña por ID (HATEOAS)",
        description = "Retorna una reseña específica con enlaces HATEOAS para acceder a recursos relacionados como usuario y veterinaria."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Reseña encontrada exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Resena.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Reseña no encontrada"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "ID de reseña inválido"
        )
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Resena>> getResenaById(
            @Parameter(description = "ID único de la reseña", required = true, example = "1")
            @PathVariable Long id) {
        Resena resena = resenaService.findById(id);
        if (resena == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(resena));
    }

    @Operation(
        summary = "Crear nueva reseña (HATEOAS)",
        description = "Crea una nueva reseña y retorna el recurso creado con enlaces HATEOAS. " +
                     "La respuesta incluye la URI del recurso creado en el header Location."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Reseña creada exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Resena.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de reseña inválidos"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario o veterinaria no encontrados"
        )
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Resena>> createResena(
            @Parameter(description = "Datos de la nueva reseña", required = true)
            @RequestBody Resena resena) {
        Resena newResena = resenaService.save(resena);
        return ResponseEntity
                .created(linkTo(methodOn(ResenaControllerV2.class).getResenaById(newResena.getId())).toUri())
                .body(assembler.toModel(newResena));
    }

    @Operation(
        summary = "Obtener reseñas por veterinaria (HATEOAS)",
        description = "Retorna todas las reseñas asociadas a una veterinaria específica con enlaces HATEOAS para navegación hipermedia."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de reseñas de la veterinaria obtenida exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(description = "Colección HAL de reseñas con enlaces hipermedia")
            )
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "La veterinaria no tiene reseñas registradas"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Veterinaria no encontrada"
        )
    })
    @GetMapping(value = "/veterinaria/{veterinariaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Resena>>> getResenasByVeterinaria(
            @Parameter(description = "ID de la veterinaria", required = true, example = "1")
            @PathVariable Long veterinariaId) {
        List<Resena> resenas = resenaService.findByVeterinariaId(veterinariaId);

        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Resena>> recursos = resenas.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                recursos,
                linkTo(methodOn(ResenaControllerV2.class).getResenasByVeterinaria(veterinariaId)).withSelfRel()
        ));
    }

    @Operation(
        summary = "Obtener promedio de calificación por veterinaria (HATEOAS)",
        description = "Retorna el promedio de calificación de una veterinaria basado en todas sus reseñas."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Promedio de calificación obtenido exitosamente",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Double.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Veterinaria no encontrada o sin reseñas"
        )
    })
    @GetMapping(value = "/veterinaria/{veterinariaId}/promedio", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Double> getPromedioCalificacionByVeterinaria(
            @Parameter(description = "ID de la veterinaria", required = true, example = "1")
            @PathVariable Long veterinariaId) {
        Double promedio = resenaService.getPromedioCalificacionByVeterinaria(veterinariaId);
        if (promedio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(promedio);
    }
}
