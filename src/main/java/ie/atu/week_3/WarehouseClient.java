package ie.atu.week_3;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;


@Component
@FeignClient(name = "inventory-service", url = "http://localhost:8081")
public interface WarehouseClient {
    @PostMapping("/api/warehouse/add")
    String storeProduct(Product product);
}
