package com.example.impuestos.controller;

import com.example.impuestos.model.Impuesto;
import com.example.impuestos.service.ImpuestoService;
import com.example.impuestos.utils.CsvImporter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/impuestos")
@RequiredArgsConstructor
public class ImpuestoController {

    private final ImpuestoService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        try {
            List<Impuesto> lista = CsvImporter.parseCsv(file);
            service.guardarMasivo(lista);
            return ResponseEntity.ok("Archivo cargado exitosamente: " + lista.size() + " registros.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al cargar el archivo: " + e.getMessage());
        }
    }

    @GetMapping("/fecha")
    public List<Impuesto> consultarPorFecha(
            @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
        return service.buscarPorFecha(fecha);
    }

    @GetMapping("/resumen")
    public ResponseEntity<?> resumen(@RequestParam("horario") String tipo) {
        return ResponseEntity.ok(service.resumenPorHorario(tipo));
    }

    @GetMapping("/sticker/{id}")
    public ResponseEntity<?> porSticker(@PathVariable Long id) {
        return service.buscarPorSticker(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportarExcel() {
        var stream = service.exportarExcel();
        if (stream == null) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=impuestos.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(stream.toByteArray());
    }
}
