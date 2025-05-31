package facturacion.model;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class EnvioSRI {
    private static final String URL_RECEPCION_PRUEBAS = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
    private static final String URL_RECEPCION_PRODUCCION = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
    private static final String URL_AUTORIZACION_PRUEBAS = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
    private static final String URL_AUTORIZACION_PRODUCCION = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";

    public String enviarFacturaSRI(String xmlFirmado, String ambiente) throws Exception {
        String urlWS = ambiente.equals("1") ? URL_RECEPCION_PRUEBAS : URL_RECEPCION_PRODUCCION;

        try {
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            conn.setDoOutput(true);

            // Crear SOAP request
            String soapRequest = crearSOAPRequest(xmlFirmado);

            // Enviar request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = soapRequest.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer respuesta
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Procesar respuesta SOAP
                return "Factura enviada correctamente al SRI";
            } else {
                return "Error al enviar factura. Código: " + responseCode;
            }
        } catch (Exception e) {
            throw new Exception("Error en comunicación con SRI: " + e.getMessage());
        }
    }

    private String crearSOAPRequest(String xmlFactura) {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ec:validarComprobante>\n" +
                "         <xml>" + escapeXML(xmlFactura) + "</xml>\n" +
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