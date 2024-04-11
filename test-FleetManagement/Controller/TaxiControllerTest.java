
package Controller;

import FleetManagement.Controller.TaxiController;
import FleetManagement.Entity.TaxiEntity;
import FleetManagement.Repository.TaxisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class TaxiControllerTest {

    @InjectMocks
    private TaxiController taxiController;

    @Mock
    private TaxisRepository taxisRepository;

    @Test
    void testGetAllTaxis() {
        TaxiEntity taxi1 = new TaxiEntity(1, "abcde");
        TaxiEntity taxi2 = new TaxiEntity(2, "fghij");
        List<TaxiEntity> taxisList = Arrays.asList(taxi1, taxi2);
        Page<TaxiEntity> taxisPage = new PageImpl<>(taxisList);

        doReturn(taxisPage).when(taxisRepository).findAll(PageRequest.of(1, 10));

        Page<TaxiEntity> result = taxiController.getAllTaxis(1, 10);

        assertEquals(taxisPage, result);
    }
}




