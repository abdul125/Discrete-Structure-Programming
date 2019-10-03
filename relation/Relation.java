


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class that represents a <i>relation</i> on a <i>set</i> of (parameterized) elements
 * of type <code>T</code>.  NEW ONE
 * 
 * @param <T>
 */
public class Relation<T> {

	//TODO: You need to determine how you will represent the relation and
	//         possibly the underlying set


	private Set<T> items;
	private Set<TwoElements<T>> setTwoElements;

	/**
	 * Creates an empty relation on an empty set
	 */
	public Relation() {
		items = new HashSet<>();
		setTwoElements = new LinkedHashSet<>();
	}

	/**
	 * Creates an empty relation on the given set
	 * @param items
	 */
	public Relation(Set<T> items) {
		this.items = new HashSet<T>(items);
		setTwoElements = new HashSet<>();
	}

	/**
	 * Optional copy constructor. Should create a <i>deep copy</i> of
	 * <code>that</code> relation into <code>this</code> relation.
	 * @param that
	 */
	public Relation(Relation<T> that){
	}

	/**
	 * Returns (a new copy of) the underlying set that the relation is based on
	 * @return
	 */
	public Set<T> getRelationSet() {
		Set<T> resultSet = new HashSet<T>(items);
		return resultSet;
	}

