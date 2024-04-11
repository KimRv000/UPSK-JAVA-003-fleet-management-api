package Controller;

import FleetManagement.Controller.TrajectoriesController;
import FleetManagement.Entity.TrajectoriesEntity;
import FleetManagement.Repository.TrajectoriesRepository;
import FleetManagement.Service.TrajectoriesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TrajectoriesControllerTest {

    @InjectMocks
    private TrajectoriesController trajectoriesController;

    @Mock
    private TrajectoriesService trajectoriesService;

    @Mock
    private TrajectoriesRepository trajectoriesRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllTrajectories() {
        TrajectoriesEntity entity1 = new TrajectoriesEntity();
        entity1.setIdTrajectory(7956);
        entity1.setTaxiId(1);
        entity1.setDate(LocalDateTime.parse("1970-01-01T11:10:16"));
        entity1.setLatitude(116.23249);
        entity1.setLongitude(41.91784);

        TrajectoriesEntity entity2 = new TrajectoriesEntity();
        entity2.setIdTrajectory(7957);
        entity2.setTaxiId(2);
        entity2.setDate(LocalDateTime.parse("1970-01-01T10:34:38"));
        entity2.setLatitude(117.12528);
        entity2.setLongitude(40.14688);

        List<TrajectoriesEntity> entitiesList = Arrays.asList(entity1, entity2);
        Page<TrajectoriesEntity> entitiesPage = new PageImpl<>(entitiesList);

        when(trajectoriesRepository.findAll(any(Pageable.class))).thenReturn(entitiesPage);

        Page<TrajectoriesEntity> result = trajectoriesController.getAllTrajectories(1);

        assertEquals(entitiesPage, result);
    }

    @Test
    public void testGetTrajectoriesInfoByTaxiId() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(trajectoriesController).build();

        int taxiId = 1;
        LocalDate date = LocalDate.now();

        List<Map<String, Object>> mockData = Arrays.asList(
                Map.of("idTrajectory", 124, "taxiId", 6418, "date", "1970-01-01T14:09:49", "latitude", 116.31597, "longitude", 39.96385)
        );
        when(trajectoriesService.findTrajectoriesByTaxiIdAndDate(any(Integer.class), any(LocalDate.class)))
                .thenReturn(mockData);

        mockMvc.perform(get("/fm/trajectories/{taxiId}", taxiId)
                        .param("date", date.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].idTrajectory").value(124))
                .andExpect(jsonPath("$[0].taxiId").value(6418))
                .andExpect(jsonPath("$[0].date").value("1970-01-01T14:09:49"))
                .andExpect(jsonPath("$[0].latitude").value(116.31597))
                .andExpect(jsonPath("$[0].longitude").value(39.96385));
    }



    @Test
    public void testGetLatestLocationsPerTaxi() throws Exception {

        List<Map<String, Object>> testData = Arrays.asList(
                Map.of("date", LocalDateTime.parse("1970-01-01T12:10:16"),
                        "latitude", 116.23249,
                        "plate", "BAJW-7971",
                        "id", 7957,
                        "longitude", 39.99704),
                Map.of("date", LocalDateTime.parse("1970-01-01T11:10:16"),
                        "latitude", 116.23249,
                        "plate", "BAJW-7971",
                        "id", 7956,
                        "longitude", 41.91784)
        );

        when(trajectoriesRepository.findLatestLocationPerTaxiPaged(any()))
                .thenReturn(new PageImpl<>(testData));

        mockMvc.perform(get("/last-trajectories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(testData.size()))
                .andExpect(jsonPath("$.content[0].date").value("1970-01-01T12:10:16"))
                .andExpect(jsonPath("$.content[0].latitude").value(116.23249))
                .andExpect(jsonPath("$.content[0].plate").value("BAJW-7971"))
                .andExpect(jsonPath("$.content[0].id").value(7957))
                .andExpect(jsonPath("$.content[0].longitude").value(39.99704));
    }
}
