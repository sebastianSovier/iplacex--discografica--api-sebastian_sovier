package org.iplacex.proyectos.discografia.discos;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "discos")
public class Disco {
       @Id
    public String _id;
    
    public String idArtista;
    public List<String> canciones;
    public int anioLanzamiento;
    public String nombre;



    public boolean validateData(){
        if((_id != null && !_id.trim().isEmpty()) && (idArtista != null && !idArtista.trim().isEmpty()) && canciones != null && !canciones.isEmpty() && (anioLanzamiento > 0) && nombre != null && !nombre.trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
}
