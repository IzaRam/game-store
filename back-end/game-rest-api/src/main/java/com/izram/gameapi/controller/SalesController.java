package com.izram.gameapi.controller;

import com.izram.gameapi.model.Sale;
import com.izram.gameapi.repository.SaleRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/sales")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SalesController {

    @Autowired
    SaleRepository saleRepository;

    @GetMapping("/all")
    public @ResponseBody Iterable<Sale> findAllSales() {
        return saleRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Sale findSaleById(@PathVariable(value = "id") int id) {
        Optional<Sale> saleOptional = saleRepository.findById(id);
        return saleOptional.orElse(null);
    }

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<Object> addNewSale(@RequestBody Sale sale) {
        if (sale.getGameList().isEmpty()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Response", "Error: Game List Empty");
            return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.BAD_REQUEST);
        }
        saleRepository.save(sale);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Response", "Sale Saved");
        return new ResponseEntity<>(jsonObject.toMap(), HttpStatus.OK);
    }

    @DeleteMapping("/del/{id}")
    public @ResponseBody String deleteSaleById(@PathVariable(value = "id") int id) {
        saleRepository.deleteById(id);
        return "Deleted";
    }

}
