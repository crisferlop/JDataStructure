package ordinateList;

import comparator.IComparator;



class NodeDouble<E>{
	private E dato;
	private NodeDouble<E> next;
	private NodeDouble<E> prev;
	
	public NodeDouble(E pdato){
		dato = pdato;
	}

	public E getDato(){return dato;}
	public void setDato(E pdato){dato = pdato;}
	public NodeDouble<E> getNext(){return next;}
	public void setNext(NodeDouble<E> pnext){next = pnext;}
	public boolean hasNext(){ return next != null;}

	
	public NodeDouble<E> getPrev(){return prev;}
	public void setPrev(NodeDouble<E> pprev){prev= pprev;}
	public boolean hasPrev(){ return prev != null;}
	
	public void print(){System.out.print(dato);}
	public String toString(){ return dato == null? "null" : dato.toString();}
}



/*
 * ==================================================================
 *  ________    _______    __     __      __       _________  _______  _________   DOUBLE LIST(v2.0)
 * |  ____  \  / _____ \  /      / /     / /	  /__  ____/ / _____/ /___  ___/
 * | |    / / / /    / / / /    / /     / /         / /     /     \      / /
 * | |   / / / /    / / / /    / /     / /         / /      \      \    / / 
 * | |__/ / / /____/ / / /____/ /     / /____  ___/ /__    __\___  /   / /
 * |_____/  \_______/ /________/     /_______//_______/   /_______/   /_/
 * ==================================================================
 */

/**
 * Class DoubleList. Listas doubles
 *
 * @author cristian
 * @param <E> Tipo de elemento del que esta compuesto la lista
 */

public class DoubleList<E> implements IList<E>{

	/** The _head. */
	private NodeDouble<E> _head;
	
	/** The _tail. */
	private NodeDouble<E> _tail;
	
	/** The _comparator. */
	private IComparator<E> _comparator;
	
	/** The _lenght. */
	private int _lenght = 0;
	
	/**
	 * 
	 * 
	 * Instancia una lista y asigna un comparator.
	 * en el paquete comparator Se encuentran comparadores ya definidos,
	 * {@link comparator.IntegerComparator},
	 * {@link comparator.StringComparator},
	 * {@link comparator.FloatComparator} y 
	 * {@link comparator.DoubleComparator}
	 * En el caso de que ninguno de los comparadores se adapte al @param <E> que forma la lista,
	 * puede implementarse de la clase {@link IComparator}. 
	 * 
	 * los comparadores sirven para que la lista se ordenen automaticamente.
	 * 
	 *
	 * @param comparator el nuevo comparator de la lista
	 */
	public DoubleList(IComparator<E> pcomparator){
		_comparator = pcomparator;
	}
	

	/* (non-Javadoc)
	 * @see list.IList#add(java.lang.Object)
	 */
	@Override
	public void add(E pdato) {
		if(_head == null){
			_head = _tail = new NodeDouble<E>(pdato);
		}
		/*
		else if(_tail == _head){
			if (_comparator.isLess(pdato, _head.getDato())){
				_head = new NodeDouble<E>(pdato);
			}
			else{
				_tail = new NodeDouble<E>(pdato);
			}
			_head.setNext(_tail);
			_tail.setPrev(_head);
		}
		*/
		else if (_comparator.isLess(pdato, _head.getDato())){
			NodeDouble<E> tmp = _head;
			_head = new NodeDouble<E>(pdato);
			_head.setNext(tmp);
			tmp.setPrev(_head);
		}
		else if (!_comparator.isHigher(_tail.getDato(), pdato)){
			NodeDouble<E> tmp = _tail;
			_tail = new NodeDouble<E>(pdato);
			tmp.setNext(_tail);
			_tail.setPrev(tmp);
		}
		else{
			NodeDouble<E> tmp = _head;
			while(_comparator.isLess(tmp.getNext().getDato(),pdato)){
				tmp = tmp.getNext();
			}
			NodeDouble<E> tmp2 = tmp.getNext();
			NodeDouble<E> insertion = new NodeDouble<>(pdato);
			tmp.setNext(insertion);
			insertion.setPrev(tmp);
			insertion.setNext(tmp2);
			tmp2.setPrev(insertion);
		}
		_lenght++;
	}
	
