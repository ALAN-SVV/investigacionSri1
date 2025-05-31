package facturacion.model;

import javax.xml.bind.JAXBException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

public class CrearXML {
    public String generarXMLFactura(Factura factura) throws JAXBException {
        // Configuración básica de la factura electrónica según XSD del SRI
        StringWriter sw = new StringWriter();

        try {
            // Formatear fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String fechaEmision = dateFormat.format(factura.getFechaEmision());

            // Crear XML manualmente (simplificado)
            StringBuilder xmlBuilder = new StringBuilder();
            xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xmlBuilder.append("<factura id=\"comprobante\" version=\"1.0.0\">\n");
            xmlBuilder.append("  <infoTributaria>\n");
            xmlBuilder.append("    <ambiente>").append(factura.getAmbiente()).append("</ambiente>\n");
            xmlBuilder.append("    <tipoEmision>").append(factura.getTipoEmision()).append("</tipoEmision>\n");
            xmlBuilder.append("    <razonSocial>Elvis Pachacama</razonSocial>\n");
            xmlBuilder.append("    <nombreComercial>El Beaterio</nombreComercial>\n");
            xmlBuilder.append("    <ruc>1719284752001</ruc>\n");
            xmlBuilder.append("    <claveAcceso>").append(generarClaveAcceso(factura)).append("</claveAcceso>\n");
            xmlBuilder.append("    <codDoc>01</codDoc>\n"); // 01: Factura
            xmlBuilder.append("    <estab>001</estab>\n");
            xmlBuilder.append("    <ptoEmi>001</ptoEmi>\n");
            xmlBuilder.append("    <secuencial>").append(factura.getNumeroFactura().substring(8)).append("</secuencial>\n");
            xmlBuilder.append("    <dirMatriz>El Beaterio</dirMatriz>\n");
            xmlBuilder.append("  </infoTributaria>\n");

            // InfoFactura
            xmlBuilder.append("  <infoFactura>\n");
            xmlBuilder.append("    <fechaEmision>").append(fechaEmision).append("</fechaEmision>\n");
            xmlBuilder.append("    <dirEstablecimiento>El Beaterio</dirEstablecimiento>\n");
            xmlBuilder.append("    <obligadoContabilidad>NO</obligadoContabilidad>\n");
            xmlBuilder.append("    <tipoIdentificacionComprador>").append(factura.getCliente().getTipoIdentificacion()).append("</tipoIdentificacionComprador>\n");
            xmlBuilder.append("    <razonSocialComprador>").append(factura.getCliente().getRazonSocial()).append("</razonSocialComprador>\n");
            xmlBuilder.append("    <identificacionComprador>").append(factura.getCliente().getIdentificacion()).append("</identificacionComprador>\n");
            xmlBuilder.append("    <totalSinImpuestos>").append(String.format("%.2f", factura.getSubtotal())).append("</totalSinImpuestos>\n");
            xmlBuilder.append("    <totalDescuento>0.00</totalDescuento>\n");

            // Total con impuestos
            xmlBuilder.append("    <totalConImpuestos>\n");
            xmlBuilder.append("      <totalImpuesto>\n");
            xmlBuilder.append("        <codigo>2</codigo>\n"); // 2: IVA
            xmlBuilder.append("        <codigoPorcentaje>2</codigoPorcentaje>\n"); // 2: IVA 12%
            xmlBuilder.append("        <baseImponible>").append(String.format("%.2f", factura.getSubtotal())).append("</baseImponible>\n");
            xmlBuilder.append("        <valor>").append(String.format("%.2f", factura.getIva())).append("</valor>\n");
            xmlBuilder.append("      </totalImpuesto>\n");
            xmlBuilder.append("    </totalConImpuestos>\n");

            xmlBuilder.append("    <propina>0.00</propina>\n");
            xmlBuilder.append("    <importeTotal>").append(String.format("%.2f", factura.getTotal())).append("</importeTotal>\n");
            xmlBuilder.append("  </infoFactura>\n");

            // Detalles
            xmlBuilder.append("  <detalles>\n");
            for (Producto producto : factura.getProductos()) {
                xmlBuilder.append("    <detalle>\n");
                xmlBuilder.append("      <codigoPrincipal>").append(producto.getCodigoPrincipal()).append("</codigoPrincipal>\n");
                xmlBuilder.append("      <descripcion>").append(producto.getDescripcion()).append("</descripcion>\n");
                xmlBuilder.append("      <cantidad>").append(producto.getCantidad()).append("</cantidad>\n");
                xmlBuilder.append("      <precioUnitario>").append(producto.getPrecioUnitario()).append("</precioUnitario>\n");
                xmlBuilder.append("      <descuento>").append(producto.getDescuento()).append("</descuento>\n");
                xmlBuilder.append("      <precioTotalSinImpuesto>").append(String.format("%.2f", producto.getPrecioTotal())).append("</precioTotalSinImpuesto>\n");

                // Impuestos del producto
                xmlBuilder.append("      <impuestos>\n");
                xmlBuilder.append("        <impuesto>\n");
                xmlBuilder.append("          <codigo>2</codigo>\n");
                xmlBuilder.append("          <codigoPorcentaje>2</codigoPorcentaje>\n");
                xmlBuilder.append("          <tarifa>12.00</tarifa>\n");
                xmlBuilder.append("          <baseImponible>").append(String.format("%.2f", producto.getPrecioTotal())).append("</baseImponible>\n");
                xmlBuilder.append("          <valor>").append(String.format("%.2f", producto.getPrecioTotal() * 0.12)).append("</valor>\n");
                xmlBuilder.append("        </impuesto>\n");
                xmlBuilder.append("      </impuestos>\n");
                xmlBuilder.append("    </detalle>\n");
            }
            xmlBuilder.append("  </detalles>\n");

            xmlBuilder.append("</factura>");

            return xmlBuilder.toString();

        } catch (Exception e) {
            throw new JAXBException("Error al generar XML: " + e.getMessage());
        }
    }

    private String generarClaveAcceso(Factura factura) {
        // Implementación simplificada de generación de clave de acceso
        // Formato real: 49 dígitos según especificación SRI
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fecha = dateFormat.format(factura.getFechaEmision());

        return "1719284752001" + fecha + "01" + "001" + "001" +
                factura.getNumeroFactura().substring(8) + "12345678" + "1";
    }
}