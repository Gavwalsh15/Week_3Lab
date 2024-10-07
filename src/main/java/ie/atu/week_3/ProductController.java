package ie.atu.week_3;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    private final WarehouseClient warehouseClient;

    @Autowired
    public ProductController(ProductService productService, WarehouseClient warehouseClient) {
        this.productService = productService;
        this.warehouseClient = warehouseClient;
    }


    @GetMapping("/get")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public String addProduct(@Valid @RequestBody Product newProduct) {
        productService.addProduct(newProduct);
        String responseFromWarehouse = warehouseClient.storeProduct(newProduct);
        return "Product successfully created\n" + responseFromWarehouse;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editProduct(@Valid @PathVariable long id, @RequestBody Product changedProduct) {
        boolean status = productService.editProduct(id, changedProduct);

        if(status){
            return new ResponseEntity<>("Product successfully Edited\n", HttpStatus.CREATED);
        }else
            return new ResponseEntity<>("Product not found\n", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> editProduct(@PathVariable long id) {
        boolean status = productService.deleteProduct(id);

        if(status){
            return new ResponseEntity<>("Product successfully Deleted\n", HttpStatus.CREATED);
        }else
            return new ResponseEntity<>("Product not found\n", HttpStatus.BAD_REQUEST);
    }


}