	/**
	 * Removes the first.
	 */
	private void removeFirst(){
		if (_head.hasNext()){
			_head = _head.getNext();
			_head.setPrev(null);
			_lenght--;
		}
		else{
			_head = _tail = null;
			_lenght = 0;
		} 
	}
	
	/**
	 * Removes the last.
	 */
	private void removeLast(){
		if (_head == _tail){
			_head = _tail = null;
			_lenght = 0;
		}
		else{
			_tail = _tail.getPrev();
			_tail.setNext(null);
			_lenght--;
		}
	}
	

	/* (non-Javadoc)
	 * @see list.IList#remove(int)
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || _lenght <= index){
			if(0 == _lenght){return;}
			throw new IndexOutOfBoundsException("No se puede remover el dato,\n "
					+ "pues esta fuera de los limites de la lista");
		}
		if(index == 0){
			removeFirst();
		}
		else if (index == _lenght-1){
			removeLast();
		}
		else{
			NodeDouble<E> tmp = getIndex(index);
			tmp.getPrev().setNext(tmp.getNext());
			tmp.getNext().setPrev(tmp.getPrev());
			_lenght--;
		}
	}
	/* (non-Javadoc)
	 * @see list.IList#get(int)
	 */
	@Override
	public E get(int index) {
		return getIndex(index).getDato();
	}
	
	/**
	 * Gets the index.
	 *
	 * @param index the index
	 * @return the index
	 */
	private NodeDouble<E> getIndex(int index){
		if (index == 0){
			return _head;
		}
		else if(index == _lenght-1){
			return _tail;
		}
		else if (0< index && index < _lenght-1){
			int calculo = (_lenght/2) - index;
			NodeDouble<E> actual;
			if(calculo <= 0){
				calculo = _lenght - index-1;
				actual = _tail;
				for(int i = 0; i< calculo; i++){
					actual = actual.getPrev();
				}	
			}
			else{
				actual = _head;
				for(int i = 0; i< index; i++){
					actual = actual.getNext();
				}
			}
			return actual;
		}
		else{
			throw new IndexOutOfBoundsException("Fuera del rango: " + index);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see list.IList#getLenght()
	 */
	@Override
	public int getLenght() {
		return _lenght;
	}

	/* (non-Javadoc)
	 * @see list.IList#getIterator()
	 */
	@Override
	public DListIterator<E> getIterator() {
		return new DListIterator<>(_head, _tail, this);
	}
	
	/* (non-Javadoc)
	 * @see list.IList#isEmpty()
	 */
	@Override
	public boolean isEmpty(){
		return _lenght == 0;
	}
	
	/* (non-Javadoc)
	 * @see list.IList#print()
	 */
	@Override
	public void print() {
		System.out.println(toString());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if (!isEmpty()){
			String a = "[";
			NodeDouble<E> actual = _head;
			for(int i = 0; i < _lenght-1; i++){
				a += actual.toString() + ",";
				actual = actual.getNext();
			}
			a += actual.toString()  + "]";
			return a;
		}
		else{
			return "[]";
		}
	}


	@Override
	public int search(E data) {
		int centerint,left, right;
		left=0;right=_lenght-1;
		E center;
		while (left <= right){
			centerint = (left + right)/2;
			center = get(centerint);
			if(_comparator.isHigher(center,data)){
				right = centerint-1;
			}
			else if (_comparator.isLess(center, data)){
				left = centerint+1;
			}
			else{
				return (left+right)/2;
			}
		}
		return -1;
	}
}
