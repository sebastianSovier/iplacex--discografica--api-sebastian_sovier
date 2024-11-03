package org.iplacex.proyectos.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository iArtistaRepository;


    @CrossOrigin(methods = RequestMethod.POST)
    @PostMapping(value="/artista",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista){
        if(!artista.validateData()){
            return ResponseEntity.badRequest().build();
        }
        Artista artistaSave = iArtistaRepository.insert(artista);
        if(artistaSave._id != null){
            return ResponseEntity.ok(artistaSave);
        }else{
            return ResponseEntity.badRequest().build();
        }
        
    }
    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest(){
         List<Artista> list = iArtistaRepository.findAll();
         if(list.isEmpty()){
            return ResponseEntity.noContent().build();
         }else{
            return ResponseEntity.ok(list);
         }
        
        
    }
    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/artista/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable("id") String id){
        if(id == null || id.trim().isEmpty()){
            return ResponseEntity.badRequest().build(); 
        }
        Optional<Artista> getArtista = iArtistaRepository.findById(id);
        if(getArtista.isPresent()){
            return ResponseEntity.ok(getArtista.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin(methods = RequestMethod.PUT)
    @PutMapping(value = "/artista/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(@PathVariable("id") String id,@RequestBody Artista artista){
        if(id == null || id.trim().isEmpty() || !artista.validateData()){
            return ResponseEntity.badRequest().build();
        }
        boolean existe = iArtistaRepository.existsById(id);
        if(existe){
            Artista saveArtista = iArtistaRepository.save(artista);
            return ResponseEntity.ok(saveArtista);
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }
    @CrossOrigin(methods = RequestMethod.DELETE)
    @DeleteMapping(value = "/artista/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> HandleDeleteArtistaRequest(@PathVariable("id") String id){
        if(id == null || id.trim().isEmpty()){
            return ResponseEntity.badRequest().build(); 
        }
        boolean existe = iArtistaRepository.existsById(id);
        if(existe){
        iArtistaRepository.deleteById(id);
        return ResponseEntity.ok("registro eliminado correctamente");
        }else{
           return ResponseEntity.notFound().build();
        }
    }
    
}
