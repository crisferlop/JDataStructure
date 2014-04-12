package comparator;

public class IntegerComparator<E> implements IComparator<E>{
	@Override
	public boolean isEqual(E pdato, E pdaton) {
		if (pdato == null){
			if(pdaton == null){
				return true;
			}
			return false;
		}
		
		else if(pdato.equals(pdaton)){
			return true;
		}
		return false;
	}

	@Override
	public boolean isLess(E pdato, E pdaton) {
		if (pdato == null){
			if(pdaton == null){
				return true;
			}
			return false;
		}
		Integer dato = (Integer) pdato;
		Integer dato2 = (Integer) pdaton;
		return dato<dato2;
	}

	@Override
	public boolean isHigher(E pdato, E pdaton) {
		// TODO Auto-generated method stub
		return !isLess(pdato, pdaton) && !isEqual(pdato, pdaton);
	}
}