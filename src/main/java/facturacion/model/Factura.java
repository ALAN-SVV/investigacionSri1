package facturacion.model;

import java.util.Date;
import java.util.List;

public class Factura {
    private String numeroComprobante;
    private Date fechaEmision;
    private Cliente cliente;
    private List<Producto> productos;
    private String ambiente;
    private String tipoEmision;
    private String razonSocial;
    private String nombreComercial;
    private String ruc;
    private String codDoc;
    private String estab;
    private String ptoEmi;
    private String dirEstablecimiento;
    private double subtotal12;
    private double subtotal0;
    private double iva12;
    private double total;

    // Constructor
    public Factura(String numeroComprobante, Date fechaEmision, Cliente cliente,
                   List<Producto> productos, String ambiente, String tipoEmision,
                   String razonSocial, String nombreComercial, String ruc,
                   String codDoc, String estab, String ptoEmi,
                   String dirEstablecimiento) {
        this.numeroComprobante = numeroComprobante;
        this.fechaEmision = fechaEmision;
        this.cliente = cliente;
        this.productos = productos;
        this.ambiente = ambiente;
        this.tipoEmision = tipoEmision;
        this.razonSocial = razonSocial;
        this.nombreComercial = nombreComercial;
        this.ruc = ruc;
        this.codDoc = codDoc;
        this.estab = estab;
        this.ptoEmi = ptoEmi;
        this.dirEstablecimiento = dirEstablecimiento;
        calcularTotales();
    }

    private void calcularTotales() {
        this.subtotal12 = 0;
        this.subtotal0 = 0;
        this.iva12 = 0;

        for (Producto producto : productos) {
            if (producto.getIva() == 12) {
                this.subtotal12 += producto.getTotal();
                this.iva12 += producto.getTotal() * 0.12;
            } else {
                this.subtotal0 += producto.getTotal();
            }
        }

        this.total = this.subtotal12 + this.subtotal0 + this.iva12;
    }

    // Getters y Setters
    public String getNumeroComprobante() {
        return numeroComprobante;
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

    public String getAmbiente() {
        return ambiente;
    }

    public String getTipoEmision() {
        return tipoEmision;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getRuc() {
        return ruc;
    }

    public String getCodDoc() {
        return codDoc;
    }

    public String getEstab() {
        return estab;
    }

    public String getPtoEmi() {
        return ptoEmi;
    }

    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public double getSubtotal12() {
        return subtotal12;
    }

    public double getSubtotal0() {
        return subtotal0;
    }

    public double getIva12() {
        return iva12;
    }

    public double getTotal() {
        return total;
    }

    // Setters solo para campos que podrían cambiar
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        calcularTotales();
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
