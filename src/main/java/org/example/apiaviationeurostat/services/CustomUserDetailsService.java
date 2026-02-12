package org.example.apiaviationeurostat.services;

import org.example.apiaviationeurostat.entities.User;
import org.example.apiaviationeurostat.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado para cargar detalles de usuario desde la base de datos.
 * Implementa la interfaz {@link UserDetailsService} de Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor para inyectar el repositorio de usuarios.
     *
     * @param userRepository el repositorio de usuarios.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carga un usuario por su nombre de usuario.
     * Busca el usuario en la base de datos y lo convierte a un objeto {@link UserDetails}
     * compatible con Spring Security.
     *
     * @param username el nombre de usuario a buscar.
     * @return un objeto {@link UserDetails} con la información del usuario.
     * @throws UsernameNotFoundException si el usuario no existe en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Convertimos tu User (Mongo) a UserDetails (Spring Security)
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0])) // Spring espera roles
                .build();
    }
}