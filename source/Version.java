public class Version {
        private String name;
        private String protocol;

        public Version(String name, String protocol) {
        	this.name = name;
        	this.protocol = protocol;
        }
        public String getName() {
            return name;
        }

        public String getProtocol() {
            return protocol;
        }
    }