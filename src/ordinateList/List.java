package ordinateList;

import comparator.IComparator;


// TODO: Auto-generated Javadoc
class Node<E>{
	private E _dato;
	private Node<E> _next;

	public Node(E pdato){
		_dato = pdato;
	}

	public E getDato(){return _dato;}
	public void setDato(E pdato){_dato = pdato;}
	public Node<E> getNext(){return _next;}
	public void setNext(Node<E> pnext){_next = pnext;}
	public void print(){System.out.print(_dato);}
	public boolean hasNext(){ return _next != null;}
	public String toString(){ return _dato == null? "null" : _dato.toString();}
}




/*
 * ============================================================
 *     	   __      _________  _______   _________   LIST(v2.0)
 *        / /	  /___  ___/ / _____/  /___  ___/
 *       / /         / /    /     \      / /
 *      / /			/ /	    \      \    / / 
 *     / /____  ___/ /__   __\___  /   / /
 *    /_______//_______/  /_______/   /_/
 * ============================================================    
 */


/**
 * Clase List. Listas Simples
 *
 * @param <E> Elementos de la Lista
 */
public class List<E> implements IList<E>{
	
	/** el nodo primero. */
	private Node<E> _head;
	
	/** el nodo final. */
	private Node<E> _tail;
	
	/** el largo. */
	private int _lenght = 0;
	
	/** The _comparator. */
	private IComparator<E> _comparator;
	
	/* (non-Javadoc)
	 * @see list.IList#add(java.lang.Object)
	 */
	
	
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
	public List(IComparator<E> pcomparator){
		_comparator = pcomparator;
	}
	
	
	@Override
	public void add(E pdato){
		if(_head == null){
			_head = _tail = new Node<E>(pdato);
		}
		else if (_comparator.isLess(pdato, _head.getDato())){
			Node<E> tmp = _head;
			_head = new Node<E>(pdato);
			_head.setNext(tmp);
		}
		else if (!_comparator.isHigher(_tail.getDato(), pdato)){
			Node<E> tmp = _tail;
			_tail = new Node<E>(pdato);
			tmp.setNext(_tail);
		}
		else{
			Node<E> tmp = _head;
			while(_comparator.isLess(tmp.getNext().getDato(),pdato)){
				tmp = tmp.getNext();
			}
			Node<E> tmp2 = tmp.getNext();
			Node<E> insertion = new Node<>(pdato);
			tmp.setNext(insertion);
			insertion.setNext(tmp2);
		}
		_lenght++;
	}
	
	/**
	 * Removes the first.
	 */
	private void removeFirst(){
		if (_head.hasNext()){
			_head = _head.getNext();
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
			Node<E>tmp = getIndex(_lenght-2);
			tmp.setNext(null);
			_tail = tmp;
			_lenght--;
		}
	}
	
	/* (non-Javadoc)
	 * @see list.IList#remove(int)
	 */
	@Override
	public void remove(int index){
		if (index< 0 || _lenght <= index){
			if (0 == _lenght){
				return;
			}
			throw new IndexOutOfBoundsException("No se puede remover el dato,\n "
					+ "pues esta fuera de los limites de la lista");
		}
			
		else if(index == 0){
			removeFirst();
		}
		else if (index == _lenght-1){
			removeLast();
		}
		else{
			Node<E>tmp = getIndex(index-1);
			tmp.setNext(tmp.getNext().getNext());
			_lenght--;
		}
	}
	
	/* (non-Javadoc)
	 * @see list.IList#get(int)
	 */
	@Override
	public E get(int index){
		return getIndex(index).getDato();
	}

	/**
	 * Gets the index.
	 *
	 * @param index the index
	 * @return the index
	 */
	private Node<E> getIndex(int index){
		if (index == 0){
			return _head;
		}
		else if(index == _lenght-1){
			return _tail;
		}
		else if (0< index && index < _lenght-1){
			Node<E> actual = _head;
			for(int i = 0; i< index; i++){
				actual = actual.getNext();
			}
			return actual;
		}
		else{
			throw new IndexOutOfBoundsException("Fuera del rango: " + index);
		}
	}
	
	/* (non-Javadoc)
	 * @see list.IList#isEmpty()
	 */
	@Override
	public boolean isEmpty(){
		return _lenght == 0;
	}
	
	/* (non-Javadoc)
	 * @see list.IList#getLenght()
	 */
	@Override
	public int getLenght(){return _lenght;}

	/* (non-Javadoc)
	 * @see list.IList#getIterator()
	 */
	@Override
	public ListIterator<E> getIterator() {
		return new ListIterator<>(_head,_tail,this);
	}
	
	/* (non-Javadoc)
	 * @see list.IList#print()
	 */
	@Override
	public void print(){
		System.out.println(toString());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		if (!isEmpty()){
			String a = "[";
			Node<E> actual = _head;
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
