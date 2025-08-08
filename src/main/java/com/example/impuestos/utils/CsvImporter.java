package com.example.impuestos.utils;

import com.example.impuestos.model.Impuesto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvImporter {

    public static List<Impuesto> parseCsv(MultipartFile file) throws Exception {
    List<Impuesto> impuestos = new ArrayList<>();

    try (
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (CSVRecord record : csvParser) {
            Impuesto imp = new Impuesto();
            imp.setSticker(Long.parseLong(record.get(0)));
            imp.setFechaMovimiento(LocalDate.parse(record.get(1), formatter));
            imp.setFechaRecaudo(LocalDate.parse(record.get(2), formatter));
            imp.setTipoHorario(record.get(3));
            imp.setNroId(new BigInteger(record.get(4)));
            imp.setNroForm(new BigDecimal(record.get(5)));
            imp.setValor(new BigInteger(record.get(6)));
            impuestos.add(imp);
        }
    }

    return impuestos;
}
}
