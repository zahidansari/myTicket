package com.myticket.MyTicket.controller.showtime;

import com.myticket.MyTicket.model.Showtime;
import com.myticket.MyTicket.service.showtime.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {
    @Autowired
    private ShowtimeService showtimeService;

    @PostMapping
    public ResponseEntity<Showtime> addShowtime(@RequestBody Showtime showtime) {
        Showtime saved = showtimeService.createShowtime(showtime);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Showtime>> getAllShowtimes() {
        List<Showtime> showtimes = showtimeService.getAllShowtimes();
        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        return showtimeService.getShowtimeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Showtime>> getShowtimesByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showtimeService.getShowtimesByMovieId(movieId));
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<Showtime>> getShowtimesByTheatre(@PathVariable Long theatreId) {
        return ResponseEntity.ok(showtimeService.getShowtimesByTheatreId(theatreId));
    }
}
