package creatip.restaurant.web.rest;

import creatip.restaurant.enumeration.CommonEnum.CategoryStatus;
import creatip.restaurant.enumeration.CommonEnum.ProductStatus;
import creatip.restaurant.enumeration.CommonEnum.TableStatus;
import creatip.restaurant.service.dto.PickListDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/setup")
public class LoadSetupResource {

    @GetMapping("/picklist")
    public PickListDTO loadPicklist() {
        PickListDTO pickList = new PickListDTO();

        for (TableStatus enumData : TableStatus.values()) {
            pickList.addTableStatus((int) enumData.value, enumData.description);
        }

        for (CategoryStatus enumData : CategoryStatus.values()) {
            pickList.addCatetoryStatus((int) enumData.value, enumData.description);
        }

        for (ProductStatus enumData : ProductStatus.values()) {
            pickList.addProductStatus((int) enumData.value, enumData.description);
        }

        return pickList;
    }
}
