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
      "-284px -426px;", BLANK, BLANK
    };

    new File("tiles").mkdir();
    new File("tileRows").mkdir();

    cmd.setSearchPath("/Users/robert/tmp/ImageMagick-7.0.7/bin");
    int row = 0;
    for (int i = 0; i < coordinates.length; i++) {
      extract(cmd, i, parse(coordinates[i]));
      if (i != 0 && i % 3 == 0) {
        append(row, i-2, i-1, i);
        row++;
      }
    }
  }

  private static int[] parse(String coordinate) {
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
    op.addImage("tiles/tile" + i + ".png");
    op.addImage("tiles/tile" + i1 + ".png");
    op.addImage("tiles/tile" + i2 + ".png");
    op.appendHorizontally();
    op.addImage("tileRows/row" + row + ".png");
    cmd.run(op);
  }
}
