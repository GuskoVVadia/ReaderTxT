/**
 * Задача класса - инкапсулировать свойства Glyph
 * По ТЗ был сформирован только текстовый блок
 * Несёт в себе значения:
 *      сущности Glyph - текстовый, в данном случае,
 *      шрифт, которым будет произведено написание текста
 *      и само значение - в данном случае один символ String
 */
package DependencePack;

import javafx.scene.text.Font;

public class PropertiesGlyph {
    private final String COREGLYPH;
    private final Font FONTGLYPH;
    private final String VALUEGLYPH;

    public PropertiesGlyph(String coreGlyph, Font fontGlyph, String valueTextGlyph) {
        this.COREGLYPH = coreGlyph;
        this.FONTGLYPH = fontGlyph;
        this.VALUEGLYPH = valueTextGlyph;
    }

    public String getCoreGlyph() {
        return COREGLYPH;
    }

    public Font getFontGlyph() {
        return FONTGLYPH;
    }

    public String getValueTextGlyph() {
        return VALUEGLYPH;
    }
}
