package filter;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class CodeParaFilter extends AbstractFilter {
	CodeParaContent codeContent;
	CodeParaAccessControllImg imgAccess = new CodeParaAccessControllImg();

	CodeThread thread;
	Timer timer;
	
	public CodeParaFilter(ContentLayer contentLayer) {
		this.codeContent = new CodeParaContent(-1, this);
		CodeParaAccessControllList list = new CodeParaAccessControllList();
		codeContent.setCodeAccessControll(list);
		thread = new CodeThread(imgAccess, list);
		timer = new Timer();
		timer.schedule(thread, 1000, 500);
		contentLayer.giveContent(this, codeContent);
	}
		
	protected BufferedImage action(BufferedImage img) {
		imgAccess.write(img);
		return img;
		}

	public void createGUI( Container parentBox ) {
		JLabel filterLabel = new JLabel( "~~~ Codereader ~~~" );
		parentBox.add( filterLabel );
		final JCheckBox qrBox = new JCheckBox("QR Codes");
		final JCheckBox barcodeBox = new JCheckBox( "Barcodes" );

		filterLabel.setBounds( 8, 8, 164, 24 );
		qrBox.setBounds( 8, 40, 164, 24 );
		barcodeBox.setBounds( 8, 72, 164, 24 );
		
		qrBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thread.setReadQR(qrBox.isSelected());
				thread.setActivate(qrBox.isSelected() || barcodeBox.isSelected());
				setActive(qrBox.isSelected() || barcodeBox.isSelected());
			}
		});
		
		barcodeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thread.setReadbar(barcodeBox.isSelected());
				thread.setActivate(qrBox.isSelected() || barcodeBox.isSelected());
				setActive(qrBox.isSelected() || barcodeBox.isSelected());
			}
		});
		
		parentBox.add(qrBox);
		parentBox.add(barcodeBox);
	}
	
	
	public int getGUIHeigth() {
		return 3;
	}
}