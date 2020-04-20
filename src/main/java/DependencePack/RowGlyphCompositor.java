package DependencePack;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.LinkedList;

public class RowGlyphCompositor extends Glyph{

    public RowGlyphCompositor() {
    }

    public ArrayList<LinkedList<Canvas>> getListRow(final LinkedList<Glyph> listGlyph, double lengthRowApp){  //получение итоговой длины строки

        if (lengthRowApp < 5.0) {
            lengthRowApp = 5.0;
        }

        final double LENGTH = Math.floor(lengthRowApp);

        //итоговый массив, содержащий укомплектованные строки.
        ArrayList<LinkedList<Canvas>> listRow = new ArrayList<>();

        LinkedList<Canvas> row = new LinkedList<>();    //массив готовых строк
        double lengthRow = 0;                              //переменная длины строки

        LinkedList<Canvas> word = new LinkedList<>();   //массив для укомплектовки слов
        double lengthWord = 0;                             //переменная длины слова
        boolean isPreparedWord = false;                 //флаг готовности слова

        boolean isPreparedRow = false;

        for (Glyph glyph: listGlyph){

            if (glyph.propertiesGlyph.getCoreGlyph().equals("text")){

                //если попался пробел или перенос строки
                if (glyph.propertiesGlyph.getValueTextGlyph().equals(" ") || glyph.propertiesGlyph.getValueTextGlyph().equals("\n")) {

                    //если попался пробел
                    if (glyph.propertiesGlyph.getValueTextGlyph().equals(" ")) {
                        lengthWord += glyph.dimensionGlyph.getWidthGlyph();
                        word.add(glyph.getCanvasGlyph());
                        isPreparedWord = true;
                    }//если попался перенос строки
                    else {
                        isPreparedRow = true;
                        isPreparedWord = true;

                    }
                }  //если попадаются символы кроме пробела и переноса строки
                else {
                    word.add(glyph.getCanvasGlyph());
                    lengthWord += glyph.dimensionGlyph.getWidthGlyph();
                }
            }

            //проверка длины слова по готовности слова
            if (isPreparedWord){                //если слово готово

                // длины строки и слова < указаной длины
                if (Math.ceil(lengthWord + lengthRow) < LENGTH){
                    row.addAll(word);
                    lengthRow += lengthWord;
                    word = new LinkedList<>();
                    lengthWord = 0;
                    isPreparedWord = false;
                }
                // если длины строки и слова = указаной длине
                if (Math.ceil(lengthWord + lengthRow) == LENGTH){
                    row.addAll(word);
                    listRow.add(row);
                    row = new LinkedList<>();
                    word = new LinkedList<>();
                    lengthRow = 0;
                    lengthWord = 0;
                    isPreparedWord = false;
                }
                // если длины строки и слова > указаной длины
                if (Math.ceil(lengthWord + lengthRow) > LENGTH){

                    // если строка заполнена - отправляем в страницу
                    if (row.size() > 0){
                        listRow.add(row);
                        row = new LinkedList<>();
                        lengthRow = 0;
                    }

                    // если слово с пустой строкой меньше указанной длины
                    if (Math.ceil(lengthWord + lengthRow) < LENGTH){
                        row.addAll(word);
                        word = new LinkedList<>();
                        lengthRow += lengthWord;
                        lengthWord = 0;
                        isPreparedWord = false;
                    } else {
                        // если слово с пустой строкой равны указаной длине
                        if (Math.ceil(lengthWord + lengthRow) == LENGTH) {
                            row.addAll(word);
                            listRow.add(row);
                            row = new LinkedList<>();
                            word = new LinkedList<>();
                            lengthRow = 0;
                            lengthWord = 0;
                            isPreparedWord = false;
                        }
                        //если слово с пустой строкой больше указаной длины
                        if (Math.ceil(lengthWord + lengthRow) > LENGTH) {

                            //проходимся по каждому элементу
                            for (Canvas canvasWord: word){

                                //если длинна отдельно взятого Canvas + длинна строки <  длинны
                                if (Math.ceil(canvasWord.getWidth() + lengthRow) < LENGTH) {
                                    row.add(canvasWord);
                                    lengthRow += canvasWord.getWidth();
                                }
                                //если длина символа с пустой строкой >= длине
                                else {
                                    row.add(canvasWord);
                                    listRow.add(row);
                                    row = new LinkedList<>();
                                    lengthRow = 0;
                                }

                            }

                            word = new LinkedList<>();
                            lengthWord = 0;
                        }
                    }
                }
            }
            if (isPreparedRow){
                listRow.add(row);
                row = new LinkedList<>();
                lengthRow = 0;
                isPreparedRow = false;
            }
        }

        return listRow;
    }

    @Override
    protected Canvas getCanvasGlyph() {
        return null;
    }
}
