package com.example.konsumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.internet.MimeMessage;

@Service
public class KonsumerService {

    @Autowired
    private JavaMailSender mailSender;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ✅ Konfigurasi email
    private static final String EMAIL_FROM = "nofriaidilfitra@gmail.com"; // pengirim
    private static final String EMAIL_TO = "raemon@pnp.ac.id";         // tujuan

    // ===========================
    // 📥 LISTENER RABBITMQ
    // ===========================
    @RabbitListener(queues = "myQueue")
    public void receivedMessage(String message) {
        System.out.println("📥 Received order data: " + message);

        try {
            JsonNode jsonNode = objectMapper.readTree(message);

            Integer orderId = jsonNode.get("id").asInt();
            Integer idProduk = jsonNode.get("idProduk").asInt();
            Integer idPelanggan = jsonNode.get("idPelanggan").asInt();
            Long harga = jsonNode.get("harga").asLong();
            Long jumlah = jsonNode.get("jumlah").asLong();
            Long total = jsonNode.get("total").asLong();

            // ✅ kirim email ke tujuan
            sendOrderEmail(orderId, idProduk, idPelanggan, harga, jumlah, total);

        } catch (Exception e) {
            System.err.println("❌ Error processing order: " + e.getMessage());

            sendErrorEmail(
                    EMAIL_TO,
                    "Order Processing Error",
                    "Error: " + e.getMessage() + "\nMessage: " + message
            );
        }
    }

    // ===========================
    // 📩 EMAIL ORDER
    // ===========================
    private void sendOrderEmail(Integer orderId, Integer idProduk,
                                Integer idPelanggan, Long harga, Long jumlah, Long total) {

        String subject = "Notifikasi Order Baru";
        String html = buildOrderHtml(orderId, idProduk, idPelanggan, harga, jumlah, total);

        sendHtmlEmail(EMAIL_TO, subject, html);
    }

    // ===========================
    // ❌ EMAIL ERROR
    // ===========================
    private void sendErrorEmail(String to, String subject, String content) {

        String html = "<h2>Order Processing Error</h2><pre>" + content + "</pre>";

        sendHtmlEmail(to, subject, html);
    }

    // ===========================
    // 🔁 GENERIC EMAIL SENDER
    // ===========================
    private void sendHtmlEmail(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(EMAIL_FROM); // ✅ pengirim
            helper.setTo(to);           // ✅ tujuan
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(message);
            System.out.println("✅ Email sent to: " + to);

        } catch (Exception e) {
            System.err.println("❌ Failed sending email: " + e.getMessage());
        }
    }

    // ===========================
    // 🧾 HTML TEMPLATE
    // ===========================
    private String buildOrderHtml(Integer orderId, Integer idProduk,
                                 Integer idPelanggan, Long harga, Long jumlah, Long total) {

        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset='UTF-8'>"
                + "<title>Notifikasi Order</title>"
                + "</head>"
                + "<body>"
                + "<h2>📦 Notifikasi Order Baru</h2>"
                + "<table border='1' cellpadding='8' cellspacing='0'>"
                + "<tr><th>Order ID</th><td>" + orderId + "</td></tr>"
                + "<tr><th>Product ID</th><td>" + idProduk + "</td></tr>"
                + "<tr><th>Customer ID</th><td>" + idPelanggan + "</td></tr>"
                + "<tr><th>Harga</th><td>Rp " + String.format("%,d", harga) + "</td></tr>"
                + "<tr><th>Jumlah</th><td>" + jumlah + "</td></tr>"
                + "<tr><th>Total</th><td><b>Rp " + String.format("%,d", total) + "</b></td></tr>"
                + "</table>"
                + "<br/>"
                + "<p>Terima kasih 🙌</p>"
                + "</body>"
                + "</html>";
    }
}