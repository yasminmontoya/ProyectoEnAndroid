package com.example.misastreria.entidades;

import java.util.Date;

public class Prenda {

    private Integer codigo;
    private Integer codigo_cliente;
    private String tipo_prenda;
    private String fecha_ingreso;
    private String fecha_salida;
    private String color;
    private String marca;
    private Integer cantidad;

    public Prenda(Integer codigo, Integer codigo_cliente, String tipo_prenda, String fecha_ingreso, String fecha_salida, String color, String marca, Integer cantidad) {
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.tipo_prenda = tipo_prenda;
        this.fecha_ingreso = fecha_ingreso;
        this.fecha_salida = fecha_salida;
        this.color = color;
        this.marca = marca;
        this.cantidad = cantidad;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(Integer codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getTipo_prenda() {
        return tipo_prenda;
    }

    public void setTipo_prenda(String tipo_prenda) {
        this.tipo_prenda = tipo_prenda;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
