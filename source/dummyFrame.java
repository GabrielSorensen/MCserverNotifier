import javax.swing.JFrame;

class dummyFrame extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8375896893800619199L;
		private static final int EXIT_ON_CLOSE2 = EXIT_ON_CLOSE;

		public dummyFrame () {
			setAlwaysOnTop(true);
			setVisible(false);
			setDefaultCloseOperation(EXIT_ON_CLOSE2);
		}
	}