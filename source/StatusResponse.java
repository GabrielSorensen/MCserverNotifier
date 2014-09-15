public class StatusResponse {
        private String description;
        private Players players;
        private Version version;
        private String favicon;
        private long time;

        
        
        public StatusResponse (String description, Players players, Version version, String favicon, int time) {
        	this.description = description;
        	this.players = players;
        	this.version = version;
        	this.favicon = favicon;
        	this.time = time;
        }
        public StatusResponse() {
        	
        }
        public String getDescription() {
            return description;
        }

        public Players getPlayers() {
            return players;
        }

        public Version getVersion() {
            return version;
        }

        public String getFavicon() {
            return favicon;
        }

        public long getTime() {
            return time;
        }      

		public void setTime(long returnTime) {
			this.time = returnTime;
		}
    }
