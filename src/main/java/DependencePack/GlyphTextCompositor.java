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

public class GlyphTextCompositor extends Glyph{

    private LinkedList<String> compositionString;

    public GlyphTextCompositor(LinkedList<String> startListString){
        this.compositionString = startListString;
        this.dimensionGlyph = null;
        this.propertiesGlyph = null;
    }

    public void compose(LinkedList<Glyph> composition) {

        for (String symbol: this.compositionString){
            composition.add(new GlyphText.Builder(symbol).sizeFont(16).build());
        }
    }

    @Override
    protected Canvas getCanvasGlyph() {
        return null;
    }
}
