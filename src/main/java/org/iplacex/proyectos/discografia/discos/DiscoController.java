package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {


    @Autowired
    private IDiscoRepository iDiscoRepository;

    @Autowired
    private IArtistaRepository iArtistaRepository;

    @CrossOrigin(methods = RequestMethod.POST)
    @PostMapping(value="/disco",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> HandlePostDiscoRequest(@RequestBody Disco disco){
        if(!disco.validateData()){
            return ResponseEntity.badRequest().build();
        }
        boolean existe = iArtistaRepository.existsById(disco.idArtista);
        if(existe){
            Disco discoInsert = iDiscoRepository.insert(disco);
            return ResponseEntity.ok(discoInsert);
        }else{
            return ResponseEntity.notFound().build();
        }
        
    }

    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest(){
        List<Disco> list = iDiscoRepository.findAll();
        if(list.isEmpty()){
            return ResponseEntity.noContent().build(); 
        }else{
            return ResponseEntity.ok(list);
        }
    }

    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/disco/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object HandleGetDiscoRequest(@PathVariable("id") String id){
        if(id == null || id.trim().isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<Disco> discoFound = iDiscoRepository.findById(id);
        if(discoFound.isPresent()){
            return ResponseEntity.ok(discoFound.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/artista/{id}/discos",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String id){
        if(id == null || id.trim().isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        List<Disco> list = iDiscoRepository.findDiscosByIdArtista(id);
        if(list.isEmpty()){
            return ResponseEntity.noContent().build(); 
        }else{
            return ResponseEntity.ok(list);
        }
    }

    
}
