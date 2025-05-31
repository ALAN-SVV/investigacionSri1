package facturacion.model;

import java.util.Date;
import java.util.List;

public class Factura {
    private String numeroFactura;
    private Date fechaEmision;
    private Cliente cliente;
    private List<Producto> productos;
    private double subtotal;
    private double iva;
    private double total;
    private String estado;
    private String ambiente; // 1: Pruebas, 2: Producción
    private String tipoEmision; // 1: Normal, 2: Contingencia

    // Constructor
    public Factura(String numeroFactura, Date fechaEmision, Cliente cliente,
                   List<Producto> productos, String ambiente, String tipoEmision) {
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.cliente = cliente;
        this.productos = productos;
        this.ambiente = ambiente;
        this.tipoEmision = tipoEmision;
        calcularTotales();
    }

    private void calcularTotales() {
        subtotal = productos.stream().mapToDouble(p -> p.getPrecioUnitario() * p.getCantidad()).sum();
        iva = subtotal * 0.12; // IVA 12% en Ecuador
        total = subtotal + iva;
    }

    // Getters y Setters
    public String getNumeroFactura() {
        return numeroFactura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getIva() {
        return iva;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public String getTipoEmision() {
        return tipoEmision;
    }
}