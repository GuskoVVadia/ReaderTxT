package DependencePack;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.LinkedList;

public class RowGlyphCompositor{

    private ArrayList<GlyphText> listGlyph;
    private double widthPage;
    private ArrayList<ArrayList<GlyphText>> arrayListsRowReady;

    public RowGlyphCompositor(ArrayList<GlyphText> listGlyph, double width) {
        this.listGlyph = listGlyph;
        this.widthPage = width;
        this.arrayListsRowReady = new ArrayList<>();
        transformRow();
    }

    private void transformRow(){

        //итоговый массив, содержащий укомплектованные строки.
        ArrayList<ArrayList<GlyphText>> listRow = new ArrayList<>();

        ArrayList<GlyphText> row = new ArrayList<>();    //массив готовых строк
        double lengthRow = 0;                              //переменная длины строки

        ArrayList<GlyphText> word = new ArrayList<>();   //массив для укомплектовки слов
        double lengthWord = 0;                             //переменная длины слова
        boolean isPreparedWord = false;                 //флаг готовности слова

        boolean isPreparedRow = false;

//        for (Glyph glyph: this.listGlyph){
        for (int i = 0; i < this.listGlyph.size(); i++) {

            GlyphText glyphTextCurrent = listGlyph.get(i);

                //если попался пробел или перенос строки
                if (glyphTextCurrent.propertiesGlyph.getValueTextGlyph().equals(" ") ||
                        glyphTextCurrent.propertiesGlyph.getValueTextGlyph().equals("\n")) {

                    //если попался пробел
                    if (glyphTextCurrent.propertiesGlyph.getValueTextGlyph().equals(" ")) {
                        lengthWord += glyphTextCurrent.dimensionGlyph.getWidthGlyph();
                        word.add(glyphTextCurrent);
                        isPreparedWord = true;
                    }//если попался перенос строки
                    else {
                        isPreparedRow = true;
                        isPreparedWord = true;
                    }
                }  //если попадаются символы кроме пробела и переноса строки
                else {
                    word.add(glyphTextCurrent);
                    lengthWord += glyphTextCurrent.dimensionGlyph.getWidthGlyph();
                }

            //проверка длины слова по готовности слова
            if (isPreparedWord){                //если слово готово

                // длины строки и слова < указаной длины
                if ((lengthWord + lengthRow) < this.widthPage){
                    row.addAll(word);
                    lengthRow += lengthWord;
                    word = new ArrayList<>();
                    lengthWord = 0;
                    isPreparedWord = false;
                }
                // если длины строки и слова = указаной длине
                if ((lengthWord + lengthRow) == this.widthPage){
                    row.addAll(word);
                    listRow.add(row);
                    row = new ArrayList<>();
                    word = new ArrayList<>();
                    lengthRow = 0;
                    lengthWord = 0;
                    isPreparedWord = false;
                }
                // если длины строки и слова > указаной длины
                if ((lengthWord + lengthRow) > this.widthPage){

                    // если строка заполнена - отправляем в страницу
                    if (row.size() > 0){
                        listRow.add(row);
                        row = new ArrayList<>();
                        lengthRow = 0;
                    }

                    // если слово с пустой строкой меньше указанной длины
                    if ((lengthWord + lengthRow) < this.widthPage){
                        row.addAll(word);
                        word = new ArrayList<>();
                        lengthRow += lengthWord;
                        lengthWord = 0;
                        isPreparedWord = false;
                    } else {
                        // если слово с пустой строкой равны указаной длине
                        if ((lengthWord + lengthRow) == this.widthPage) {
                            row.addAll(word);
                            listRow.add(row);
                            row = new ArrayList<>();
                            word = new ArrayList<>();
                            lengthRow = 0;
                            lengthWord = 0;
                            isPreparedWord = false;
                        }

                        //если слово с пустой строкой больше указаной длины
                        if ((lengthWord + lengthRow) > this.widthPage) {

                            //проходимся по каждому элементу
                            for (int j = 0; j < word.size(); j++) {

                                //если длинна отдельно взятого Canvas + длинна строки <  длинны
                                GlyphText gtTemp = word.get(j);
                                if ((gtTemp.dimensionGlyph.getWidthGlyph() + lengthRow) < this.widthPage) {
                                    row.add(gtTemp);
                                    lengthRow += gtTemp.dimensionGlyph.getWidthGlyph();
                                }
                                //если длина символа с пустой строкой >= длине
                                else {
                                    row.add(gtTemp);
                                    listRow.add(row);
                                    row = new ArrayList<>();
                                    lengthRow = 0;
                                }
                            }

                            word = new ArrayList<>();
                            lengthWord = 0;
                        }
                    }
                }
            }
            if (isPreparedRow){
                listRow.add(row);
                row = new ArrayList<>();
                lengthRow = 0;
                isPreparedRow = false;
            }
        } //end for loop

        if (this.arrayListsRowReady.size() > 0) {
            this.arrayListsRowReady.clear();
        }
        this.arrayListsRowReady = listRow;

    }

    public boolean getRow(int i, LinkedList<Canvas> canvasLinkedList){
        if (i >= this.arrayListsRowReady.size()){
            return false;
        }
        ArrayList<GlyphText> arrayListRowOut = arrayListsRowReady.get(i);
        for (int j = 0; j < arrayListRowOut.size(); j++) {
            canvasLinkedList.add(arrayListRowOut.get(j).getCanvasGlyph());
        }
        return true;
    }


    public void setWidthPage(double width){
        this.widthPage = width;
        transformRow();
    }

    public int getLength(){
        return this.arrayListsRowReady.size();
    }



}
