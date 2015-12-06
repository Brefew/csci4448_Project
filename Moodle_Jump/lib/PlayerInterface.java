package lib;

import java.util.Observable;
import java.util.Observer;

public interface PlayerInterface extends Observer {
	@Override
	public void update(Observable o, Object arg);
}
