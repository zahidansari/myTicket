package com.myticket.MyTicket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myticket.MyTicket.model.Movie;
import com.myticket.MyTicket.model.Screen;
import com.myticket.MyTicket.model.Showtime;
import com.myticket.MyTicket.model.Theatre;
import com.myticket.MyTicket.repository.MovieRepository;
import com.myticket.MyTicket.repository.ScreenRepository;
import com.myticket.MyTicket.repository.ShowtimeRepository;
import com.myticket.MyTicket.repository.TheatreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private final ShowtimeRepository showtimeRepository;
    private final ObjectMapper objectMapper;

    public DataInitializer(MovieRepository movieRepository,
                          TheatreRepository theatreRepository,
                          ScreenRepository screenRepository,
                          ShowtimeRepository showtimeRepository,
                          ObjectMapper objectMapper) {
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
        this.showtimeRepository = showtimeRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        if (theatreRepository.count() > 0 || movieRepository.count() > 0 || showtimeRepository.count() > 0) {
            return;
        }

        try {
            SeedData seedData = loadSeedData();
            List<Theatre> savedTheatres = theatreRepository.saveAll(seedData.getTheatres());

            Map<String, Long> theatreIdByName = new HashMap<>();
            for (Theatre theatre : savedTheatres) {
                theatreIdByName.put(theatre.getName(), theatre.getId());
            }

            List<ScreenSeed> screenSeeds = seedData.getScreens();
            List<Screen> screensToSave = new java.util.ArrayList<>();
            for (ScreenSeed screenSeed : screenSeeds) {
                Screen screen = new Screen();
                screen.setTheatreId(theatreIdByName.get(screenSeed.getTheatreName()));
                screen.setName(screenSeed.getName());
                screen.setTotalSeats(screenSeed.getTotalSeats());
                screensToSave.add(screen);
            }
            screenRepository.saveAll(screensToSave);

            List<Movie> savedMovies = movieRepository.saveAll(seedData.getMovies());

            Map<String, Long> movieIdByTitle = new HashMap<>();
            for (Movie movie : savedMovies) {
                movieIdByTitle.put(movie.getTitle(), movie.getId());
            }

            List<ShowtimeSeed> showtimeSeeds = seedData.getShowtimes();
            List<Showtime> showtimesToSave = new java.util.ArrayList<>();
            for (ShowtimeSeed showtimeSeed : showtimeSeeds) {
                Showtime showtime = new Showtime();
                showtime.setMovieId(movieIdByTitle.get(showtimeSeed.getMovieTitle()));
                showtime.setTheatreId(theatreIdByName.get(showtimeSeed.getTheatreName()));
                showtime.setShowDate(showtimeSeed.getShowDate());
                showtime.setShowTime(showtimeSeed.getShowTime());
                showtime.setPrice(showtimeSeed.getPrice());
                showtimesToSave.add(showtime);
            }
            showtimeRepository.saveAll(showtimesToSave);

            System.out.println("Seed data loaded from external file: " + seedData.getTheatres().size() + " theatres, "
                    + seedData.getScreens().size() + " screens, " + seedData.getMovies().size() + " movies, "
                    + seedData.getShowtimes().size() + " showtimes");
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load startup catalog data", e);
        }
    }

    private SeedData loadSeedData() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/catalog-seed.json");
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, SeedData.class);
        }
    }

    public static class SeedData {
        private List<Theatre> theatres;
        private List<ScreenSeed> screens;
        private List<Movie> movies;
        private List<ShowtimeSeed> showtimes;

        public List<Theatre> getTheatres() {
            return theatres;
        }

        public void setTheatres(List<Theatre> theatres) {
            this.theatres = theatres;
        }

        public List<ScreenSeed> getScreens() {
            return screens;
        }

        public void setScreens(List<ScreenSeed> screens) {
            this.screens = screens;
        }

        public List<Movie> getMovies() {
            return movies;
        }

        public void setMovies(List<Movie> movies) {
            this.movies = movies;
        }

        public List<ShowtimeSeed> getShowtimes() {
            return showtimes;
        }

        public void setShowtimes(List<ShowtimeSeed> showtimes) {
            this.showtimes = showtimes;
        }
    }

    public static class ScreenSeed {
        private String theatreName;
        private String name;
        private Integer totalSeats;

        public String getTheatreName() {
            return theatreName;
        }

        public void setTheatreName(String theatreName) {
            this.theatreName = theatreName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTotalSeats() {
            return totalSeats;
        }

        public void setTotalSeats(Integer totalSeats) {
            this.totalSeats = totalSeats;
        }
    }

    public static class ShowtimeSeed {
        private String movieTitle;
        private String theatreName;
        private String showDate;
        private String showTime;
        private Double price;

        public String getMovieTitle() {
            return movieTitle;
        }

        public void setMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
        }

        public String getTheatreName() {
            return theatreName;
        }

        public void setTheatreName(String theatreName) {
            this.theatreName = theatreName;
        }

        public String getShowDate() {
            return showDate;
        }

        public void setShowDate(String showDate) {
            this.showDate = showDate;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}
