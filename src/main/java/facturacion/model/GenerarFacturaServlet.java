package facturacion.model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/GenerarFactura")
public class GenerarFacturaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 1. Obtener parámetros del formulario
            String tipoIdentificacion = request.getParameter("tipoIdentificacion");
            String identificacion = request.getParameter("identificacion");
            String razonSocial = request.getParameter("razonSocial");

            // 2. Crear cliente (ejemplo con datos fijos para prueba)
            Cliente cliente = new Cliente(
                    identificacion != null ? identificacion : "0999999999",
                    tipoIdentificacion != null ? tipoIdentificacion : "05",
                    razonSocial != null ? razonSocial : "Cliente de Prueba",
                    "Dirección del cliente",
                    "022222222",
                    "cliente@test.com"
            );

            // 3. Crear productos (ejemplo con datos fijos)
            List<Producto> productos = new ArrayList<>();
            productos.add(new Producto("001", "Producto 1", 2, 10.50, 0.0, 12.0));
            productos.add(new Producto("002", "Producto 2", 1, 25.75, 5.0, 12.0));

            // 4. Crear factura
            Factura factura = new Factura(
                    "001-001-000000001", // Número de factura
                    new Date(),           // Fecha actual
                    cliente,
                    productos,
                    "1",                  // Ambiente: 1 pruebas
                    "1"                   // Tipo emisión: 1 normal
            );

            // 5. Generar XML
            CrearXML creadorXML = new CrearXML();
            String xmlFactura = creadorXML.generarXMLFactura(factura);

            // 6. Firmar XML
            FirmaXML firmador = new FirmaXML();
            String xmlFirmado = firmador.firmarFactura(xmlFactura);

            // 7. Generar respuesta HTML
            out.println("<html>");
            out.println("<head><title>Factura Generada</title></head>");
            out.println("<body>");
            out.println("<h1>Factura electrónica generada correctamente</h1>");
            out.println("<p>Número: " + factura.getNumeroFactura() + "</p>");
            out.println("<p>Cliente: " + cliente.getRazonSocial() + "</p>");
            out.println("<p>Total: $" + factura.getTotal() + "</p>");
            out.println("<h3>XML Firmado:</h3>");
            out.println("<pre>" + xmlFirmado.substring(0, Math.min(xmlFirmado.length(), 1000)) + "...</pre>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            // Manejo de errores
            out.println("<html>");
            out.println("<head><title>Error</title></head>");
            out.println("<body>");
            out.println("<h1>Error al generar factura</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("</body>");
            out.println("</html>");
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }
}