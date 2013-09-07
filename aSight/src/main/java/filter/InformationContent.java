package filter;

public class InformationContent {

	private String message;
	private int x, y;
	private int timeout;
	private final Filter parent;
	private int id;

	public InformationContent( String message, int x, int y, int timeout, Filter parent ) {
		this.message = message;
		this.x = x;
		this.y = y;
		this.timeout = timeout;
		this.parent = parent;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTimeout() {
		return timeout;
	}

	public Filter getParent() {
		return parent;
	}

	public int getId() {
		return id;
	}

	public void setMessage( String message ) {
		this.message = message;
	}

	public void setX( int x ) {
		this.x = x;
	}

	public void setY( int y ) {
		this.y = y;
	}

	public void setTimeout( int timeout ) {
		this.timeout = timeout;
	}

}
