package com.AgendaVet.AgendaVet.controller;

import com.AgendaVet.AgendaVet.assemblers.MascotaModelAssembler;
import com.AgendaVet.AgendaVet.assemblers.UsuarioModelAssembler;
import com.AgendaVet.AgendaVet.model.Mascota;
import com.AgendaVet.AgendaVet.model.Usuario;
import com.AgendaVet.AgendaVet.service.MascotaService;
import com.AgendaVet.AgendaVet.service.UsuarioService;
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
@RequestMapping("/api/v2/usuarios")
@Tag(name = "Usuarios V2 (HATEOAS)", description = "API REST con HATEOAS para gestión avanzada de usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private MascotaModelAssembler mascotaModelAssembler;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Operation(
        summary = "Obtener todos los usuarios (HATEOAS)",
        description = "Retorna una colección de usuarios con enlaces HATEOAS para navegación hipermedia. " +
                     "Incluye enlaces de auto-referencia y navegación a recursos relacionados."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista de usuarios obtenida exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(description = "Colección HAL de usuarios con enlaces hipermedia")
            )
        ),
        @ApiResponse(
            responseCode = "204", 
            description = "No hay usuarios registrados en el sistema"
        ),
        @ApiResponse(
            responseCode = "500", 
            description = "Error interno del servidor"
        )
    })
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel()
        ));
    }

    @Operation(
        summary = "Obtener usuario por ID (HATEOAS)",
        description = "Retorna un usuario específico con enlaces HATEOAS para acceder a recursos relacionados como mascotas."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Usuario encontrado exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Usuario.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "ID de usuario inválido"
        )
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(
            @Parameter(description = "ID único del usuario", required = true, example = "1")
            @PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @Operation(
        summary = "Crear nuevo usuario (HATEOAS)",
        description = "Crea un nuevo usuario y retorna el recurso creado con enlaces HATEOAS. " +
                     "La respuesta incluye la URI del recurso creado en el header Location."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Usuario creado exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Usuario.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de usuario inválidos"
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto - email ya registrado"
        )
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> createUsuario(
            @Parameter(description = "Datos del nuevo usuario", required = true)
            @RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioService.save(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(newUsuario.getId())).toUri())
                .body(assembler.toModel(newUsuario));
    }

    @Operation(
        summary = "Actualizar usuario completo (HATEOAS)",
        description = "Actualiza todos los campos de un usuario existente (reemplazo completo) y retorna el recurso con enlaces HATEOAS."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Usuario actualizado exitosamente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Usuario.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de usuario inválidos"
        )
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos completos del usuario", required = true)
            @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario updatedUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(assembler.toModel(updatedUsuario));
    }

    @Operation(
        summary = "Actualizar usuario parcial (HATEOAS)",
        description = "Actualiza parcialmente los campos de un usuario existente (solo los campos proporcionados) y retorna el recurso con enlaces HATEOAS."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Usuario actualizado parcialmente con enlaces HATEOAS",
            content = @Content(
                mediaType = "application/hal+json",
                schema = @Schema(implementation = Usuario.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Datos de usuario inválidos"
        )
    })
    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> patchUsuario(
            @Parameter(description = "ID del usuario a actualizar parcialmente", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Campos del usuario a actualizar (solo los campos proporcionados serán modificados)", required = true)
            @RequestBody Usuario usuario) {
        Usuario updatedUsuario = usuarioService.patchUsuario(id, usuario);
        if (updatedUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedUsuario));
    }

    @Operation(
        summary = "Eliminar usuario (HATEOAS)",
        description = "Elimina un usuario del sistema de forma permanente. Esta operación no se puede deshacer."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Usuario eliminado exitosamente"
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Usuario no encontrado"
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "Conflicto - usuario tiene dependencias que impiden la eliminación"
        )
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener mascotas de un usuario (HATEOAS)",
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
    @GetMapping(value = "/{id}/mascotas", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Mascota>>> getMascotasByUsuario(
            @Parameter(description = "ID del usuario propietario de las mascotas", required = true, example = "1")
            @PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        List<Mascota> mascotas = mascotaService.findByUsuarioId(id);
        if (mascotas == null || mascotas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Mascota>> mascotasModel = mascotas.stream()
                .map(mascotaModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(
                mascotasModel,
                linkTo(methodOn(UsuarioControllerV2.class).getMascotasByUsuario(id)).withSelfRel()
        ));
    }
}