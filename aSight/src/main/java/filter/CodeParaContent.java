package filter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//ParaContent is a information content, that just paints all Containers it gets from the CodeThread through a ParaAccessControllList
public class CodeParaContent extends InformationContent{
	public CodeParaContent(int timeout, ContentLayerCompatible parent) {
		super(timeout, parent);
	}

	CodeParaAccessControllList codes;

	public void setCodeAccessControll(CodeParaAccessControllList codes) {
		this.codes = codes;
	}

	public BufferedImage paintContent(BufferedImage img) {
		if (codes!= null){
			Graphics g = img.getGraphics();
			g.setFont(application.Main.getFont());
			try {
				ArrayList<CodeParaContainer> arr = codes.read();
				for (CodeParaContainer c: arr)
					c.paintContent(g);
			} catch (CodeParaNoObjectYetException e) {
				//due to the low upfollowing problems of this error, it is, for now, just ignored.
			}
			g.dispose();
		}
		return img;
	}

}