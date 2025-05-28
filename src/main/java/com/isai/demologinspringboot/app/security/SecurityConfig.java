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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Habilitar o deshabilitar CSRF según tu necesidad
                .csrf(AbstractHttpConfigurer::disable)

                // Configuración de rutas públicas y protegidas
                .authorizeHttpRequests(authorizeRequests -> {
                    // Rutas públicas (accesibles sin login)
                    authorizeRequests.requestMatchers(
                            "/admin",
                            "/register**",
                            "/login**",
                            "/js/**",
                            "/css/**",
                            "/img/**",
                            "/my-ai-service/api/ai/v1/generate"  // <-- Ruta pública añadida
                    ).permitAll();

                    // Rutas que requieren autenticación
                    authorizeRequests.requestMatchers("/admin/**").authenticated();
                    authorizeRequests.requestMatchers("/client/**").authenticated();

                    // Cualquier otra ruta también requiere autenticación
                    authorizeRequests.anyRequest().authenticated();
                })

                // Configuración de login personalizado
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/", true)
                )

                // Configuración de logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // Manejo de sesiones
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/login?invalid-session=true")
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true")
                )

                .build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorizeRequests -> {
//                    // Permitir TODO para este endpoint por ahora para depurar
//                    authorizeRequests.requestMatchers("my-ai-service/api/ai/v1/generate").permitAll();
//                    // Para todo lo demás, puedes mantener tu configuración original
//                    authorizeRequests.anyRequest().authenticated(); // O si quieres, también .permitAll() temporalmente para ver si es algo más
//                })
//                // ... el resto de tu configuración (formLogin, logout, sessionManagement)
//                .build();
//    }
}
