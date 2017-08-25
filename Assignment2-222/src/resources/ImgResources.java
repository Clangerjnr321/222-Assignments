package resources;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum ImgResources {
	FaceG("FaceG.png"),
	FaceY("FaceY.png"),
	GreenA("GreenPieceA.png"),
	GreenB("GreenPieceB.png"),
	GreenC("GreenPieceC.png"),
	GreenD("GreenPieceD.png"),
	GreenE("GreenPieceE.png"),
	GreenF("GreenPieceF.png"),
	GreenH("GreenPieceH.png"),
	GreenI("GreenPieceI.png"),
	GreenJ("GreenPieceJ.png"),
	GreenK("GreenPieceK.png"),
	GreenL("GreenPieceL.png"),
	GreenM("GreenPieceM.png"),
	GreenN("GreenPieceN.png"),
	GreenO("GreenPieceO.png"),
	GreenP("GreenPieceP.png"),
	GreenQ("GreenPieceQ.png"),
	GreenR("GreenPieceR.png"),
	GreenS("GreenPieceS.png"),
	GreenT("GreenPieceT.png"),
	GreenU("GreenPieceU.png"),
	GreenV("GreenPieceV.png"),
	GreenW("GreenPieceW.png"),
	GreenX("GreenPieceX.png"),
	GreenZ("GreenPieceZ.png"),
	YellowA("YellowPieceA.png"),
	YellowB("YellowPieceB.png"),
	YellowC("YellowPieceC.png"),
	YellowD("YellowPieceD.png"),
	YellowE("YellowPieceE.png"),
	YellowF("YellowPieceF.png"),
	YellowH("YellowPieceH.png"),
	YellowI("YellowPieceI.png"),
	YellowJ("YellowPieceJ.png"),
	YellowK("YellowPieceK.png"),
	YellowL("YellowPieceL.png"),
	YellowM("YellowPieceM.png"),
	YellowN("YellowPieceN.png"),
	YellowO("YellowPieceO.png"),
	YellowP("YellowPieceP.png"),
	YellowQ("YellowPieceQ.png"),
	YellowR("YellowPieceR.png"),
	YellowS("YellowPieceS.png"),
	YellowT("YellowPieceT.png"),
	YellowU("YellowPieceU.png"),
	YellowV("YellowPieceV.png"),
	YellowW("YellowPieceW.png"),
	YellowX("YellowPieceX.png"),
	YellowZ("YellowPieceZ.png");


	public Image img;
	ImgResources(String resourceName) {
		try{ img = ImageIO.read(ImgResources.class.getResource(resourceName)); }
		catch (IOException e){ throw new Error(e); }
	}

	public Image rotate(Image img, int number) {

		double angle = number*90;

		double sin = Math.abs(Math.sin(Math.toRadians(angle))),
				cos = Math.abs(Math.cos(Math.toRadians(angle)));

		int height = img.getHeight(null);
		int width = img.getWidth(null);

		int newWidth = (int) Math.floor(width * cos + height * sin);
		int newHeight = (int) Math.floor(height * cos + width * sin);

		BufferedImage image = new BufferedImage(67, 67, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		g.translate((newWidth-width)/2, (newHeight-height)/2);
		g.rotate(Math.toRadians(angle), width/2, height/2);
		g.drawRenderedImage((RenderedImage) img, null);
		g.dispose();

		return image;

	}
}
