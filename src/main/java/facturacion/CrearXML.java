package facturacion;

import facturacion.model.Factura;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.text.SimpleDateFormat;

public class CrearXML {
    public String generarXML(Factura factura) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // Elemento raíz
            Element facturaElem = doc.createElement("factura");
            facturaElem.setAttribute("id", "comprobante");
            facturaElem.setAttribute("version", "1.0.0");
            doc.appendChild(facturaElem);

            // Info Tributaria
            Element infoTributaria = doc.createElement("infoTributaria");
            addChild(doc, infoTributaria, "ambiente", factura.getAmbiente());
            addChild(doc, infoTributaria, "tipoEmision", factura.getTipoEmision());
            addChild(doc, infoTributaria, "razonSocial", factura.getRazonSocial());
            addChild(doc, infoTributaria, "ruc", factura.getRuc());
            addChild(doc, infoTributaria, "claveAcceso", generarClaveAcceso(factura));
            // ... otros campos
            facturaElem.appendChild(infoTributaria);

            // Info Factura
            Element infoFactura = doc.createElement("infoFactura");
            addChild(doc, infoFactura, "fechaEmision",
                    new SimpleDateFormat("dd/MM/yyyy").format(factura.getFechaEmision()));
            addChild(doc, infoFactura, "dirEstablecimiento", factura.getDirEstablecimiento());
            // ... otros campos
            facturaElem.appendChild(infoFactura);

            // Detalles
            Element detalles = doc.createElement("detalles");
            for (Producto producto : factura.getProductos()) {
                Element detalle = doc.createElement("detalle");
                addChild(doc, detalle, "codigoPrincipal", producto.getCodigo());
                addChild(doc, detalle, "descripcion", producto.getDescripcion());
                addChild(doc, detalle, "cantidad", String.valueOf(producto.getCantidad()));
                addChild(doc, detalle, "precioUnitario", String.valueOf(producto.getPrecioUnitario()));
                // ... otros campos
                detalles.appendChild(detalle);
            }
            facturaElem.appendChild(detalles);

            // Convertir a String
            return XMLUtil.documentToString(doc);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Error al generar XML", e);
        }
    }

    private void addChild(Document doc, Element parent, String tagName, String value) {
        Element child = doc.createElement(tagName);
        child.appendChild(doc.createTextNode(value));
        parent.appendChild(child);
    }

    private String generarClaveAcceso(Factura factura) {
        // Implementación según especificación del SRI
        // Ejemplo simplificado:
        String clave = factura.getFechaEmision().format("ddMMyyyy") +
                factura.getCodDoc() +
                factura.getRuc() +
                factura.getAmbiente() +
                factura.getSerie() +
                factura.getNumeroComprobante() +
                "12345678"; // Número aleatorio

        return clave + calcularDigitoVerificador(clave);
    }

    private String calcularDigitoVerificador(String clave) {
        // Algoritmo para calcular dígito verificador
        // Implementación según especificación del SRI
        return "8"; // Ejemplo simplificado
    }
}
