package banking;

import GenCol.*;
import model.modeling.*;
import model.simulation.*;
import view.modeling.ViewableAtomic;
import view.simView.*;

public class customerQueue extends ViewableAtomic {// ViewableAtomic is used
													// instead
	// of atomic due to its
	// graphics capability
	protected entity customer;
	protected Queue cq;
	protected int noCustomerInQueue;
	protected double clockTime;

	public customerQueue() {
		this("customerQueue");
	}

	public customerQueue(String name) {
		super(name);
		addInport("in");
		addInport("next");
		addOutport("out");
		addOutport("status");

		addTestInput("in", new customer("C1", 1000));
		addTestInput("in", new customer("C2", 2000));
		addTestInput("in", new customer("C3", 1500));
		addTestInput("in", new customer("C4", 4000));
		addTestInput("next", new signal("1"));
		addTestInput("next", new signal("0"));
	}

	public void initialize() {
		phase = "passive";
		sigma = INFINITY;
		cq = new Queue();
		noCustomerInQueue = 0;
		super.initialize();
		clockTime = 0;
	}

	public void deltext(double e, message x) {
		Continue(e);
		clockTime += e;

		noCustomerInQueue = cq.size();
		if (phaseIs("passive"))
			for (int i = 0; i < x.getLength(); i++) {
				if (messageOnPort(x, "in", i)) {
					customer c = (customer) x.getValOnPort("in", i);
					c.arrival_time = clockTime;
					cq.addLast(c);
					holdIn("queueing", 0);
				}

				if (messageOnPort(x, "next", i)) {
					signal next = (signal) x.getValOnPort("next", i);
					if (next.name.equals("1"))
						holdIn("dequeueing", 0);
				}
			}
	}

	public void deltint() {
		clockTime += ta();
		passivate();
	}

	public void deltcon(double e, message x) {
		deltint();
		deltext(0, x);
	}

	public message out() {
		message m = new message();
		if (phaseIs("queueing") && noCustomerInQueue == 0) {
			m.add(makeContent("status", new signal("1")));
		}
		if (phaseIs("dequeueing") && cq.size() == 0) {
			m.add(makeContent("status", new signal("0")));
		}
		if (phaseIs("dequeueing") && cq.size() > 0) {
			m.add(makeContent("out", (customer) cq.removeFirst()));
			if (cq.size() == 0) {
				m.add(makeContent("status", new signal("0")));
			}
			if (cq.size() > 0) {
				m.add(makeContent("status", new signal("1")));
			}
		}
		return m;
	}

	public void showState() {
		super.showState();
	}

	public String getTooltipText() {
		return super.getTooltipText() + "\n" + "job: ";
	}
}
