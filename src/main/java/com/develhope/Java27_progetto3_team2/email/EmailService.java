package com.develhope.Java27_progetto3_team2.email;

import com.develhope.Java27_progetto3_team2.order.entity.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendOrderConfirmationEmail(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'alle' HH:mm:ss");
        String subject = "Conferma Ordine";
        String linkPath = "https://drive.google.com/uc?export=view&id=1e8D9jy0WGHrl80lUHNUHDwcIwhlnqGHs";
        String body =
                "<html>" +
                        "<body style='background-color: #ffffff;'>" +
                        "<div style='color: #ffffff; font-family: Arial, sans-serif;'>" +
                        "<img src='" + linkPath + "' alt='WeEat Logo' style='width: 600px; height: auto;'>" +
                        "<p style='color: #ffb6c1;'>Ciao " + order.getUser().getName() + " " + order.getUser().getSurname() + " hai effettuato un ordine su WeEat!</p>" +
                        "<h3 style='color: #ffb6c1;'>Dettagli Ordine:</h3>" +
                        "<p style='color: #ffb6c1;'>Data Ordine: " + order.getOrderDate().format(formatter) + "</p>" +
                        "<p style='color: #ffb6c1;'>Data Arrivo Ordine:" + order.getDeliveryTime().format(formatter) + "</p>" +
                        "<p style='color: #ffb6c1;'>Indirizzo: " + order.getDeliveryAddress() + "</p>" +
                        "<p style='color: #ffb6c1;'>Spedito da: " + order.getRestaurant().getNameRestaurant() + "</p>" +
                        "<p style='color: #ffb6c1;'>Metodo di Pagamento: " + order.getPaymentMethod() + "</p>" +
                        "<p style='color: #ffb6c1;'>Prodotti: " + order.getItems().toString() + "</p>" +
                        "<p style='color: #ffb6c1;'>Prezzo Totale: " + order.getTotalPrice() + "€" + "</p>" +
                        "</div>" +
                        "</body>" +
                        "</html>";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("weeatnotificationservice@gmail.com");
            helper.setTo(order.getUser().getEmail());
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