	/**
	 * Return <code>true</code> if this relation is <i>reflexive</i>, <code>false</code>
	 * otherwise.
	 * @return
	 */
	public boolean isReflexive() {

		for (T t: items) {
			TwoElements<T> teRefl = new TwoElements<>(t, t);
			if (!setTwoElements.contains(teRefl)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return <code>true</code> if this relation is <i>symmetric</i>, <code>false</code>
	 * otherwise.
	 * @return
	 */
	public boolean isSymmetric() {
		ArrayList<TwoElements<T>> list = new ArrayList<>();
		for (TwoElements<T> te: setTwoElements) {
			TwoElements<T> newTe = new TwoElements<T>(te.getT2(), te.getT1());
			if (list.contains(newTe)) {
				list.remove(newTe);
			} else if (!te.isSymmetric()){
				list.add(te);
			}
		}
		return list.isEmpty();
	}

	/**
	 * Return <code>true</code> if this relation is <i>asymmetric</i>, <code>false</code>
	 * otherwise.
	 * @return
	 */
	public boolean isAsymmetric() {
		if (setTwoElements.isEmpty())
			return true;
		for (T t1: items)
			for (T t2: items) {
					if (setTwoElements.contains(new TwoElements<T>(t1, t2)) 
							&& setTwoElements.contains(new TwoElements<T>(t2, t1))) {
						return false;
					}
				}
		return true;
	}

	
	/**
	 * Return <code>true</code> if this relation is <i>antisymmetric</i>, <code>false</code>
	 * otherwise.
	 * @return
	 */
	public boolean isAntisymmetric() {
		for (T t1: items)
			for (T t2: items) {
				if (setTwoElements.contains(new TwoElements<T>(t1, t2)) 
						&& setTwoElements.contains(new TwoElements<T>(t2, t1)) 
						^ new TwoElements<T>(t1, t2).isSymmetric()) {
					return false;
				} 
			}
		return true;
	}

	/**
	 * Return <code>true</code> if this relation is <i>transitive</i>, <code>false</code>
	 * otherwise.
	 * @return
	 */
	public boolean isTransitive() {
		for (T t1: items)
			for (T t2: items)
				for (T t3: items) {
					if (setTwoElements.contains(new TwoElements<T>(t1, t2)) 
							&& setTwoElements.contains(new TwoElements<T>(t2, t3)) 
							&& !isPath(t1, t3)) {
						return false;
					}
				}
		return true;
	}

	private boolean isPath(T t1, T t2) {
		for (TwoElements<T> te: setTwoElements) {
			if (te.getT1() == t1 && te.getT2() == t2) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return <code>true</code> if this relation is a <i>partial order</i>, <code>false</code>
	 * otherwise.
	 * @return
	 */
	public boolean isPartialOrder() {
		return isReflexive() && isAntisymmetric() && isTransitive();
	}

	/**
	 * Return <code>true</code> if this relation is an <i>equivalence relation</i>, <code>false</code>
	 * otherwise.
	 * @return
	 */
	public boolean isEquivalenceRelation() {
		return isReflexive() && isSymmetric() && isTransitive();
	}

	/**
	 * Returns <code>true</code> if this relation is a <i>function</i> on the underlying
	 * set, <code>false</code> otherwise.
	 * @return
	 */
	public boolean isFunction() {
		
		if (setTwoElements.isEmpty())
			return false;
		boolean t1has_a_value=false;
		for (T t1: items){
		    
			t1has_a_value=false;	
		    
			for (T t2: items){
				if(setTwoElements.contains(new TwoElements<T>(t1, t2)))
					if(!t1has_a_value){
						t1has_a_value=true;
					}
					else{/* then t1 must have two values */
						return false;
					}
			}
			/* if t1 still has no value then the relation is not
			 * a function
			 */
			if(!t1has_a_value){
				return false;
			}
		}
				
	    /* if we get to this point we have tested that every
	     * t1 has exactly one value, so the relation must be a 
	     * function.	
	     */
		return true;

	}

	/**
	 * Returns <code>true</code> if this relation is a function and is <i>surjective</i> 
	 * (an onto function), <code>false</code> otherwise.
	 * @return
	 */
	public boolean isSurjection() {
		ArrayList<T> t1 = new ArrayList<>();
		ArrayList<T> t2 = new ArrayList<>();
		if (isFunction()) {

			for (TwoElements<T> te: setTwoElements) {
				t1.add(te.getT1());
				t2.add(te.getT2());
			}	

			for (T t: items) {
				if (!t2.contains(t)) {
					return false;
				}
			}
			return true;
		} 
		else {
			return false;
		}

	}

	/**
	 * Returns <code>true</code> if this relation is a function and is <i>injective</i> 
	 * (a one-to-one function), <code>false</code> otherwise.
	 * @return
	 */
	public boolean isInjection() {
		ArrayList<T> t2 = new ArrayList<>();
		if (isFunction()) {
			for (TwoElements<T> te: setTwoElements) {

				if (t2.contains(te.getT2())) {
					return false;
				} 
				else {
					t2.add(te.getT2());
				}
			}

			return true;
		} 
		else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if this relation is a function and is <i>bijective</i> 
	 * (a one-to-one and onto function), <code>false</code> otherwise.
	 * @return
	 */
	public boolean isBijection() {
		return isSurjection() && isInjection();

	}

	/**
	 * Returns <code>true</code> if the elements in the given ordered pair (a, b) are related
	 * (that is, if a is-related-to- b).
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean isRelation(T a, T b) {
		return setTwoElements.contains(new TwoElements<T>(a, b));

	}

	/**
	 * Adds the element <code>a</code> to the underlying set that this relation 
	 * is based on.  This method does not infer any particular relation involving 
	 * <code>a</code> and other elements in the underlying set (to add relations, 
	 * use {@link #addRelation} to add relations between elements).  If <code>a</code> 
	 * is null, an {@link IllegalArgumentException} is thrown.
	 */
	public void addElement(T a) {

		if (a == null) {
			throw new IllegalArgumentException("Relation does not allow null values");
		}
		items.add(a);
	}

	/**
	 * Removes the element <code>a</code> from the underlying set that this relation
	 * is based on.  As a side-effect, removes all relations involving <code>a</code> 
	 * @param a
	 */
	public void removeElement(T a) {
		if (a == null) {
			throw new IllegalArgumentException("Relation does not allow null values");
		}
		items.remove(a);
	}

	/**
	 * Adds the relation between the ordered pair (a, b) to this relation.  If a or b
	 * are not contained in the underlying set that this relation is based on, calling this
	 * method will add them for you.  If either a or b are null, an {@link IllegalArgumentException}
	 * is thrown.
	 * @param a
	 * @param b
	 */
	public void addRelation(T a, T b) {

		if(a == null || b == null) 
			throw new IllegalArgumentException("Relation does not allow null values");

		if (!items.contains(a)) {
			addElement(a);
			if (!items.contains(b)) {
				addElement(b);
			}
		}

		TwoElements<T> te = new TwoElements<>(a, b);
		setTwoElements.add(te);
		ArrayList<TwoElements<T>> list = new ArrayList<>(setTwoElements);
		Collections.sort(list);
		setTwoElements = new LinkedHashSet<>(list);
	}

	/**
	 * Removes the relation between the ordered pair (a, b) if it exists
	 * @param a
	 * @param b
	 */
	public void removeRelation(T a, T b) {
		if(a == null || b == null) 
			throw new IllegalArgumentException("Relation does not allow null values");

		TwoElements<T> te = new TwoElements<T>(a, b);
		if (setTwoElements.contains(te)) {
			setTwoElements.remove(te);
		}
	}

	/**
	 * Returns a new {@link Relation} that represents the <i>reflexive closure</i> of this
	 * relation
	 * @return
	 */
	public Relation<T> getReflexiveClosure() {
		Relation<T> result = new Relation<>(items);
		for (T t: items) {
			TwoElements<T> teRefl = new TwoElements<>(t, t);
			if (!setTwoElements.contains(teRefl)) {
				result.addRelation(t, t);
			}
		}
		for (TwoElements<T> te: setTwoElements) {
			result.addRelation(te.getT1(), te.getT2());
		}
		return result;
	}

	/**
	 * Returns a new {@link Relation} that represents the <i>symmetric closure</i> of this
	 * relation
	 * @return
	 */
	public Relation<T> getSymmetricClosure() {
		Relation<T> result = new Relation<>(items);
		for (TwoElements<T> te: setTwoElements) {
			if (!te.isSymmetric() && !result.getSetTwoElements().contains(new TwoElements<T>(te.getT2(), te.getT1()))) {
				result.addRelation(te.getT2(), te.getT1());
			}
		}
		for (TwoElements<T> te: setTwoElements) {
			result.addRelation(te.getT1(), te.getT2());
		}
		return result;

	}

	/**
	 * Returns a new {@link Relation} that represents the <i>transitive closure</i> of this
	 * relation
	 * @return
	 */
	public Relation<T> getTransitiveClosure() {
		Relation<T> result = new Relation<>(items);
		boolean done = false;
		Relation <T> old_relation = new Relation<>(items);
		old_relation.setTwoElements = setTwoElements;
		
		while(!done){
			done = true;
			for (T t1: items)
				for (T t2: items)
					for (T t3: items) {
						if (old_relation.setTwoElements.contains(new TwoElements<T>(t1, t2)) 
								&& old_relation.setTwoElements.contains(new TwoElements<T>(t2, t3)) 
								&& !old_relation.setTwoElements.contains(new TwoElements<T>(t1, t3))) {
							result.addRelation(t1, t3);
							done=false;
						}
					}
		
			for (TwoElements<T> te: old_relation.setTwoElements) {
				result.addRelation(te.getT1(), te.getT2());
			}
			old_relation.setTwoElements=result.setTwoElements;
		}

		return result;
	}

	/**
	 * Composes <code>this</code> relation with <code>r</code>.  This method has
	 * been done for you as an example of how to use the interface.  Bugs in your
	 * other methods may mean this method does not work.  It will not be tested in
	 * the grader.
	 * @param r
	 * @return
	 */
	public Relation<T> compose(Relation<T> r) {
		if(!r.getRelationSet().equals(this.getRelationSet())) {
			throw new IllegalStateException("Underlying sets do not match, cannot be composed");
		}
		Relation<T> result = new Relation<T>(r.getRelationSet());
		for(T a : this.getRelationSet()) {
			for(T c : this.getRelationSet()) {
				for(T b : this.getRelationSet()) {
					if(this.isRelation(a, b) && r.isRelation(b, c)) {
						result.addRelation(a, c);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Overridden <code>toString()</code> method that returns a string representation of this
	 * relation.  The formatting of the output string includes the underlying set and all of 
	 * the ordered pairs in the relation.  An example:
	 * <pre>
	 *   R({a, b, c}) = { (a, b), (a, c), (b, a), (c, c) }
	 * </pre>
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("R(");
		s.append(items.toString());
		s.append(") = ");
		s = new StringBuilder(s.toString().replace('[', '{').replace(']', '}'));

		s.append(setTwoElements.toString());
		return s.toString().replace("[", "{ ").replace("]", " }").replace('<', '(').replace('>', ')').replace(",", ", ").replace(",  ", ", ");


	}

	public static void main(String[]args){
		//this is an example for how to use this class (once you've implemented its functions)

		LinkedHashSet<String> list = new LinkedHashSet<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		Relation<String> relate1 = new Relation<String>(list);
		relate1.addRelation("a", "b");
		relate1.addRelation("b","c");
		
		System.out.println(relate1.toString());
		System.out.println(relate1.isSymmetric());
		System.out.println(relate1.isTransitive());
		for(String a : relate1.getRelationSet()) {
			for(String b : relate1.getRelationSet()) {
				System.out.println("(a,b) = ("+a+","+b+") = "+relate1.isRelation(a, b));
			}
		}
		
		Relation<String> rc = relate1.getReflexiveClosure();
		System.out.println(rc);
		System.out.println(rc.isReflexive());
		for(String a : rc.getRelationSet()) {
			for(String b : rc.getRelationSet()) {
				System.out.println("(a,b) = ("+a+","+b+") = "+rc.isRelation(a, b));
			}
		}
		
		Relation<String> tc = relate1.getTransitiveClosure();
		System.out.println(tc);
		System.out.println(tc.isTransitive());
		for(String a : tc.getRelationSet()) {
			for(String b : tc.getRelationSet()) {
				System.out.println("(a,b) = ("+a+","+b+") = "+tc.isRelation(a, b));
			}
		}
		Relation<String> sc = relate1.getSymmetricClosure();
		System.out.println(sc);
		System.out.println(sc.isSymmetric());
		for(String a : sc.getRelationSet()) {
			for(String b : sc.getRelationSet()) {
				System.out.println("(a,b) = ("+a+","+b+") = "+sc.isRelation(a, b));
			}
		}

		System.out.println(relate1.toString());
		System.out.println(relate1.isSymmetric());
		System.out.println(relate1.isTransitive());
		for(String a : relate1.getRelationSet()) {
			for(String b : relate1.getRelationSet()) {
				System.out.println("(a,b) = ("+a+","+b+") = "+relate1.isRelation(a, b));
			}
		}
		System.out.println("done");

	}


	private class TwoElements<T> implements Comparable<TwoElements<T>>{

		private T t1;
		private T t2;

		public TwoElements(T t1, T t2) {
			this.t1 = t1;
			this.t2 = t2;
		}

		/*
		 * Getters and setters.
		 */
		public T getT1() {
			return t1;
		}

		public T getT2() {
			return t2;
		}

		public boolean isSymmetric() {
			return t1 == t2;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((t1 == null) ? 0 : t1.hashCode());
			result = prime * result + ((t2 == null) ? 0 : t2.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TwoElements other = (TwoElements) obj;
			if (t1 == null) {
				if (other.t1 != null)
					return false;
			} else if (!t1.equals(other.t1))
				return false;
			if (t2 == null) {
				if (other.t2 != null)
					return false;
			} else if (!t2.equals(other.t2))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "(" + t1 + ", " + t2 + ")"; 
		}



		@Override
		public int compareTo(TwoElements<T> o) {
			if (this.t1.toString().compareTo(o.getT1().toString()) > 0) {
				return 1;
			} else if (this.t1.toString().compareTo(o.getT1().toString()) < 0) {
				return -1;
			} else {
				if (this.t2.toString().compareTo(o.getT2().toString()) > 0) {
					return 1;
				} else if (this.t2.toString().compareTo(o.getT2().toString()) < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		}



	}


	public Set<T> getItems() {
		return items;
	}

	public Set<TwoElements<T>> getSetTwoElements() {
		return setTwoElements;
	}

}

