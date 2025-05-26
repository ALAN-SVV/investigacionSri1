package facturacion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/generarFactura")
public class GenerarFacturaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener datos del formulario
        String ruc = request.getParameter("ruc");
        String razonSocial = request.getParameter("razonSocial");
        // ... otros parámetros

        // Crear factura
        Factura factura = new Factura(ruc, razonSocial /*, otros datos */);

        // Generar XML
        CrearXML crearXML = new CrearXML();
        String xmlContent = crearXML.generarXML(factura);

        // Firmar XML
        FirmarXML firmarXML = new FirmarXML();
        String xmlFirmado = firmarXML.firmarDocumento(xmlContent);

        // Generar PDF
        FacturaPDF facturaPDF = new FacturaPDF();
        byte[] pdfBytes = facturaPDF.generarPDF(factura);

        // Enviar al SRI (opcional)
        EnvioSRI envioSRI = new EnvioSRI();
        String respuestaSRI = envioSRI.enviarFactura(xmlFirmado);

        // Guardar archivos
        String nombreArchivo = "FAC-" + factura.getNumeroComprobante();
        FileUtil.guardarArchivo(xmlFirmado, nombreArchivo + ".xml");
        FileUtil.guardarArchivo(pdfBytes, nombreArchivo + ".pdf");

        // Preparar respuesta
        response.setContentType("application/json");
        response.getWriter().write("{\"status\":\"success\", \"xml\":\"" + nombreArchivo + ".xml\", \"pdf\":\"" + nombreArchivo + ".pdf\"}");
    }
}
