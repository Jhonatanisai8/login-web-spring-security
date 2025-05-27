package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.MembershipType;
import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.models.Payment; // Importar la entidad Payment
import com.isai.demologinspringboot.app.repositorys.PaymentRepository; // Importar el repositorio de Payment
import com.isai.demologinspringboot.app.services.impl.MembershipTypeService;
import com.isai.demologinspringboot.app.services.impl.MembershipUserService;
import com.isai.demologinspringboot.app.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Para añadir atributos al modelo
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime; // Para la fecha y hora del pago
import java.util.UUID; // Para generar un número de recibo único

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/client")
public class MembershipUserController {

    private final UserService userService;
    private final MembershipUserService membershipUserService;
    private final MembershipTypeService membershipTypeService;
    private final PaymentRepository paymentRepository; // Inyectar el repositorio de pagos

    // Este método ahora preparará el checkout, en lugar de asignar la membresía directamente
    @RequestMapping(path = "/buy-membership", method = RequestMethod.POST)
    private String prepareCheckout(
            @RequestParam("idMembershipType") Integer idMembershipType,
            Model model, // Usar Model para pasar datos a la vista actual
            RedirectAttributes redirectAttributes) { // Para posibles errores antes del checkout
        try {
            MembershipType membershipType = membershipTypeService.findMembershipTypeById(idMembershipType);
            if (membershipType == null) {
                redirectAttributes.addFlashAttribute("error", "El tipo de membresía seleccionado no existe.");
                return "redirect:/client/memberships";
            }
            model.addAttribute("membershipType", membershipType);
            return "client/checkout-payments"; // Redirige a la nueva vista de checkout
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al preparar la compra de la membresía: " + e.getMessage());
            return "redirect:/client/memberships";
        }
    }

    // Nuevo método para procesar el pago
    @RequestMapping(path = "/process-payment", method = RequestMethod.POST)
    private String processPayment(
            @RequestParam("idMembershipType") Integer idMembershipType,
            @RequestParam("cardNumber") String cardNumber, // Datos simulados de la tarjeta
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam("cvv") String cvv,
            @RequestParam("cardHolderName") String cardHolderName,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        try {
            MembershipType membershipType = membershipTypeService.findMembershipTypeById(idMembershipType);
            if (membershipType == null) {
                redirectAttributes.addFlashAttribute("error", "El tipo de membresía para el pago no existe.");
                return "redirect:/client/memberships";
            }

            String username = principal.getName();
            User user = userService.findByUserName(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

            // --- Lógica de Simulación de Pago ---
            // Aquí iría la integración con una pasarela de pago real (Stripe, PayPal, etc.)
            // Por ahora, simulamos un pago exitoso siempre.
            boolean paymentSuccessful = simulatePayment(cardNumber, expiryDate, cvv, cardHolderName, membershipType.getPrice());

            if (paymentSuccessful) {
                // 1. Registrar el Pago
                Payment payment = Payment.builder()
                        .receiptNumber(UUID.randomUUID().toString()) // Genera un número de recibo único
                        .paymentAmount(membershipType.getPrice())
                        .paymentDate(LocalDateTime.now())
                        .concept("Compra de Membresía: " + membershipType.getMembershipName())
                        .observations("Pago simulado con tarjeta (XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4) + ")")
                        .receipt("simulated_receipt_" + UUID.randomUUID().toString().substring(0, 8) + ".pdf") // Simula un nombre de recibo
                        .user(user)
                        .membershipType(membershipType)
                        .build();
                paymentRepository.save(payment);

                // 2. Asignar la Membresía al Usuario (tu lógica actual)
                membershipUserService.assignMembershipToUser(user, membershipType);

                redirectAttributes.addFlashAttribute("success", "¡Pago y membresía adquirida con éxito!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error en el procesamiento del pago. Por favor, inténtelo de nuevo.");
            }

        } catch (Exception e) {
            // Manejo de excepciones más específico si es necesario
            redirectAttributes.addFlashAttribute("error", "Error crítico al procesar la membresía: " + e.getMessage());
        }
        return "redirect:/client/profile"; // Redirige al perfil del usuario después de la compra
    }

    // Método de simulación de pago
    private boolean simulatePayment(String cardNumber, String expiryDate, String cvv, String cardHolderName, Double amount) {
        // En un entorno real, aquí interactuarías con la API de una pasarela de pagos.
        // Podrías validar formatos básicos, pero no harías una validación de tarjeta real aquí.
        // Por simplicidad, siempre retornamos true.
        return true;
    }
}