package facturacion;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class FirmarXML {
    private static final String CERTIFICATE_PATH = "/certificado.p12";
    private static final String CERTIFICATE_PASSWORD = "tu_password"; // Cambia por tu contraseña real

    public String firmarDocumento(String xmlContent) {
        try {
            // Cargar el certificado desde el classpath
            InputStream certStream = FirmarXML.class.getResourceAsStream(CERTIFICATE_PATH);
            if (certStream == null) {
                throw new RuntimeException("No se encontró el certificado en: " + CERTIFICATE_PATH);
            }

            // Cargar el keystore PKCS12
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, CERTIFICATE_PASSWORD.toCharArray());

            // Obtener el alias del certificado (normalmente solo hay uno)
            String alias = ks.aliases().nextElement();

            // Obtener la clave privada y el certificado
            PrivateKey privateKey = (PrivateKey) ks.getKey(alias, CERTIFICATE_PASSWORD.toCharArray());
            X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);

            // Verificar que el certificado esté cargado correctamente
            if (privateKey == null || certificate == null) {
                throw new RuntimeException("No se pudo cargar la clave privada o el certificado");
            }

            // Resto del código de firma...
            // (usa privateKey y certificate como en la implementación anterior)

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar o usar el certificado digital", e);
        }
    }
}
