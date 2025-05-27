package com.isai.demologinspringboot.app.controllers;

import com.isai.demologinspringboot.app.models.MembershipType;
import com.isai.demologinspringboot.app.models.User;
import com.isai.demologinspringboot.app.models.Payment;
import com.isai.demologinspringboot.app.repositorys.PaymentRepository;
import com.isai.demologinspringboot.app.services.impl.MembershipTypeService;
import com.isai.demologinspringboot.app.services.impl.MembershipUserService;
import com.isai.demologinspringboot.app.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/client")
public class MembershipUserController {

    private final UserService userService;
    private final MembershipUserService membershipUserService;
    private final MembershipTypeService membershipTypeService;
    private final PaymentRepository paymentRepository;

    //  método para preparar el checkout, en lugar de asignar la membresía directamente
    @RequestMapping(path = "/buy-membership", method = RequestMethod.POST)
    private String prepareCheckout(
            @RequestParam("idMembershipType") Integer idMembershipType,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            MembershipType membershipType = membershipTypeService.findMembershipTypeById(idMembershipType);
            if (membershipType == null) {
                redirectAttributes.addFlashAttribute("error", "El tipo de membresía seleccionado no existe.");
                return "redirect:/client/memberships";
            }
            model.addAttribute("membershipType", membershipType);
            return "client/checkout-payments";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al preparar la compra de la membresía: " + e.getMessage());
            return "redirect:/client/memberships";
        }
    }

    //  método para procesar el pago
    @RequestMapping(path = "/process-payment", method = RequestMethod.POST)
    private String processPayment(
            @RequestParam("idMembershipType") Integer idMembershipType,
            @RequestParam("cardNumber") String cardNumber,
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

            boolean paymentSuccessful = simulatePayment(cardNumber, expiryDate, cvv, cardHolderName, membershipType.getPrice());

            if (paymentSuccessful) {
                // registramos el pago
                Payment payment = Payment.builder()
                        .receiptNumber(UUID.randomUUID().toString())
                        .paymentAmount(membershipType.getPrice())
                        .paymentDate(LocalDateTime.now())
                        .concept("Compra de Membresía: " + membershipType.getMembershipName())
                        .observations("Pago simulado con tarjeta (XXXX-XXXX-XXXX-" + cardNumber.substring(cardNumber.length() - 4) + ")")
                        .receipt("simulated_receipt_" + UUID.randomUUID().toString().substring(0, 8) + ".pdf")
                        .user(user)
                        .membershipType(membershipType)
                        .build();
                paymentRepository.save(payment);

                // asigamos la membresia al usuario
                membershipUserService.assignMembershipToUser(user, membershipType);

                redirectAttributes.addFlashAttribute("success", "¡Pago y membresía adquirida con éxito!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error en el procesamiento del pago. Por favor, inténtelo de nuevo.");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error crítico al procesar la membresía: " + e.getMessage());
        }
        return "redirect:/client/profile";
    }

    // simula el pago
    private boolean simulatePayment(String cardNumber, String expiryDate, String cvv, String cardHolderName, Double amount) {
        return true;
    }
}