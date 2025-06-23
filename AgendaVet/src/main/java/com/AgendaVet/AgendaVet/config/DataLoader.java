package com.AgendaVet.AgendaVet.config;

import com.AgendaVet.AgendaVet.model.*;
import com.AgendaVet.AgendaVet.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VeterinariaRepository veterinariaRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // Solo ejecutar si no hay datos
        if (usuarioRepository.count() > 0) {
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        // Crear Usuarios
        for (int i = 0; i < 10; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setContrasena(faker.internet().password());
            usuario.setRol(i < 2 ? "ADMIN" : "USER");
            usuario.setTelefono(faker.phoneNumber().cellPhone());
            usuario.setDireccion(faker.address().fullAddress());
            usuarioRepository.save(usuario);
        }

        // Crear Veterinarias
        for (int i = 0; i < 5; i++) {
            Veterinaria veterinaria = new Veterinaria();
            veterinaria.setNombre("Veterinaria " + faker.company().name());
            veterinaria.setDireccion(faker.address().fullAddress());
            veterinaria.setTelefono(faker.phoneNumber().phoneNumber());
            veterinaria.setEmail(faker.internet().emailAddress());
            veterinaria.setHorario("Lunes a Viernes: 08:00 - 18:00, Sábados: 08:00 - 14:00");
            veterinariaRepository.save(veterinaria);
        }

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Veterinaria> veterinarias = veterinariaRepository.findAll();

        // Crear Mascotas
        for (int i = 0; i < 15; i++) {
            Mascota mascota = new Mascota();
            mascota.setNombre(faker.animal().name());
            mascota.setEspecie(faker.options().option("Perro", "Gato", "Conejo", "Hamster", "Ave"));
            mascota.setRaza(faker.dog().breed());
            mascota.setEdad(random.nextInt(15) + 1);
            mascota.setPropietario(usuarios.get(random.nextInt(usuarios.size())));
            mascotaRepository.save(mascota);
        }

        List<Mascota> mascotas = mascotaRepository.findAll();

        // Crear Servicios
        String[] servicios = {"Consulta General", "Vacunación", "Cirugía", "Radiografía", 
                             "Análisis de Sangre", "Limpieza Dental", "Esterilización", "Desparasitación"};
        
        for (Veterinaria vet : veterinarias) {
            for (int i = 0; i < 4; i++) {
                Servicio servicio = new Servicio();
                servicio.setNombre(servicios[random.nextInt(servicios.length)]);
                servicio.setDescripcion(faker.lorem().sentence());
                servicio.setPrecio((float) faker.number().randomDouble(0, 20000, 150000));
                servicio.setDuracion(random.nextInt(120) + 30); // 30-150 minutos
                servicio.setVeterinaria(vet);
                servicioRepository.save(servicio);
            }
        }

        List<Servicio> serviciosList = servicioRepository.findAll();

        // Crear Reservas
        for (int i = 0; i < 20; i++) {
            Reserva reserva = new Reserva();
            reserva.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            reserva.setMascota(mascotas.get(random.nextInt(mascotas.size())));
            reserva.setVeterinaria(veterinarias.get(random.nextInt(veterinarias.size())));
            reserva.setServicio(serviciosList.get(random.nextInt(serviciosList.size())));
            
            LocalDate fechaBase = LocalDate.now().plusDays(random.nextInt(30) - 15);
            reserva.setFecha(fechaBase);
            reserva.setHora(LocalTime.of(random.nextInt(10) + 8, random.nextInt(2) * 30));
            
            reserva.setEstado(faker.options().option("PENDIENTE", "CONFIRMADA", "COMPLETADA", "CANCELADA"));
            // Eliminar: reserva.setObservaciones(faker.lorem().sentence());
            reservaRepository.save(reserva);
        }

        List<Reserva> reservas = reservaRepository.findAll();

        // Crear Reseñas
        for (int i = 0; i < 15; i++) {
            Resena resena = new Resena();
            resena.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            resena.setVeterinaria(veterinarias.get(random.nextInt(veterinarias.size())));
            resena.setPuntuacion(random.nextInt(5) + 1);
            resena.setComentario(faker.lorem().paragraph());
            resena.setFecha(LocalDate.now().minusDays(random.nextInt(90)).toString());
            resenaRepository.save(resena);
        }

        // Crear Notificaciones
        for (int i = 0; i < 25; i++) {
            Notificacion notificacion = new Notificacion();
            notificacion.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            
            if (!reservas.isEmpty()) {
                notificacion.setReserva(reservas.get(random.nextInt(reservas.size())));
            }
            
            notificacion.setTipo(faker.options().option("RECORDATORIO", "CONFIRMACION", "CANCELACION", "PROMOCION"));
            notificacion.setMensaje(faker.lorem().sentence());
            notificacion.setFechaCreacion(LocalDateTime.now().minusDays(random.nextInt(30)));
            notificacion.setLeido(random.nextBoolean());
            notificacionRepository.save(notificacion);
        }

        System.out.println("✅ Datos de prueba cargados exitosamente:");
        System.out.println("   - " + usuarioRepository.count() + " usuarios");
        System.out.println("   - " + veterinariaRepository.count() + " veterinarias");
        System.out.println("   - " + mascotaRepository.count() + " mascotas");
        System.out.println("   - " + servicioRepository.count() + " servicios");
        System.out.println("   - " + reservaRepository.count() + " reservas");
        System.out.println("   - " + resenaRepository.count() + " reseñas");
        System.out.println("   - " + notificacionRepository.count() + " notificaciones");
    }
}