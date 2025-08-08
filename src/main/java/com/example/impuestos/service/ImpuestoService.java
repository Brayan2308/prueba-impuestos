package com.example.impuestos.service;

import com.example.impuestos.model.Impuesto;
import com.example.impuestos.repository.ImpuestoRepository;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImpuestoService {

    private final ImpuestoRepository repo;

    public List<Impuesto> buscarPorFecha(LocalDate fecha) {
        return repo.findByFechaMovimiento(fecha);
    }

    public List<Object[]> resumenPorHorario(String tipoHorario) {
        return repo.resumenPorHorario(tipoHorario);
    }

    public Optional<Impuesto> buscarPorSticker(Long sticker) {
        return repo.findById(sticker);
    }

    public List<Impuesto> guardarMasivo(List<Impuesto> lista) {
        return repo.saveAll(lista);
    }

    public List<Impuesto> listarTodos() {
        return repo.findAll();
    }

     public ByteArrayOutputStream exportarExcel() {
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        List<Impuesto> impuestos = repo.findAll();
        Sheet sheet = workbook.createSheet("Impuestos");

        Row header = sheet.createRow(0);
        String[] columnas = {"Sticker", "Fecha Movimiento", "Fecha Recaudo", "Tipo Horario", "Nro ID", "Nro Form", "Valor"};
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int fila = 1;
        for (Impuesto imp : impuestos) {
            Row row = sheet.createRow(fila++);
            row.createCell(0).setCellValue(imp.getSticker());
            row.createCell(1).setCellValue(imp.getFechaMovimiento().toString());
            row.createCell(2).setCellValue(imp.getFechaRecaudo().toString());
            row.createCell(3).setCellValue(imp.getTipoHorario());
            row.createCell(4).setCellValue(imp.getNroId().toString());
            row.createCell(5).setCellValue(imp.getNroForm().toString());
            row.createCell(6).setCellValue(imp.getValor().doubleValue());
        }

        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(out);
        return out;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
}
