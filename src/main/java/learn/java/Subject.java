package learn.java;

public class Subject {

	private Dependency dependency;

	public void setDependency(Dependency dependency) {
		this.dependency = dependency;
	}

	public void callDependentMultipleTimes() {
		dependency.sum(1, 1);
		dependency.sum(1, 2);
		dependency.sum(1, 3);
		dependency.sum(0, 0);
	}
}
