package facturacion.model;

public class Cliente {
    private String identificacion;
    private String tipoIdentificacion;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;

    // Constructor
    public Cliente(String identificacion, String tipoIdentificacion,
                   String nombre, String direccion, String telefono,
                   String email) {
        this.identificacion = identificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y Setters
    public String getIdentificacion() {
        return identificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}