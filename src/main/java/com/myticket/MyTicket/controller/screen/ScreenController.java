package com.myticket.MyTicket.controller.screen;

import com.myticket.MyTicket.model.Screen;
import com.myticket.MyTicket.service.screen.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {
    @Autowired
    private ScreenService screenService;

    @PostMapping
    public ResponseEntity<Screen> addScreen(@RequestBody Screen screen) {
        Screen saved = screenService.createScreen(screen);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<Screen>> getScreensByTheatre(@PathVariable Long theatreId) {
        return ResponseEntity.ok(screenService.getScreensByTheatreId(theatreId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screen> getScreenById(@PathVariable Long id) {
        return screenService.getScreenById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Screen>> getAllScreens() {
        return ResponseEntity.ok(screenService.getAllScreens());
    }
}
