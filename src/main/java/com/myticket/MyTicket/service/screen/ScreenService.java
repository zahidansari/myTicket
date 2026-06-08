package com.myticket.MyTicket.service.screen;

import com.myticket.MyTicket.model.Screen;
import com.myticket.MyTicket.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreenService {
    @Autowired
    private ScreenRepository screenRepository;

    public Screen createScreen(Screen screen) {
        return screenRepository.save(screen);
    }

    public List<Screen> getScreensByTheatreId(Long theatreId) {
        return screenRepository.findByTheatreId(theatreId);
    }

    public Optional<Screen> getScreenById(Long id) {
        return screenRepository.findById(id);
    }

    public List<Screen> getAllScreens() {
        return screenRepository.findAll();
    }
}
