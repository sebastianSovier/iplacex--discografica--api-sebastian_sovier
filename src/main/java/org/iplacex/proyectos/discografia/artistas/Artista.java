package org.iplacex.proyectos.discografia.artistas;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("artistas")
public class Artista {

    @Id
    public String _id;
    
    public String nombre;
    public List<String> estilos;
    public int anioFundacion;
    public boolean estaActivo;
    

    public boolean validateData(){
        if((_id != null && !_id.trim().isEmpty()) && (estaActivo == true || estaActivo == false) && estilos != null && !estilos.isEmpty() && (anioFundacion > 0) && nombre != null && !nombre.trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
