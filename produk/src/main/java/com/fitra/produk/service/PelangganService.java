package com.fitra.produk.service;

import org.springframework.stereotype.Service;
import com.fitra.produk.model.Pelanggan;
import com.fitra.produk.repository.PelangganRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class PelangganService {

@Autowired
private PelangganRepository pelangganRepository;

public List<Pelanggan> getAllPelanggan() {
    return pelangganRepository.findAll();
}

public Pelanggan createPelanggan(Pelanggan pelanggan) {
    return pelangganRepository.save(pelanggan);

}
public Pelanggan getPelangganById(Long id) {
    return pelangganRepository.findById(id).orElse(null);
}

public void deletePelanggan(Long id) {
    pelangganRepository.deleteById(id);
}
}
