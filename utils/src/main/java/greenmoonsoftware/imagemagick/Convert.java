package greenmoonsoftware.imagemagick;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import java.io.File;
import java.io.IOException;

public class Convert {
  private static final String BLANK = "-145px -176px;";
  private static final ConvertCmd cmd = new ConvertCmd();

  public static void main(String[] args0) throws InterruptedException, IOException, IM4JavaException {
    String[] coordinates = new String[]{
      "-37px -237px", "-91px -237px",  "-145px -237px;",
      "-37px -291px;", "-91px -291px;", "-145px -291px;",
      "-37px -345px;", "-91px -345px;", "-145px -345px;",
      "-37px -407px;", "-91px -407px;", "-145px -407px;",
      BLANK, BLANK, "-145px -461px;",
      "-37px -523px;", "-91px -523px;", "-145px -523px;",
      BLANK, BLANK, "-145px -577px;",
      "-37px -639px;", "-91px -639px;", "-145px -639px;",
      BLANK, BLANK, "-145px -693px;",
      "-37px -755px;", "-91px -755px;", "-145px -755px;",
      BLANK, BLANK, "-145px -809px;",
      "-230px -237px;", "-284px -237px;", BLANK,
      "-230px -318px;", "-284px -318px;", BLANK,
      "-230px -372px;", "-284px -372px;", BLANK,
      "", "-284px -426px;", BLANK,
      "", "", ""
    };

    String[] coordinatesGray = new String[]{
      "-461px -237px;", "-515px -237px;", "-569px -237px;",
      "-461px -291px;", "-515px -291px;", "-569px -291px;",
      "-461px -345px;", "-515px -345px;", "-569px -345px;",
      "-461px -399px;", "-515px -399px;", "-569px -399px;",
      "", "", "-569px -453px;",
      "-461px -507px;", "-515px -507px;", "-569px -507px;",
      "", "", "-569px -561px;",
      "-459px -638px;", "-513px -638px;", "-567px -638px;",
      "", "", "-567px -692px;",
      "-461px -762px;", "-515px -762px;", "-569px -762px;",
      "", "", "-569px -816px;",
      "-652px -237px;", "-706px -237px;", "",
      "-652px -318px;", "-706px -318px;", "",
      "-652px -372px;", "-706px -372px;", "",
      "", "-706px -426px;", "",
      "", "", ""
    };
    cmd.setSearchPath("/Users/robert/tmp/ImageMagick-7.0.7/bin");

    new File("tiles").mkdir();
    new File("tileRows").mkdir();
    new File("tileOutputs").mkdir();

    set(coordinates, 1);
    set(coordinatesGray, 2);

    reallyAppendAll();
  }

  private static void set(String[] coordinates, int set) throws InterruptedException, IOException, IM4JavaException {
    int row = 0;
    for (int i = 0; i < coordinates.length; i++) {
      if (i != 0 && i % 3 == 0) {
        append(row, i-3, i-2, i-1);
        row++;
      }
      extract(cmd, i, parse(coordinates[i]));
    }
    appendAll(row, set);
  }

  private static int[] parse(String coordinate) {
    if ("".equals(coordinate)) {
      coordinate = BLANK;
    }
    String[] result = coordinate
      .replaceAll("-", "")
      .replaceAll("px", "")
      .replaceAll(";", "")
      .split(" ");
    return new int[]{
      Integer.parseInt(result[0].trim()),
      Integer.parseInt(result[1].trim())
    };
  }

  private static void extract(ConvertCmd cmd, int index, int[] xy) throws IOException, InterruptedException, IM4JavaException {
    IMOperation op = new IMOperation();
    op.addImage("OGGoodWoodsTiles.png");
    op.crop(50, 50, xy[0], xy[1]);
    op.addImage("tiles/tile" + index + ".png");
    cmd.run(op);
  }

  private static void append(int row, int i, int i1, int i2) throws InterruptedException, IOException, IM4JavaException {
    IMOperation op = new IMOperation();
    System.out.println("Row: " + row + "; " + i + ", " + i1 + ", " + i2);
    op.addImage("tiles/tile" + i + ".png");
    op.addImage("tiles/tile" + i1 + ".png");
    op.addImage("tiles/tile" + i2 + ".png");
    op.appendHorizontally();
    op.addImage("tileRows/row" + row + ".png");
    cmd.run(op);
  }

  private static void appendAll(int row, int set) throws InterruptedException, IOException, IM4JavaException {
    IMOperation op = new IMOperation();
    for (int i = 0; i < row; i++) {
      op.addImage("tileRows/row" + i + ".png");
    }
    op.appendVertically();
    op.addImage("tileOutputs/tileset" + set + ".png");
    cmd.run(op);
  }

  private static void reallyAppendAll() throws InterruptedException, IOException, IM4JavaException {
    IMOperation op = new IMOperation();
    op.addImage("tileOutputs/tileset1.png");
    op.addImage("tileOutputs/tileset2.png");
    op.appendHorizontally();
    op.addImage("tileOutputs/tileset-final.png");
    cmd.run(op);
  }
}
