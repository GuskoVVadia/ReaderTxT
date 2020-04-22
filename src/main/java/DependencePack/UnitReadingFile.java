/**
 * модуль чтения файла.
 * Задача - считать из файла текст, разбить на отдельные символы и передать список символом при запросе.
 * В случае неудачи работы с файлом - формируется текстовый лист сообщения об ошибке чтения и тоже передаётся по запросу
 * текста.
 * Считывание производится текстовых файлом, с кодировкой UTF-8.
 * Лист ошибочного чтения формируется при отлове ошибки чтения файла.
 */
package DependencePack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UnitReadingFile {

    public LinkedList<String> getSymbolComposition (Path path){
        List<String> listFileRow;
        try {
            listFileRow =  Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            listFileRow = new ArrayList<>(Arrays.asList("Ошибка чтения файла".split("")));
        }

        LinkedList<String> listSymbol = new LinkedList<>();

        for (String rowFile: listFileRow){
            listSymbol.addAll(Arrays.asList(rowFile.split("")));
            listSymbol.add("\n");
        }

        return listSymbol;
    }


}
