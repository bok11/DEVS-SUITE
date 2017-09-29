package banking;

import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion.Setting;

import GenCol.entity;

public class signal extends entity {
	public String name;
	public signal(String name) {
		super(name);
		this.name = name;
	}

	public signal(String name, double Processing_time) {
		super(name);
		this.name = name;
	}

	public boolean equal(signal m) {
		signal jm = (signal) m;
		return jm.name .equals(name);

	}
	public boolean equal(String m) {
		return this.name .equals(m);

	}



	public void print() {
		System.out.println("signal: " + name );
	}

}