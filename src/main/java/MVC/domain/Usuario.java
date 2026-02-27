package MVC.domain;

public class Usuario {
    private String numeroTarjeta;
    private String bancoEmisor;
    private String ciudad;

    public Usuario(String numeroTarjeta, String bancoEmisor, String ciudad) {
        this.numeroTarjeta = numeroTarjeta;
        this.bancoEmisor = bancoEmisor;
        this.ciudad = ciudad;
    }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public String getBancoEmisor() { return bancoEmisor; }
    public void setBancoEmisor(String bancoEmisor) { this.bancoEmisor = bancoEmisor; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
}