/**
 * Задача класса:
 *  описание минимальной текстовой единицы - расчёт габаритов и свойств.
 *  Наследник класса Glyph - добавляет свойства текстовой единицы
 *  Использован паттерн Строитель.
 */
package DependencePack;

import javafx.scene.canvas.Canvas;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GlyphText extends Glyph{

    public static class Builder{
        private String valueGlyphTextBuilder;
        private double sizeFontGlyphTextBuilder = 12.0;
        private String nameFontGlyphTextBuilder = "Arial";
        private Font fontGlyphTextBuilder;
        private DimensionGlyph dimensionGlyphTextBuilder;

        public Builder(String value){
            this.valueGlyphTextBuilder = value;
        }

        public Builder(GlyphText glyphText){
            this.valueGlyphTextBuilder = glyphText.propertiesGlyph.getValueTextGlyph();
        }

        public Builder sizeFont(double sizeFont){
            this.sizeFontGlyphTextBuilder = sizeFont;
            return this;
        }

        public Builder nameFont(String nameFont){
            this.nameFontGlyphTextBuilder = nameFont;
            return this;
        }

        public Builder font(Font font){
            this.fontGlyphTextBuilder = font;
            return this;
        }

        public GlyphText build(){
            if (this.fontGlyphTextBuilder == null){
                this.fontGlyphTextBuilder = new Font(this.nameFontGlyphTextBuilder,
                        this.sizeFontGlyphTextBuilder);
            }
            this.dimensionGlyphTextBuilder = getDimension();
            return new GlyphText(this);

        }

        //метод просчёта свойств ширины и высоты символа
        //расчёт происходит от того, сколько места занимает написание символа указанным шрифтом и его размером
        private DimensionGlyph getDimension(){
            Text text = new Text();
            text.setFont(this.fontGlyphTextBuilder);
            text.setText(this.valueGlyphTextBuilder);
            double width = text.getBoundsInLocal().getWidth();
            double height = text.getBoundsInLocal().getHeight();
            return new DimensionGlyph(width, height);
        }
    }

    private GlyphText (Builder builder){
        this.propertiesGlyph = new PropertiesGlyph(
                "text", builder.fontGlyphTextBuilder, builder.valueGlyphTextBuilder);
        this.dimensionGlyph = builder.dimensionGlyphTextBuilder;
    }


    @Override
    protected Canvas getCanvasGlyph() {

            // при построении canvas ширина округлена для правильного отображения на странице по горизонту
            Canvas canvasGlyph = new Canvas(Math.round(this.dimensionGlyph.getWidthGlyph()),
                    this.dimensionGlyph.getHeightGlyph());
            canvasGlyph.getGraphicsContext2D().setFont(this.propertiesGlyph.getFontGlyph());
            canvasGlyph.getGraphicsContext2D().fillText(this.propertiesGlyph.getValueTextGlyph(),
                    0.0,
                    this.dimensionGlyph.getHeightGlyph() * 0.75);
        return canvasGlyph;
    }
}
