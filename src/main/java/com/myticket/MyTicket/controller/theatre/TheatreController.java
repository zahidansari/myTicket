package com.myticket.MyTicket.controller.theatre;

import com.myticket.MyTicket.model.Theatre;
import com.myticket.MyTicket.service.theatre.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {
    @Autowired
    private TheatreService theatreService;

    @PostMapping("/register")
    public ResponseEntity<Theatre> registerTheatre(@RequestBody Theatre theatre) {
        Theatre saved = theatreService.createTheatre(theatre);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Theatre>> getAllTheatres() {
        List<Theatre> theatres = theatreService.getAllTheatres();
        return ResponseEntity.ok(theatres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theatre> getTheatreById(@PathVariable Long id) {
        return theatreService.getTheatreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
