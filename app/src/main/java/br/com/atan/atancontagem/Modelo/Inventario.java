package br.com.atan.atancontagem.Modelo;

public class Inventario {
    private String num_inventario;
    private String loja;
    private String data;
    private String status;
    private String enviado;
    private String contagem;
    private String observarcao;

    public Inventario(String num_inventario, String loja, String data, String status, String enviado, String observarcao) {
        this.num_inventario = num_inventario;
        this.loja = loja;
        this.data = data;
        this.status = status;
        this.enviado = enviado;
        this.observarcao = observarcao;
    }

    public Inventario() {
    }

    public String getNum_inventario() {
        return num_inventario;
    }

    public void setNum_inventario(String num_inventario) {
        this.num_inventario = num_inventario;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }

    public String getContagem() {
        return contagem;
    }

    public void setContagem(String contagem) {
        this.contagem = contagem;
    }

    public String getObservarcao() {
        return observarcao;
    }

    public void setObservarcao(String observarcao) {
        this.observarcao = observarcao;
    }
}
