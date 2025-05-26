package facturacion;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sri.facturacion.model.Factura;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

public class FacturaPDF {
    public byte[] generarPDF(Factura factura) {
        try {
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Encabezado
            Paragraph header = new Paragraph("FACTURA", new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            // Información de la empresa
            Paragraph empresa = new Paragraph(
                    factura.getRazonSocial() + "\n" +
                            "RUC: " + factura.getRuc() + "\n" +
                            factura.getDirEstablecimiento(),
                    new Font(Font.FontFamily.HELVETICA, 10));
            document.add(empresa);

            // Información del comprobante
            PdfPTable tableInfo = new PdfPTable(2);
            tableInfo.setWidthPercentage(100);

            addCell(tableInfo, "Número:", factura.getNumeroComprobante());
            addCell(tableInfo, "Fecha Emisión:",
                    new SimpleDateFormat("dd/MM/yyyy").format(factura.getFechaEmision()));
            addCell(tableInfo, "Cliente:", factura.getCliente().getNombre());
            addCell(tableInfo, "RUC/CI:", factura.getCliente().getIdentificacion());

            document.add(tableInfo);

            // Detalle de productos
            PdfPTable tableDetalle = new PdfPTable(4);
            tableDetalle.setWidthPercentage(100);
            tableDetalle.setWidths(new float[]{2, 4, 2, 2});

            addHeaderCell(tableDetalle, "Código");
            addHeaderCell(tableDetalle, "Descripción");
            addHeaderCell(tableDetalle, "Cantidad");
            addHeaderCell(tableDetalle, "P. Unitario");
            addHeaderCell(tableDetalle, "Total");

            for (Producto producto : factura.getProductos()) {
                addCell(tableDetalle, producto.getCodigo());
                addCell(tableDetalle, producto.getDescripcion());
                addCell(tableDetalle, String.valueOf(producto.getCantidad()));
                addCell(tableDetalle, String.format("$%.2f", producto.getPrecioUnitario()));
                addCell(tableDetalle, String.format("$%.2f", producto.getTotal()));
            }

            document.add(tableDetalle);

            // Totales
            PdfPTable tableTotales = new PdfPTable(2);
            tableTotales.setWidthPercentage(50);
            tableTotales.setHorizontalAlignment(Element.ALIGN_RIGHT);

            addCell(tableTotales, "SUBTOTAL 12%:", String.format("$%.2f", factura.getSubtotal12()));
            addCell(tableTotales, "SUBTOTAL 0%:", String.format("$%.2f", factura.getSubtotal0()));
            addCell(tableTotales, "IVA 12%:", String.format("$%.2f", factura.getIva12()));
            addCell(tableTotales, "TOTAL:", String.format("$%.2f", factura.getTotal()));

            document.add(tableTotales);

            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }

    private void addCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBorder(0);
        table.addCell(cell);
    }

    private void addHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(220, 220, 220));
        table.addCell(cell);
    }

    private void addCell(PdfPTable table, String label, String value) {
        addCell(table, label);
        addCell(table, value);
    }
}
