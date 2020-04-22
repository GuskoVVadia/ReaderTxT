/**
 * Задача класса:
 *  построение текстовых единиц.
 *  Конструктор класса принимает LinkedList<String> - т.е. то, что нужно преобразовать в текстовый Glyph
 *  Принимает отдельный List - т.е. куда именно нужно записать сформированные классом объекты
 *  Наследник класса Glyph
 */
package DependencePack;

import javafx.scene.canvas.Canvas;

import java.util.LinkedList;
import java.util.List;

public class GlyphTextCompositor extends Glyph {

    private LinkedList<String> compositionString;
    private List<Glyph> compositionGlyphList;

    public GlyphTextCompositor(LinkedList<String> startListString){
        this.compositionString = startListString;
        this.dimensionGlyph = null;
        this.propertiesGlyph = null;
    }

    public void setComposition(List<Glyph> composition) {
            this.compositionGlyphList = composition;
    }

    public void compose() {
        for (String symbol: this.compositionString){
            compositionGlyphList.add(new GlyphText.Builder(symbol).sizeFont(16).build());
        }
    }

    @Override
    protected Canvas getCanvasGlyph() {
        return null;
    }
}
