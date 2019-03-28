package crystalchain;

import java.util.HashMap;

import javax.swing.JOptionPane;

public class Keyboard {
	static int steps = 0;
	public static void main(String[] args) {
		
		JOptionPane jop = new JOptionPane();
		String string2entry = "";
		String keypad = "";
		string2entry = JOptionPane.showInputDialog(jop, "Entrez le string : ", string2entry);
		// annul -> null; x -> null
		if (string2entry != null && string2entry.matches("[1-9]*")) {
			keypad = JOptionPane.showInputDialog(jop, "Entrez le keypad : ", keypad);
			
			HashMap<String, Key> keyMap = new HashMap<String, Key>();
			if (keypad != null && keypad.length() == 9) {
				int x = 1;
				int y = 1;
				for (int i = 1; i <= 9; i++) {
					if (!keypad.matches(".*[" + i + "].*")) {
						jop.showMessageDialog(jop, "bad keypad !", "ERROR_MESSAGE", jop.ERROR_MESSAGE);
						System.exit(0);
					}
					y = i%3==0 ? 3 : i%3;
					keyMap.put(keypad.substring(i-1, i), new Key(x, y));
					if (i%3 == 0) {
						x++;
					}
				}
				Key currentKey = keyMap.get(string2entry.substring(0, 1));
				for (int i = 1; i < string2entry.length(); i++) {
					steps += keyMap.get(string2entry.substring(i, i+1)).getDistance(currentKey);
					currentKey = keyMap.get(string2entry.substring(i, i+1));
				}
				jop.showMessageDialog(jop, steps + " steps (seconds)", "Keyboards steps", jop.PLAIN_MESSAGE);
			}
			else {
			jop.showMessageDialog(jop, "bad keypad !!!", "ERROR_MESSAGE", jop.ERROR_MESSAGE);
			System.exit(0);
		}
		}
		else {
			jop.showMessageDialog(jop, "bad string, only digits !", "ERROR_MESSAGE", jop.ERROR_MESSAGE);
			System.exit(0);
		}
	}

}

class Key {
	private int posX;
	private int posY;
	Key(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	public int getX() {
		return posX;
	}
	public int getY() {
		return posY;
	}
	public int getDistance(Key key) {
		int distanceX = Math.abs(key.getX() - posX);
		int distanceY = Math.abs(key.getY() - posY);
		if (distanceX == 0 && distanceY == 0) {
			return 0;
		}
		if (distanceX == 2 || distanceY == 2) {
			return 2;
		}
		return 1;
	}
}