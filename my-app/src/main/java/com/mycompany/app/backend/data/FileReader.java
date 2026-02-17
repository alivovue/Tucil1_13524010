package com.mycompany.app.backend.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReader {
    public static ColorObject loadColorBoard(String filepath) throws Exception {
        List<String> line = Files.readAllLines(Path.of(filepath));
        line.removeIf(s -> s.trim().isEmpty());

        if (line.size() == 0) throw new IllegalArgumentException("empty file");

        int n = line.size();

        for (int i = 0 ; i < line.size() ; i++) {
            if (line.get(i).length() != n) {
                throw new IllegalArgumentException("not square");
            }
        }

        boolean[] seen = new boolean[27];
        int jumlahWarna = 0;

        ColorObject color = new ColorObject(n);
        for (int i = 0 ; i < n ; i++) {
            String row = line.get(i);
            for (int j = 0 ; j < n ; j++) {
                char c = Character.toUpperCase(row.charAt(j));
                if (c < 'A' || c > 'Z') {
                    throw new IllegalArgumentException("not valid char");
                }

                int number = c - 'A' + 1;

                if (!seen[number]) {
                    seen[number] = true;
                    jumlahWarna++;
                }

                color.setColor(i, j, number);
            }
        }

        if (jumlahWarna != n) {
            throw new IllegalArgumentException("jumlah warna != n");
        }

        return color;
    }
}
