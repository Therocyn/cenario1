package cenario1.context;

import javax.swing.JPanel;
import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.parameter.Parameters;
import repast.simphony.relogo.Observer;
import repast.simphony.relogo.factories.LinkFactory;
import repast.simphony.relogo.factories.ObserverFactory;
import repast.simphony.relogo.factories.PatchFactory;
import repast.simphony.relogo.factories.RLWorldDimensions;
import repast.simphony.relogo.factories.RLWorldDimensions.WorldType
import repast.simphony.relogo.factories.ReLogoWorldFactory;
import repast.simphony.relogo.factories.TurtleFactory;
import cenario1.relogo.UserLink;
import cenario1.relogo.UserObserver;
import cenario1.relogo.UserPatch;
import cenario1.relogo.UserTurtle;
import cenario1.relogo.UserGlobalsAndPanelFactory;

public class SimBuilder implements ContextBuilder {
	
	public Context build(Context context) {
	
		if (RunEnvironment.instance.isBatch()){
            UserGlobalsAndPanelFactory ugpf = new UserGlobalsAndPanelFactory();
            ugpf.initialize(new JPanel());
            ugpf.addGlobalsAndPanelComponents();
        }
		 
		Parameters p = RunEnvironment.getInstance().getParameters();
		
		// NOTE: minPxcor and minPycor must be <= 0
		int minPxcor = p.getValue("default_observer_minPxcor");
		int maxPxcor = p.getValue("default_observer_maxPxcor");
		int minPycor = p.getValue("default_observer_minPycor");
		int maxPycor = p.getValue("default_observer_maxPycor");
		RLWorldDimensions rLWorldDimensions = new RLWorldDimensions(minPxcor, maxPxcor, minPycor, maxPycor, WorldType.BOUNCY);
		
		LinkFactory lf = new LinkFactory(UserLink);
		TurtleFactory tf = new TurtleFactory(UserTurtle);
		PatchFactory pf = new PatchFactory(UserPatch);		
		ReLogoWorldFactory wf = new ReLogoWorldFactory(context,"default_observer_context", rLWorldDimensions, tf, pf, lf);
		
		ObserverFactory oF = new ObserverFactory("default_observer",UserObserver,wf);
		Observer dO = oF.createObserver();
		
		context.add(dO);
		return context;
	}
}
