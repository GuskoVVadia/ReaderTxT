/**
 * Задача класса:
 *      пройтись по списку единиц изображения, найти тектовые единицы и считать слова.
 * Если нужные текстовые единицы - класс декорирует их, путём увеличения их предыдущего значения шрифта
 * и увеличение его.
 * Счёт слов происходит по пробелам и переносам каретки в тексте.
 * Если глиф не текстовый - значений пока не определено.
 */
package DependencePack;

import java.util.ArrayList;

public class TwoWordDecorator {

    private double sizeFontAdd; //переменная величина на которую нужно увеличить шрифт выбранных слов

    /**
     * Конструктор класса
     * @param addValue получаем величину увеличения шрифта
     */
    public TwoWordDecorator(double addValue) {
        this.sizeFontAdd = addValue;
    }

    /**
     * Метод выполняющий поиск двух слов рядом.
     * @param glyphList структура данных, хранящая текстовые глифы, по которым будет происходить поиск и
     *                  которые будут пересобраны.
     */
    public void decorated(ArrayList<GlyphText> glyphList){

        int scoreWord = 1;  //переменная номера слова
        int countSpace = 0; //количество пробелов в искомой строке/структуре
        boolean isWordPrepared = false; //флаг готовности слова

        for (int i = 0; i < glyphList.size(); i++) {
            Glyph glyphTemp = glyphList.get(i);

                if (glyphTemp.propertiesGlyph.getValueTextGlyph().equals(" ") || glyphTemp.propertiesGlyph.getValueTextGlyph().equals("\n")){
                    if (isWordPrepared){
                        scoreWord += 1;
                        if (scoreWord > 4){
                            scoreWord = 1;
                        }
                        countSpace = 0;
                        isWordPrepared = false;
                    }
                } else {
                    isWordPrepared = true;
                    if (scoreWord < 3){
                        double sizeFontThisGlyph = glyphTemp.propertiesGlyph.getFontGlyph().getSize();
                        GlyphText decorGlyph = new GlyphText.Builder((GlyphText) glyphTemp).sizeFont(sizeFontAdd + sizeFontThisGlyph).build();
                        glyphList.set(i, decorGlyph);
                    }
                }
        }
    }
}
