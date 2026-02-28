package MVC.dto;

public class UsuarioDTO {
    private final String numeroTarjeta;
    private final String bancoEmisor;
    private final String ciudad;

    public UsuarioDTO(String numeroTarjeta, String bancoEmisor, String ciudad) {
        this.numeroTarjeta = numeroTarjeta;
        this.bancoEmisor = bancoEmisor;
        this.ciudad = ciudad;
    }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public String getBancoEmisor() { return bancoEmisor; }
    public String getCiudad() { return ciudad; }
}
