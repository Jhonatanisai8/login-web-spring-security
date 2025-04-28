package com.isai.demologinspringboot.app.security;

import com.isai.demologinspringboot.app.services.UserDetailsServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //incriptamos las contraseñas
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuramos un AuthenticationProvider con UserDetailsService y PasswordEncoder
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsServiceImplementation userDetailsServiceImplementation) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImplementation);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    //exponemos el AuthenticationManager como bean para usarlo en otros lugares
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //cadenas de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Habilitar CSRF si tu aplicación lo requiere
                .csrf(AbstractHttpConfigurer::disable)  // O deshabilitar solo si es API (por ejemplo: .disable())
                // Configuración de rutas públicas y protegidas
                .authorizeHttpRequests(authorizeRequests -> {
                    // Definir rutas públicas (sin autenticación)
                    authorizeRequests.requestMatchers("/register**", "/login**", "/js/**", "/css/**", "/img/**")
                            .permitAll();
                    // Cualquier otra ruta necesita estar autenticado
                    authorizeRequests.anyRequest().authenticated();
                })

                // Configurar login (personalizar con mensajes de error)
                .formLogin(form -> form
                        .loginPage("/login")    // Página de login personalizada
                        .loginProcessingUrl("/login")  // URL de procesamiento de login
                        .permitAll()
                        // Redirigir a una página con un mensaje si login falla
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/register", true) // Redirigir al home después de un login exitoso
                )

                // Configurar logout (aumentar seguridad y experiencia)
                .logout(logout -> logout
                        .logoutUrl("/logout")    // URL para cerrar sesión
                        .invalidateHttpSession(true)  // Invalidar sesión
                        .clearAuthentication(true)   // Limpiar la autenticación
                        .logoutSuccessUrl("/login?logout")   // Redirigir a login después de logout
                        .permitAll()
                )

                // Manejo de sesiones: Expirar sesiones después de un tiempo determinado
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Solo crear sesión si es necesario
                        .invalidSessionUrl("/login?invalid-session=true")   // Redirigir si la sesión es inválida
                        .maximumSessions(1)  // Limitar la cantidad de sesiones por usuario
                        .expiredUrl("/login?expired=true")  // Redirigir si la sesión expira
                )
                .build();
    }


}
