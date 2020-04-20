/**
 * Задача класса:
 *  инкаплулирование размеров минимальной единицы отображения - Glyph
 *  формирует размеры один раз и предохраняет от изменений
 *  отдача размеров через геттеры
 */

package DependencePack;

public class DimensionGlyph {

    private final double WIDTHGLYPH;
    private final double HEIGHTGLYPH;

    public DimensionGlyph(double widthGlyph, double heightGlyph) {
        this.WIDTHGLYPH = widthGlyph;
        this.HEIGHTGLYPH = heightGlyph;
    }

    public double getWidthGlyph() {
        return WIDTHGLYPH;
    }

    public double getHeightGlyph() {
        return HEIGHTGLYPH;
    }
}
