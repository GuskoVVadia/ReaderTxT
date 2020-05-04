package DependencePack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitReadingFile {
    public ArrayList<String> getList(Path path) throws IOException {

        List<String> listFileRow =  Files.readAllLines(path, StandardCharsets.UTF_8);
        ArrayList<String> listSymbol = new ArrayList<>();

        for (int i = 0; i < listFileRow.size(); i++) {
            listSymbol.addAll(Arrays.asList(listFileRow.get(i).split("")));
            listSymbol.add("\n");
            listFileRow.set(i, null);
        }


        listFileRow.clear();
        listFileRow = null;
        return listSymbol;
    }
}
