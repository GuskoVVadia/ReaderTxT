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

    //статичный класс построитель
    public static class Builder{
        private String valueGlyphTextBuilder;   //символ, который будет выведен этим глифом (буква, пробел, перенос каретки)
        private double sizeFontGlyphTextBuilder = 12.0; //шрифт по умолчанию
        private String nameFontGlyphTextBuilder = "Arial";  //название шрифта по умолчанию
        private Font fontGlyphTextBuilder;  //шрифт, что будет использоваться glyph для отрисовки своего значения
        private DimensionGlyph dimensionGlyphTextBuilder;   //класс габаритов (ширины, высоты) символа

        //конструктор строителя принимает символ
        public Builder(String value){
            this.valueGlyphTextBuilder = value;
        }

        //конструктор строителя принимает аналогичный glyph, что бы получить значение типа String
        public Builder(GlyphText glyphText){
            this.valueGlyphTextBuilder = glyphText.propertiesGlyph.getValueTextGlyph();
        }

        //установка размеров шрифта
        public Builder sizeFont(double sizeFont){
            this.sizeFontGlyphTextBuilder = sizeFont;
            return this;
        }

        //установка имени шрифта
        public Builder nameFont(String nameFont){
            this.nameFontGlyphTextBuilder = nameFont;
            return this;
        }

        //установка шрифта
        public Builder font(Font font){
            this.fontGlyphTextBuilder = font;
            return this;
        }

        //построение класса GlyphText и небольшая проверка на изменившиеся значения, если нет - тогда по умолчанию
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
            text = null;
            return new DimensionGlyph(width, height);
        }
    }

    //закрытый конструктор класса, доступный только статическому строителю
    private GlyphText (Builder builder){
        this.propertiesGlyph = new PropertiesGlyph(
                "text", builder.fontGlyphTextBuilder, builder.valueGlyphTextBuilder);
        this.dimensionGlyph = builder.dimensionGlyphTextBuilder;
    }


    //описание как сформировать Canvas из этого глифа.
    //Формирование возвращаемого объекта происходит из ширины и высота размеров буквы при написании указанным шрифтом
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
