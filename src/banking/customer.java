/*     
 *    
 *  Author     : ACIMS(Arizona Centre for Integrative Modeling & Simulation)
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */
package banking;

import GenCol.*;

public class customer extends entity {
	public double processing_time;
	public String name;
	public double arrival_time;
	public double service_time;
	public double depart_time;

	public customer(String name) {
		super(name);
		processing_time = 100;
		this.name = name;
	}

	public customer(String name, double Processing_time) {
		super(name);
		processing_time = Processing_time;
		this.name = name;
	}

	public boolean equal(entity m) {
		customer jm = (customer) m;
		return (processing_time == jm.processing_time) && (jm.name.equals(name)) && (jm.arrival_time == arrival_time)
				&& (jm.service_time == service_time) && (depart_time == jm.depart_time);
	}

	public boolean greater_than(entity m) {
		customer jm = (customer) m;
		return arrival_time < jm.arrival_time;

	}

	public void print() {
		System.out.println("customer: " + name + " processing time: " + processing_time + " arrival time: "
				+ arrival_time + " service time: " + service_time + " departure time:" + depart_time);
	}

	public void update(double e) {
		processing_time = processing_time - e;
	}

}



