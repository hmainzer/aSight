package filter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class CodeParaContent extends InformationContent{
	public CodeParaContent(int timeout, Filter parent) {
		super(timeout, parent);
	}

	CodeParaAccessControllList codes;

	public void setCodeAccessControll(CodeParaAccessControllList codes) {
		this.codes = codes;
	}

	@Override
	public BufferedImage paintContent(BufferedImage img) {
		if (codes!= null){
			Graphics g = img.getGraphics();
			try {
				ArrayList<CodeParaContainer> arr = codes.read();
				for (CodeParaContainer c: arr)
					c.paintContent(g);
			} catch (CodeParaNoObjectYetException e) {
			}
			g.dispose();
		}
		return img;
	}

}