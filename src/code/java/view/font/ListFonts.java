package code.java.view.font;

import java.awt.*;

/**
 *  From Core Java volume 1.
 *  List available fonts from your operating system.
 */
public class ListFonts {
    public static void main(String[] args) {
/*
To ﬁnd out which fonts are available on a particular
computer, call the getAvailableFontFamilyNames method
of the GraphicsEnvironment class.
The method returns an array of strings containing
the names of all available fonts.

To obtain an instance of the GraphicsEnvironment
class that describes the graphics environment
of the user’s system, use the static
getLocalGraphicsEnvironment method.

The following program prints the names
of all fonts on your system:
*/
        String[] fontNames =
                GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();

        for (String fontName : fontNames) {
            System.out.println(fontName);
        }
    }

/**
 *
 * 上面程序在Core Java volume 1教科书上输入结果如下：
 * SansSerif
 * Serif
 * Monospaced
 * Dialog
 * DialogInput
 *
 * 上面程序在我Lenovo Legion这台电脑上的输出结果如下：
 * Agency FB
 * Algerian
 * Arial
 * Arial Black
 * Arial Narrow
 * Arial Rounded MT Bold
 * Bahnschrift
 * Baskerville Old Face
 * Bauhaus 93
 * Bell MT
 * Berlin Sans FB
 * Berlin Sans FB Demi
 * Bernard MT Condensed
 * Blackadder ITC
 * Bodoni MT
 * Bodoni MT Black
 * Bodoni MT Condensed
 * Bodoni MT Poster Compressed
 * Book Antiqua
 * Bookman Old Style
 * Bookshelf Symbol 7
 * Bradley Hand ITC
 * Britannic Bold
 * Broadway
 * Brush Script MT
 * Calibri
 * Calibri Light
 * Californian FB
 * Calisto MT
 * Cambria
 * Cambria Math
 * Candara
 * Candara Light
 * Castellar
 * Centaur
 * Century
 * Century Gothic
 * Century Schoolbook
 * Chiller
 * Colonna MT
 * Comic Sans MS
 * Consolas
 * Constantia
 * Cooper Black
 * Copperplate Gothic Bold
 * Copperplate Gothic Light
 * Corbel
 * Corbel Light
 * Courier New
 * Curlz MT
 * Dialog
 * DialogInput
 * DIN Next LT Pro
 * DIN Next LT Pro Light
 * DIN Next LT Pro Medium
 * Dubai
 * Dubai Light
 * Dubai Medium
 * Ebrima
 * Edwardian Script ITC
 * Elephant
 * Engravers MT
 * Eras Bold ITC
 * Eras Demi ITC
 * Eras Light ITC
 * Eras Medium ITC
 * Felix Titling
 * Footlight MT Light
 * Forte
 * Franklin Gothic Book
 * Franklin Gothic Demi
 * Franklin Gothic Demi Cond
 * Franklin Gothic Heavy
 * Franklin Gothic Medium
 * Franklin Gothic Medium Cond
 * Freestyle Script
 * French Script MT
 * Gabriola
 * Gadugi
 * Garamond
 * Georgia
 * Gigi
 * Gill Sans MT
 * Gill Sans MT Condensed
 * Gill Sans MT Ext Condensed Bold
 * Gill Sans Ultra Bold
 * Gill Sans Ultra Bold Condensed
 * Gloucester MT Extra Condensed
 * Goudy Old Style
 * Goudy Stout
 * Haettenschweiler
 * Harlow Solid Italic
 * Harrington
 * HGB1_CNKI
 * HGB1X_CNKI
 * HGB2_CNKI
 * HGB2X_CNKI
 * HGB3_CNKI
 * HGB3X_CNKI
 * HGB4_CNKI
 * HGB4X_CNKI
 * HGB5_CNKI
 * HGB5X_CNKI
 * HGB6_CNKI
 * HGB6X_CNKI
 * HGB7_CNKI
 * HGB7X_CNKI
 * HGB8_CNKI
 * HGB8X_CNKI
 * HGBD_CNKI
 * HGBKB_CNKI
 * HGBKBX_CNKI
 * HGBKH_CNKI
 * HGBKHX_CNKI
 * HGBX_CNKI
 * HGBZ_CNKI
 * HGDY_CNKI
 * HGF1_CNKI
 * HGF1X_CNKI
 * HGF2_CNKI
 * HGF2X_CNKI
 * HGF3_CNKI
 * HGF4_CNKI
 * HGF4X_CNKI
 * HGF5_CNKI
 * HGF5X_CNKI
 * HGF6_CNKI
 * HGF6X_CNKI
 * HGF7_CNKI
 * HGF7X_CNKI
 * HGF8_CNKI
 * HGF9_CNKI
 * HGF9X_CNKI
 * HGFX_CNKI
 * HGFZ_CNKI
 * HGH1_CNKI
 * HGH1X_CNKI
 * HGH2_CNKI
 * HGH2X_CNKI
 * HGH3_CNKI
 * HGH3X_CNKI
 * HGH4_CNKI
 * HGH4X_CNKI
 * HGH5_CNKI
 * HGH5X_CNKI
 * HGH6_CNKI
 * HGH6X_CNKI
 * HGH7_CNKI
 * HGH7X_CNKI
 * HGHD_CNKI
 * HGHT1_CNKI
 * HGHT2_CNKI
 * HGHUATI_CNKI
 * HGHX_CNKI
 * HGHZ_CNKI
 * HGKY_CNKI
 * HGNBS_CNKI
 * HGOCR_CNKI
 * HGSXT_CNKI
 * HGTT_CNKI
 * HGX1_CNKI
 * HGX1X_CNKI
 * HGXF1_CNKI
 * HGXFX_CNKI
 * HGXFZ_CNKI
 * HGXT_CNKI
 * HGXY_CNKI
 * HGYB_CNKI
 * HGYT1_CNKI
 * HGYT2_CNKI
 * High Tower Text
 * HoloLens MDL2 Assets
 * Impact
 * Imprint MT Shadow
 * Informal Roman
 * Ink Free
 * Javanese Text
 * Jokerman
 * Juice ITC
 * Kristen ITC
 * Kunstler Script
 * Leelawadee UI
 * Leelawadee UI Semilight
 * Lucida Bright
 * Lucida Calligraphy
 * Lucida Console
 * Lucida Fax
 * Lucida Handwriting
 * Lucida Sans
 * Lucida Sans Typewriter
 * Lucida Sans Unicode
 * Magneto
 * Maiandra GD
 * Malgun Gothic
 * Malgun Gothic Semilight
 * Marlett
 * Matura MT Script Capitals
 * Microsoft Himalaya
 * Microsoft JhengHei UI
 * Microsoft JhengHei UI Light
 * Microsoft New Tai Lue
 * Microsoft PhagsPa
 * Microsoft Sans Serif
 * Microsoft Tai Le
 * Microsoft YaHei UI
 * Microsoft YaHei UI Light
 * Microsoft Yi Baiti
 * Mistral
 * Modern No. 20
 * Mongolian Baiti
 * Monospaced
 * Monotype Corsiva
 * MS Gothic
 * MS Outlook
 * MS PGothic
 * MS Reference Sans Serif
 * MS Reference Specialty
 * MS UI Gothic
 * MT Extra
 * MV Boli
 * Myanmar Text
 * Niagara Engraved
 * Niagara Solid
 * Nirmala UI
 * Nirmala UI Semilight
 * NumberOnly
 * OCR A Extended
 * Old English Text MT
 * Onyx
 * Palace Script MT
 * Palatino Linotype
 * Papyrus
 * Parchment
 * Perpetua
 * Perpetua Titling MT
 * Playbill
 * Poor Richard
 * Pristina
 * Rage Italic
 * Ravie
 * Rockwell
 * Rockwell Condensed
 * Rockwell Extra Bold
 * SansSerif
 * Script MT Bold
 * Segoe MDL2 Assets
 * Segoe Print
 * Segoe Script
 * Segoe UI
 * Segoe UI Black
 * Segoe UI Emoji
 * Segoe UI Historic
 * Segoe UI Light
 * Segoe UI Semibold
 * Segoe UI Semilight
 * Segoe UI Symbol
 * Serif
 * Showcard Gothic
 * SimSun-ExtB
 * Sitka Banner
 * Sitka Display
 * Sitka Heading
 * Sitka Small
 * Sitka Subheading
 * Sitka Text
 * Snap ITC
 * Stencil
 * Sylfaen
 * Symbol
 * Tahoma
 * Tempus Sans ITC
 * Times New Roman
 * Trebuchet MS
 * Tw Cen MT
 * Tw Cen MT Condensed
 * Tw Cen MT Condensed Extra Bold
 * Verdana
 * Viner Hand ITC
 * Vivaldi
 * Vladimir Script
 * Webdings
 * Wide Latin
 * Wingdings
 * Wingdings 2
 * Wingdings 3
 * Yu Gothic
 * Yu Gothic Light
 * Yu Gothic Medium
 * Yu Gothic UI
 * Yu Gothic UI Light
 * Yu Gothic UI Semibold
 * Yu Gothic UI Semilight
 * 仿宋
 * 华光中圆_CNKI
 * 华光中楷_CNKI
 * 华光中等线_CNKI
 * 华光中长宋_CNKI
 * 华光中雅_CNKI
 * 华光书宋_CNKI
 * 华光书宋一_CNKI
 * 华光书宋二_CNKI
 * 华光仿宋_CNKI
 * 华光仿宋一_CNKI
 * 华光仿宋二_CNKI
 * 华光准圆_CNKI
 * 华光大标宋_CNKI
 * 华光大黑_CNKI
 * 华光大黑二_CNKI
 * 华光姚体_CNKI
 * 华光小标宋_CNKI
 * 华光平黑_CNKI
 * 华光幼线_CNKI
 * 华光广告_CNKI
 * 华光彩云_CNKI
 * 华光报宋_CNKI
 * 华光报宋一_CNKI
 * 华光报宋二_CNKI
 * 华光敦韵宋_CNKI
 * 华光文韵宋_CNKI
 * 华光方珊瑚_CNKI
 * 华光标题宋_CNKI
 * 华光标题黑_CNKI
 * 华光楷体_CNKI
 * 华光楷体一_CNKI
 * 华光楷体二_CNKI
 * 华光淡古印_CNKI
 * 华光琥珀_CNKI
 * 华光秀丽_CNKI
 * 华光粗圆_CNKI
 * 华光粗黑_CNKI
 * 华光细圆_CNKI
 * 华光细黑_CNKI
 * 华光细黑一_CNKI
 * 华光综艺_CNKI
 * 华光美黑_CNKI
 * 华光胖头鱼_CNKI
 * 华光行书_CNKI
 * 华光行楷_CNKI
 * 华光行草_CNKI
 * 华光超粗黑_CNKI
 * 华光通心圆_CNKI
 * 华光隶书_CNKI
 * 华光隶变_CNKI
 * 华光魏体_CNKI
 * 华光黑体_CNKI
 * 华光黑变_CNKI
 * 华文中宋
 * 华文仿宋
 * 华文宋体
 * 华文彩云
 * 华文新魏
 * 华文楷体
 * 华文琥珀
 * 华文细黑
 * 华文行楷
 * 华文隶书
 * 宋体
 * 幼圆
 * 微軟正黑體
 * 微軟正黑體 Light
 * 微软雅黑
 * 微软雅黑 Light
 * 新宋体
 * 新細明體-ExtB
 * 方正兰亭超细黑简体
 * 方正姚体
 * 方正粗黑宋简体
 * 方正舒体
 * 楷体
 * 汉仪中黑 197
 * 等线
 * 等线 Light
 * 細明體-ExtB
 * 細明體_HKSCS-ExtB
 * 隶书
 * 黑体
 *
 *
 * 按：两者输入结果不同，可见学编程，不能死板地照教科书来。
 */

}
