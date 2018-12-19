package learn.java;

public class Environment {
	
	private Instance instance;
	private HostFinder hostFinder;
	
	public void setHostFinder(HostFinder hostFinder) {
		this.hostFinder = hostFinder;
	}


	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	
	public Host getInstanceHost() {
		String hostName = instance.getHostName();
		String hostNameSn = instance.getHostShortName();
		Host host = hostFinder.findByName(hostName+"x");
		host.returnNothing(new Location());
		host.returnNothing(new Location());
		host.returnNothing(new Location());
		return host;

	}
	
	public String getInstanceHostName(){
		return instance.getHostName();
	}

}
