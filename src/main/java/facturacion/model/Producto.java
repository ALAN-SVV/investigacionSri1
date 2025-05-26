package facturacion.model;

public class Producto {
    private String codigoPrincipal;
    private String codigoAuxiliar;
    private String descripcion;
    private double cantidad;
    private double precioUnitario;
    private double descuento;
    private int iva;
    private String unidadMedida;

    // Constructor
    public Producto(String codigoPrincipal, String descripcion,
                    double cantidad, double precioUnitario, int iva,
                    String unidadMedida) {
        this(codigoPrincipal, null, descripcion, cantidad, precioUnitario, 0, iva, unidadMedida);
    }

    public Producto(String codigoPrincipal, String codigoAuxiliar,
                    String descripcion, double cantidad,
                    double precioUnitario, double descuento,
                    int iva, String unidadMedida) {
        this.codigoPrincipal = codigoPrincipal;
        this.codigoAuxiliar = codigoAuxiliar;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.iva = iva;
        this.unidadMedida = unidadMedida;
    }

    // Método para calcular el total por producto
    public double getTotal() {
        return (precioUnitario * cantidad) - descuento;
    }

    // Getters y Setters
    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public String getCodigoAuxiliar() {
        return codigoAuxiliar;
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

    public int getIva() {
        return iva;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}