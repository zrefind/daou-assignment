package org.kang.assignment.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import org.kang.assignment.domain.settlement.Settlement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettlementParser {

    public static List<Settlement> txtToSettlement(File file) {
        List<Settlement> settlements = new ArrayList<>();

        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] factors = line.split("\\|", -1);
                settlements.add(toSettlement(Arrays.asList(factors)));
            }

            return settlements;
        } catch (Exception e) {
            throw new RuntimeException("txt err");
        }
    }

    public static List<Settlement> csvToSettlement(File file) {
        CsvMapper mapper = new CsvMapper();
        List<Settlement> settlements = new ArrayList<>();

        try (MappingIterator<List<String>> it = mapper.readerForListOf(String.class).with(CsvParser.Feature.WRAP_AS_ARRAY).readValues(file)) {
            for (List<String> source : it.readAll()) {
                settlements.add(toSettlement(source));
            }

            return settlements;
        } catch (Exception e) {
            throw new RuntimeException("ERROR");
        }
    }

    private static Settlement toSettlement(List<String> source) {
        return Settlement.of(
                source.get(0),
                source.get(1),
                source.get(2),
                source.get(3),
                source.get(4),
                source.get(5)
        );
    }

}
