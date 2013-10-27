package filter;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import application.Main;

public class PictureSearchFilter extends AbstractFilter {

	private boolean takeNextPicture = false;
	private int category = 0, precision = 1;
	private final int keyPrec = application.Main
			.getFromConfig("PictureSearchFilterPrec"), keyStart = application.Main
			.getFromConfig("PictureSearchFilterStart"), keyCatP = application.Main
			.getFromConfig("PictureSearchFilterCat+"), keyCatM = application.Main
			.getFromConfig("PictureSearchFilterCat-");
	protected int defaultKey = application.Main
			.getFromConfig("PictureSearchFilterDef");
	private JCheckBox isActiveBox;
	private JComboBox<String> categoryComboBox;
	private JSpinner precisionSpinner;
	private PictureSearchContent content;
	private PictureSearchThread runningSearch;

	public PictureSearchFilter() {		
		content = new PictureSearchContent(-5, this, Main.getFromConfig("PictureSearchFilterTextPosX"), Main.getFromConfig("PictureSearchFilterTextPosY"));
		Main.getContentLayer().giveContent(this, content);
	}

	protected BufferedImage action(BufferedImage img) {
		if (!takeNextPicture) {
			return img;
		}
		takeNextPicture = false;
		if ( runningSearch == null || ( runningSearch != null && runningSearch.getState() == Thread.State.TERMINATED )){		
			startSearch(application.Utility.copy(img));
		}
		return img;
	}

	private void startSearch(BufferedImage img) {
		runningSearch = new PictureSearchThread(img,
				category, precision, content);
		runningSearch.start();
	}

	public void createGUI(Container parentBox) {
		// JLabel
		JLabel filterLabel = new JLabel("~~~ Picture Search ~~~");
		filterLabel.setBounds(8, 8, 164, 24);
		parentBox.add(filterLabel);

		// JCheckBox
		isActiveBox = new JCheckBox("Activate");
		isActiveBox.setBounds(8, 40, 120, 24);
		isActiveBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PictureSearchFilter.this.setActive(isActiveBox.isSelected());
			}
		});
		parentBox.add(isActiveBox);

		// Snapshot
		final JButton snapshotButton = new JButton("Search Picture");
		snapshotButton.setBounds(8, 72, 120, 24);
		snapshotButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				takeNextPicture = isActive();
			}
		});
		parentBox.add(snapshotButton);

		// Category Label
		final JLabel categoryLabel = new JLabel("Category:");
		categoryLabel.setBounds(8, 104, 48, 24);
		parentBox.add(categoryLabel);

		// Category
		categoryComboBox = new JComboBox<String>(getCategories());
		categoryComboBox.setBounds(64, 104, 104, 24);
		categoryComboBox.setSelectedIndex(category);
		categoryComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getActionCommand().equals("comboBoxChanged")) {
					category = categoryComboBox.getSelectedIndex();
				}
			}
		});
		parentBox.add(categoryComboBox);

		// Precision Label
		final JLabel precisionLabel = new JLabel("Precision:");
		precisionLabel.setBounds(8, 136, 48, 24);
		parentBox.add(precisionLabel);

		// Precision
		precisionSpinner = new JSpinner();
		precisionSpinner.setValue(precision);
		precisionSpinner.setBounds(64, 136, 104, 24);
		precisionSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (precisionSpinner.getValue() instanceof Integer) {
					int value = (Integer) precisionSpinner.getValue();
					if (value > 10) {
						value = 10;
					} else if (value < 1) {
						value = 1;
					}
					precision = value;
					precisionSpinner.setValue(precision);
				}
			}
		});
		parentBox.add(precisionSpinner);
	}

	public int getGUIHeight() {
		return 5;
	}

	private Vector<String> getCategories() {
		Vector<String> v = new Vector<String>();
		v.add("0 > US Traffic Signs                              ");
		v.add("1 > Flags (fotw.net)                              ");
		v.add("2 > Flags                                         ");
		v.add("3 > English football jerseys                      ");
		v.add("4 > US Uniform Insignia                           ");
		v.add("5 > Symbols                                       ");
		v.add("6 > Maya Hyerogliphs                              ");
		v.add("7 > Van Gogh Gallery                              ");
		v.add("8 > Picasso Gallery                               ");
		v.add("9 > Michelangelo Gallery                          ");
		v.add("10 > Monet Gallery                                ");
		v.add("11 > Munch Gallery                                ");
		v.add("12 > UK Heraldry                                  ");
		v.add("13 > Irish Coats of Arms                          ");
		v.add("14 > American Historical flags                    ");
		v.add("15 > Logos                                        ");
		v.add("16 > Post stamps                                  ");
		v.add("17 > American flags                               ");
		v.add("18 > Space                                        ");
		v.add("19 > Coin                                         ");
		v.add("20 > Comics Batman                                ");
		v.add("21 > Famous people                                ");
		v.add("22 > Nature                                       ");
		v.add("23 > Temples                                      ");
		v.add("24 > Flowers                                      ");
		v.add("25 > Ships                                        ");
		v.add("26 > Monuments                                    ");
		v.add("27 > Banknotes                                    ");
		v.add("28 > Florence monuments                           ");
		v.add("29 > Paris monuments                              ");
		v.add("30 > Berlin monuments                             ");
		v.add("31 > Barcelona monuments                          ");
		v.add("32 > Rome monuments                               ");
		return v;
	}

	@Override
	public boolean keyEvent(int key, int event, HotkeyMessage msg) {
		if (key == defaultKey) {
			this.setActive(!this.isActive());
			isActiveBox.setSelected(this.isActive());
			msg.addEvent("Pic Search: "
					+ (this.isActive() ? "on" : "off"));
			return true;
		}
		if (!this.isActive()) {
			return false;
		}

		if (key == keyPrec) {
			if ((int) precisionSpinner.getValue() == 10) {
				precisionSpinner.setValue(1);
			} else {
				precisionSpinner
						.setValue((int) precisionSpinner.getValue() + 1);
			}
			msg.addEvent("Pic Search: Precision: "
					+ precisionSpinner.getValue());
			return true;
		}
		if (key == keyStart) {
			if ( runningSearch == null || ( runningSearch != null && runningSearch.getState() == Thread.State.TERMINATED )){	
				takeNextPicture = true;
				msg.addEvent("Pic Search: Search Picture");
			} else {
				msg.addEvent("Pic Search: Already Searching");
			}
			return true;
		}
		if (key == keyCatP) {
			if (categoryComboBox.getSelectedIndex() == categoryComboBox
					.getItemCount() - 1) {
				categoryComboBox.setSelectedIndex(0);
			} else {
				categoryComboBox.setSelectedIndex(categoryComboBox
						.getSelectedIndex() + 1);
			}
			msg.addEvent("Pic Search: Category: "
					+ categoryComboBox.getSelectedItem());
			return true;
		}
		if (key == keyCatM) {
			if (categoryComboBox.getSelectedIndex() == 0) {
				categoryComboBox.setSelectedIndex(categoryComboBox
						.getItemCount() - 1);
			} else {
				categoryComboBox.setSelectedIndex(categoryComboBox
						.getSelectedIndex() - 1);
			}
			msg.addEvent("Pic Search: Category: "
					+ categoryComboBox.getSelectedItem());
			return true;
		}

		return false;
	}
}
