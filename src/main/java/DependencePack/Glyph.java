/**
 * Минимальная единица, ответственная за габариты единицы, за свойства и за формирование самого изображения себя.
 */
package DependencePack;

import javafx.scene.canvas.Canvas;

public abstract class Glyph {
    //свойство единииы изображения
    protected PropertiesGlyph propertiesGlyph;
    //ширина и высота единицы изображения
    protected DimensionGlyph dimensionGlyph;
    //метод формирования самого изображения, по инкапсулированным габаритам и свойствам
    protected abstract Canvas getCanvasGlyph();

}
