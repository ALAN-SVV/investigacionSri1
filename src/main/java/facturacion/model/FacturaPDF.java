package facturacion.model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FacturaPDF {
    public String generarPDF(Factura factura, String xmlFirmado) throws IOException, DocumentException {
        // Crear directorio si no existe
        File directorio = new File("facturas_pdf");
        if (!directorio.exists()) {
            directorio.mkdir();
        }

        String filePath = "facturas_pdf/" + factura.getNumeroFactura() + ".pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));

        document.open();

        // Fuentes
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font fontNormal = new Font(Font.FontFamily.HELVETICA, 12);

        // Título
        Paragraph titulo = new Paragraph("FACTURA ELECTRÓNICA", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        // Datos de la factura
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        document.add(new Paragraph("Número: " + factura.getNumeroFactura(), fontNormal));
        document.add(new Paragraph("Fecha: " + sdf.format(factura.getFechaEmision()), fontNormal));
        document.add(new Paragraph("Ambiente: " + (factura.getAmbiente().equals("1") ? "Pruebas" : "Producción"), fontNormal));
        document.add(new Paragraph(" "));

        // Datos del emisor
        Paragraph emisor = new Paragraph("EMISOR", fontSubtitulo);
        emisor.setSpacingBefore(10f);
        document.add(emisor);
        document.add(new Paragraph("Razón Social: Elvis Pachacama", fontNormal));
        document.add(new Paragraph("RUC: 1719284752001", fontNormal));
        document.add(new Paragraph("Dirección: El Beaterio", fontNormal));
        document.add(new Paragraph(" "));

        // Datos del cliente
        Paragraph cliente = new Paragraph("CLIENTE", fontSubtitulo);
        cliente.setSpacingBefore(10f);
        document.add(cliente);
        document.add(new Paragraph("Razón Social: " + factura.getCliente().getRazonSocial(), fontNormal));
        document.add(new Paragraph("Identificación: " + factura.getCliente().getIdentificacion(), fontNormal));
        document.add(new Paragraph("Dirección: " + factura.getCliente().getDireccion(), fontNormal));
        document.add(new Paragraph(" "));

        // Productos
        Paragraph productos = new Paragraph("DETALLE DE PRODUCTOS/SERVICIOS", fontSubtitulo);
        productos.setSpacingBefore(10f);
        document.add(productos);

        // Tabla de productos (simplificada)
        for (Producto p : factura.getProductos()) {
            document.add(new Paragraph(p.getDescripcion() + " - Cantidad: " + p.getCantidad() +
                    " - Precio: $" + p.getPrecioUnitario() +
                    " - Total: $" + (p.getPrecioUnitario() * p.getCantidad()), fontNormal));
        }
        document.add(new Paragraph(" "));

        // Totales
        Paragraph totales = new Paragraph("TOTALES", fontSubtitulo);
        totales.setSpacingBefore(10f);
        document.add(totales);
        document.add(new Paragraph("Subtotal: $" + String.format("%.2f", factura.getSubtotal()), fontNormal));
        document.add(new Paragraph("IVA (12%): $" + String.format("%.2f", factura.getIva()), fontNormal));
        document.add(new Paragraph("TOTAL: $" + String.format("%.2f", factura.getTotal()), fontNormal));

        document.close();

        return filePath;
    }
}