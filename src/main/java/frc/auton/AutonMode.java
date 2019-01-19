package frc.auton;

public class AutonMode extends java.util.Vector<Move> {

	/**
	 * Appease the serialization gods
	 */
	private static final long serialVersionUID = -9054678334744895380L;
	
	public final String name;
	
	public AutonMode(String name, Iterable<Move> moves) {
		this.name = new String(name);
		moves.forEach(this::add);
	}
	
	public AutonMode(String name, Move... moves) {
		super(java.util.Arrays.asList(moves));
		this.name = new String(name);
		
	}
		
}
