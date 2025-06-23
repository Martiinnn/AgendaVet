package com.AgendaVet.AgendaVet.controller.V2;


import com.AgendaVet.AgendaVet.assemblers.ResenaModelAssebler;
import com.AgendaVet.AgendaVet.model.Resena;
import com.AgendaVet.AgendaVet.service.ResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Reseñas", description = "Gestión de reseñas")
public class ResenaControllerV2 {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private ResenaModelAssebler assembler;

    @Operation(summary = "Listar reseñas")
    @ApiResponse(responseCode = "200", description = "OK")
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

    @Operation(summary = "Obtener reseña por ID")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Resena>> getResenaById(
            @Parameter(description = "ID de la reseña")
            @PathVariable Long id) {
        Resena resena = resenaService.findById(id);
        if (resena == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(resena));
    }

    @Operation(summary = "Crear reseña")
    @ApiResponse(responseCode = "201", description = "Creada")
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Resena>> createResena(
            @RequestBody Resena resena) {
        Resena newResena = resenaService.save(resena);
        return ResponseEntity
                .created(linkTo(methodOn(ResenaControllerV2.class).getResenaById(newResena.getId())).toUri())
                .body(assembler.toModel(newResena));
    }

    @Operation(summary = "Reseñas por veterinaria")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/veterinaria/{veterinariaId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Resena>>> getResenasByVeterinaria(
            @Parameter(description = "ID de la veterinaria")
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

    @Operation(summary = "Promedio de calificación")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/veterinaria/{veterinariaId}/promedio", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Double> getPromedioCalificacionByVeterinaria(
            @Parameter(description = "ID de la veterinaria")
            @PathVariable Long veterinariaId) {
        Double promedio = resenaService.getPromedioCalificacionByVeterinaria(veterinariaId);
        if (promedio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(promedio);
    }
}
