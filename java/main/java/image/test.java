package image;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "C://Users//miang//Desktop//图//timg.jpg";
		ImageUtil util = new ImageUtil();
		util.thumbnailImage(fileName, 36, 36, true);
		
		File file = new File("C://Users//miang//Desktop//图//thumb_timg.jpg");
		
		
		try{
			//
			BufferedImage bi1 = ImageIO.read(file);
			// 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
			BufferedImage bi2 = new BufferedImage(bi1.getWidth(),bi1.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Ellipse2D.Double shape = new Ellipse2D.Double(0,0,bi1.getWidth(),bi1.getHeight());
			Graphics2D g2 = bi2.createGraphics(); 
			g2.setClip(shape);
			// 使用 setRenderingHint 设置抗锯齿
			g2.drawImage(bi1,0,0,null);
			g2.dispose();
			ImageIO.write(bi2, "png", new File("C://Users//miang//Desktop//图//new.jpg"));
		}catch(Exception e){
			
		}
		
	}

}
