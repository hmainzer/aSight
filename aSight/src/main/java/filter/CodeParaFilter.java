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
	JCheckBox qrBox, barcodeBox;

	CodeThread thread;
	Timer timer;

	private int keyQR = application.Main.getFromConfig("CodeParaFilterQR"),
			keyBar = application.Main.getFromConfig("CodeParaFilterBar");

	public CodeParaFilter(ContentLayer contentLayer) {
		this.codeContent = new CodeParaContent(-1, this);
		CodeParaAccessControllList list = new CodeParaAccessControllList();
		codeContent.setCodeAccessControll(list);
		thread = new CodeThread(imgAccess, list);
		timer = new Timer();
		timer.schedule(thread, 1000, 1000);
		contentLayer.giveContent(this, codeContent);
	}

	protected BufferedImage action(BufferedImage img) {
		imgAccess.write(img);
		return img;
	}

	public void createGUI(Container parentBox) {
		JLabel filterLabel = new JLabel("~~~ Codereader ~~~");
		parentBox.add(filterLabel);
		qrBox = new JCheckBox("QR Codes");
		barcodeBox = new JCheckBox("Barcodes");

		filterLabel.setBounds(8, 8, 164, 24);
		qrBox.setBounds(8, 40, 164, 24);
		barcodeBox.setBounds(8, 72, 164, 24);

		qrBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thread.setReadQR(qrBox.isSelected());
				thread.setActivate(qrBox.isSelected()
						|| barcodeBox.isSelected());
				setActive(qrBox.isSelected() || barcodeBox.isSelected());
			}
		});

		barcodeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thread.setReadbar(barcodeBox.isSelected());
				thread.setActivate(qrBox.isSelected()
						|| barcodeBox.isSelected());
				setActive(qrBox.isSelected() || barcodeBox.isSelected());
			}
		});

		parentBox.add(qrBox);
		parentBox.add(barcodeBox);
	}

	public int getGUIHeight() {
		return 3;
	}

	@Override
	public boolean keyEvent(int key, int event, HotkeyMessage msg) {
		if (key == keyQR || key == keyBar) {
			if (key == keyQR) {
				qrBox.setSelected(!qrBox.isSelected());
				thread.setReadQR(qrBox.isSelected());
				msg.addEvent("QR-Code Filter: "
						+ (qrBox.isSelected() ? "on" : "off"));
			} else {
				barcodeBox.setSelected(!barcodeBox.isSelected());
				thread.setReadbar(barcodeBox.isSelected());
				msg.addEvent("Barcode Filter: "
						+ (barcodeBox.isSelected() ? "on" : "off"));
			}
			this.setActive(qrBox.isSelected() || barcodeBox.isSelected());
			thread.setActivate(qrBox.isSelected() || barcodeBox.isSelected());
			return true;
		}
		return false;
	}
}