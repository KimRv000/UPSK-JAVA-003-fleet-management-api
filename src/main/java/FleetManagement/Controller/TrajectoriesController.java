package FleetManagement.Controller;

import FleetManagement.Entity.TrajectoriesEntity;
import FleetManagement.Repository.TrajectoriesRepository;
import FleetManagement.Service.TrajectoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fm")
public class TrajectoriesController {


    @Autowired
    private final TrajectoriesRepository trajectoriesRepository;
    private final TrajectoriesService trajectoriesService;

    public TrajectoriesController(TrajectoriesRepository trajectoriesRepository, TrajectoriesService trajectoriesService) {
        this.trajectoriesRepository = trajectoriesRepository;
        this.trajectoriesService = trajectoriesService;
    }

    @GetMapping("/trajectories")
    public Page<TrajectoriesEntity> getAllTrajectories(@RequestParam(defaultValue = "1") int page) {
        int size = 100;
        PageRequest pageRequest = PageRequest.of(page, size);
        return trajectoriesRepository.findAll(pageRequest);
    }

    @GetMapping("/trajectories/{taxiId}")
    public ResponseEntity<List<Map<String, Object>>> getTrajectoriesInfoByTaxiId(
            @PathVariable int taxiId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Map<String, Object>> trajectories;

        if (date != null) {
            trajectories = trajectoriesService.findTrajectoriesByTaxiIdAndDate(taxiId, date);
        } else {
            trajectories = trajectoriesService.findTrajectoryInfoByTaxiId(taxiId);
        }

        if (!trajectories.isEmpty()) {
            return ResponseEntity.ok(trajectories);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/last-trajectories")
    public ResponseEntity<?> getLatestLocationsPerTaxi(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Map<String, Object>> latestLocationsPage = trajectoriesRepository.findLatestLocationPerTaxiPaged(pageRequest);

        if (!latestLocationsPage.isEmpty()) {
            return ResponseEntity.ok(latestLocationsPage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
