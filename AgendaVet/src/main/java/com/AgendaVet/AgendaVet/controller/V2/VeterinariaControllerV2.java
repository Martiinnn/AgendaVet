package com.AgendaVet.AgendaVet.controller.V2;



import com.AgendaVet.AgendaVet.assemblers.VeterinariaModelAssembler;
import com.AgendaVet.AgendaVet.model.Veterinaria;
import com.AgendaVet.AgendaVet.model.Servicio;
import com.AgendaVet.AgendaVet.service.VeterinariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/veterinarias")
@Tag(name = "Veterinarias V2", description = "API V2 para gestión de veterinarias con HATEOAS")
public class VeterinariaControllerV2 {

    @Autowired
    private VeterinariaService veterinariaService;

    @Autowired
    private VeterinariaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las veterinarias")
    @ApiResponse(responseCode = "200", description = "Lista de veterinarias obtenida exitosamente")
    public CollectionModel<EntityModel<Veterinaria>> getAllVeterinarias() {
        List<EntityModel<Veterinaria>> veterinarias = veterinariaService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(veterinarias,
                linkTo(methodOn(VeterinariaControllerV2.class).getAllVeterinarias()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener veterinaria por ID")
    @ApiResponse(responseCode = "200", description = "Veterinaria encontrada")
    public ResponseEntity<EntityModel<Veterinaria>> getVeterinariaById(
            @Parameter(description = "ID de la veterinaria") @PathVariable Long id) {
        Veterinaria veterinaria = veterinariaService.findById(id);
        if (veterinaria == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(veterinaria));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear nueva veterinaria")
    @ApiResponse(responseCode = "201", description = "Veterinaria creada exitosamente")
    public ResponseEntity<EntityModel<Veterinaria>> createVeterinaria(@RequestBody Veterinaria veterinaria) {
        Veterinaria savedVeterinaria = veterinariaService.save(veterinaria);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(savedVeterinaria));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar veterinaria")
    @ApiResponse(responseCode = "200", description = "Veterinaria actualizada exitosamente")
    public ResponseEntity<EntityModel<Veterinaria>> updateVeterinaria(
            @Parameter(description = "ID de la veterinaria") @PathVariable Long id,
            @RequestBody Veterinaria veterinaria) {
        Veterinaria existingVeterinaria = veterinariaService.findById(id);
        if (existingVeterinaria == null) {
            return ResponseEntity.notFound().build();
        }
        veterinaria.setId(id);
        Veterinaria updatedVeterinaria = veterinariaService.save(veterinaria);
        return ResponseEntity.ok(assembler.toModel(updatedVeterinaria));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar veterinaria")
    @ApiResponse(responseCode = "204", description = "Veterinaria eliminada exitosamente")
    public ResponseEntity<Void> deleteVeterinaria(
            @Parameter(description = "ID de la veterinaria") @PathVariable Long id) {
        Veterinaria existingVeterinaria = veterinariaService.findById(id);
        if (existingVeterinaria == null) {
            return ResponseEntity.notFound().build();
        }
        veterinariaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{veterinariaId}/servicios")
    @Operation(summary = "Agregar servicio a veterinaria")
    @ApiResponse(responseCode = "201", description = "Servicio agregado exitosamente")
    public ResponseEntity<Servicio> addServicio(
            @Parameter(description = "ID de la veterinaria") @PathVariable Long veterinariaId,
            @RequestBody Servicio servicio) {
        Servicio savedServicio = veterinariaService.addServicio(veterinariaId, servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedServicio);
    }

    @GetMapping("/{veterinariaId}/servicios")
    @Operation(summary = "Obtener servicios de veterinaria")
    @ApiResponse(responseCode = "200", description = "Lista de servicios obtenida exitosamente")
    public ResponseEntity<List<Servicio>> getServiciosByVeterinaria(
            @Parameter(description = "ID de la veterinaria") @PathVariable Long veterinariaId) {
        List<Servicio> servicios = veterinariaService.findServiciosByVeterinariaId(veterinariaId);
        return ResponseEntity.ok(servicios);
    }

    @PostMapping("/{veterinariaId}/horarios")
    @Operation(summary = "Actualizar horarios de veterinaria")
    @ApiResponse(responseCode = "200", description = "Horarios actualizados exitosamente")
    public ResponseEntity<?> updateHorarios(
            @Parameter(description = "ID de la veterinaria") @PathVariable Long veterinariaId,
            @RequestBody String horarios) {
        Object result = veterinariaService.updateHorarios(veterinariaId, horarios);
        return ResponseEntity.ok(result);
    }
}