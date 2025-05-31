package facturacion.model;

public class Producto {
    private String codigoPrincipal;
    private String codigoAuxiliar;
    private String descripcion;
    private double cantidad;
    private double precioUnitario;
    private double descuento;
    private double iva;

    // Constructor
    public Producto(String codigoPrincipal, String descripcion, double cantidad,
                    double precioUnitario, double descuento, double iva) {
        this.codigoPrincipal = codigoPrincipal;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.iva = iva;
    }

    // Getters y Setters
    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public String getCodigoAuxiliar() {
        return codigoAuxiliar;
    }

    public void setCodigoAuxiliar(String codigoAuxiliar) {
        this.codigoAuxiliar = codigoAuxiliar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getIva() {
        return iva;
    }

    public double getPrecioTotal() {
        return (precioUnitario * cantidad) - descuento;
    }
}