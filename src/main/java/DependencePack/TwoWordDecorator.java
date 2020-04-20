/**
 * Задача класса:
 *      пройтись по списку единиц изображения, найти тектовые единицы и считать слова.
 * Если нужные текстовые единицы - класс декорирует их, путём увеличения их предыдущего значения шрифта
 * и увеличение его на 3 единицы (в соответствии с ТЗ).
 * Счёт слов происходит по пробелам и переносам каретки в тексте.
 * Если глиф не текстовый - значений пока не определено.
 */
package DependencePack;

import java.util.LinkedList;

public class TwoWordDecorator {

    private double sizeFontAdd = 3.0;
    private LinkedList<Glyph> glyphList;

    public TwoWordDecorator() {
    }

    public void setComposition(LinkedList<Glyph> glyphListNonDecor){
        this.glyphList = glyphListNonDecor;
    }

    public void decorated(){

        int scoreWord = 1;
        int countSpace = 0;
        boolean isWordPrepared = false;

        for (int i = 0; i < glyphList.size(); i++) {
            Glyph glyphTemp = glyphList.get(i);

            if (glyphTemp.propertiesGlyph.getCoreGlyph().equals("text")) {

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
}
