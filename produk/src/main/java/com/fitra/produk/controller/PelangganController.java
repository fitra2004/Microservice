package com.fitra.produk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fitra.produk.service.PelangganService;
import com.fitra.produk.model.Pelanggan;
@RestController
@RequestMapping("/api/pelanggan")
public class PelangganController {

    @Autowired
    private PelangganService pelangganService;

    @GetMapping
    public ResponseEntity<?> getAllPelanggan() {
        return ResponseEntity.ok(pelangganService.getAllPelanggan());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelanggan> getPelangganById(@PathVariable Long id) {
        Pelanggan pelanggan = pelangganService.getPelangganById(id);
        return pelanggan != null ? ResponseEntity.ok(pelanggan) : ResponseEntity.notFound().build();
}
    @PostMapping
    public ResponseEntity<Pelanggan> createPelanggan(@RequestBody Pelanggan pelanggan) {
        Pelanggan createdPelanggan = pelangganService.createPelanggan(pelanggan);
        return ResponseEntity.ok(createdPelanggan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePelanggan(@PathVariable Long id) {
        pelangganService.deletePelanggan(id);
        return ResponseEntity.noContent().build();
}
}