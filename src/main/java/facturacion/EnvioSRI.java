package facturacion;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class EnvioSRI {
    public String enviarFactura(String xmlFirmado) {
        try {
            // URL del servicio web del SRI (ambiente de pruebas)
            URL url = new URL("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            connection.setDoOutput(true);

            // Construir el SOAP request
            String soapRequest = construirSOAPRequest(xmlFirmado);

            // Enviar la petición
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = soapRequest.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer la respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } else {
                throw new RuntimeException("Error en la conexión con el SRI: " + responseCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar factura al SRI", e);
        }
    }

    private String construirSOAPRequest(String xmlFirmado) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ec:validarComprobante>\n" +
                "         <xml>" + escapeXML(xmlFirmado) + "</xml>\n" +
                "      </ec:validarComprobante>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    private String escapeXML(String xml) {
        return xml.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
