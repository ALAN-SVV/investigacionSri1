package facturacion.model;

import org.apache.xml.security.Init;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class FirmaXML {
    static {
        Init.init();
    }

    public String firmarFactura(String xmlFactura) throws Exception {
        // Cargar el certificado .p12
        KeyStore ks = KeyStore.getInstance("PKCS12");
        char[] password = "Elvis2103".toCharArray();
        ks.load(getClass().getResourceAsStream("/14045426_identity_1719284752.p12"), password);

        String alias = ks.aliases().nextElement();
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password);
        X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

        // Parsear el XML
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(xmlFactura.getBytes("UTF-8")));

        // Crear la firma
        XMLSignature sig = new XMLSignature(doc, "", XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256);
        Element root = doc.getDocumentElement();
        root.appendChild(sig.getElement());

        // Configurar transformaciones
        Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
        transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);

        // Añadir referencia - CORRECCIÓN AQUÍ
        // Prueba con SHA1 primero (luego puedes cambiar a SHA256)
        sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);

        // Añadir información de clave y certificado
        sig.addKeyInfo(cert);
        sig.addKeyInfo(cert.getPublicKey());

        // Firmar el documento
        sig.sign(privateKey);

        // Convertir a String
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        StringWriter sw = new StringWriter();
        trans.transform(new DOMSource(doc), new StreamResult(sw));

        return sw.toString();
    }
}