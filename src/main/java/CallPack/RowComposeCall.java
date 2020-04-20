package CallPack;

import DependencePack.Glyph;
import DependencePack.RowGlyphCompositor;
import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public class RowComposeCall implements Callable<ArrayList<LinkedList<Canvas>>> {

    private LinkedList<Glyph> linkedListGlyph;
    private double height;

    public RowComposeCall(final LinkedList<Glyph> linkedListGlyph, double heightApp) {
        this.linkedListGlyph = linkedListGlyph;
        this.height = heightApp;
    }

    @Override
    public ArrayList<LinkedList<Canvas>> call() throws Exception {
        RowGlyphCompositor rgc = new RowGlyphCompositor();
        return rgc.getListRow(this.linkedListGlyph, this.height);
    }
}
