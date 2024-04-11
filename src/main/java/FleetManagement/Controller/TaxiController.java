package FleetManagement.Controller;

import FleetManagement.Entity.TaxiEntity;
import FleetManagement.Repository.TaxisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/fm")
public class TaxiController {

    @Autowired
    private final TaxisRepository taxisRepository;
    public TaxiController(TaxisRepository taxisRepository) {
        this.taxisRepository = taxisRepository;
    }

    @GetMapping("/taxis")
    public Page<TaxiEntity> getAllTaxis(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return taxisRepository.findAll(pageRequest);
    }



}

