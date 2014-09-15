import java.util.List;

public class Players {
        private int max;
        private int online;
        private List<Player> sample;

        public Players (int max, int online, List<Player> sample) {
        	this.max = max;
        	this.online = online;
        	this.sample = sample;
        }
        public int getMax() {
            return max;
        }

        public int getOnline() {
            return online;
        }

        public List<Player> getSample() {
            return sample;
        }    
        public String playersToString() {
        	String s = "";
        	for (Player p : sample) {
        		s += p.getName() + '\n';
        	}
        	return s;
        }
    }