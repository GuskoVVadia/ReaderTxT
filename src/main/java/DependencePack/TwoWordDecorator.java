/**
 * Задача класса:
 *      пройтись по списку единиц изображения, найти тектовые единицы и считать слова.
 * Если нужные текстовые единицы - класс декорирует их, путём увеличения их предыдущего значения шрифта
 * и увеличение его на 3 единицы (в соответствии с ТЗ).
 * Счёт слов происходит по пробелам и переносам каретки в тексте.
 * Если глиф не текстовый - значений пока не определено.
 */
package DependencePack;

import java.util.ArrayList;

public class TwoWordDecorator {

    private double sizeFontAdd;

    public TwoWordDecorator(double addValue) {
        this.sizeFontAdd = addValue;
    }

    public void decorated(ArrayList<GlyphText> glyphList){

        int scoreWord = 1;
        int countSpace = 0;
        boolean isWordPrepared = false;

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
